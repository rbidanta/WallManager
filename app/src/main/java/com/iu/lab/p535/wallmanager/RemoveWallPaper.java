package com.iu.lab.p535.wallmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class RemoveWallPaper extends AppCompatActivity {


    ImageDBHandler imageDBHandler = new ImageDBHandler(this);
    GridView imageGridView = null;
    ArrayList<ImageView> imageViewList = null;
    Context context = this;

    ArrayList<Bitmap> bitmapArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remove_wall_paper);



        //Bitmap image= imageView.getDrawingCache();

        Intent intent = getIntent();
       // intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        ArrayList<String> imageURIList  = null;


        //getIntent().getStringExtra(WallManMain.IMAGE_URI);

        //Bundle extras = getIntent().getExtras();

        try {
            //if(extras != null){

                bitmapArrayList = new ArrayList<Bitmap>();

                imageViewList = new ArrayList<ImageView>();

                AsyncTaskRunner fetchImages = new AsyncTaskRunner();

                fetchImages.execute();

            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);





    }

    private class AsyncTaskRunner extends AsyncTask<String,String,String> {

        private String resp;
        @Override
        protected String doInBackground(String... params) {

            ArrayList<String> uri_Al = null;



            uri_Al = imageDBHandler.fetchImages();


                for(int i=0;i<uri_Al.size();i++) {

                    System.out.println(" URI - "+ i + " = " +uri_Al.get(i));

                    Uri uri = Uri.parse(uri_Al.get(i));

                        /*getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //final int takeFlags = intent.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        getApplicationContext().getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        Cursor cursor = getApplicationContext().getContentResolver().query(uri,null,null,null,null);
                        cursor.moveToFirst();

                        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

                        String filePath = cursor.getString(idx);

                        System.out.println(" FilePath - "+ i + " = " + filePath);*/

                    Bitmap bitmap = null;
                    try {


                        getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //getApplicationContext().getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        ParcelFileDescriptor parcelFileDescriptor =
                                getContentResolver().openFileDescriptor(uri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        bitmapArrayList.add(bitmap);
                        parcelFileDescriptor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imageURIList.get(i)));

                    //Bitmap bitmap = BitmapFactory.decodeFile(filePath);


                    //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_add_wallpaper);
                    //layout.addView(imageView);
                }

            System.out.println(" Number of Bitmaps:"+ bitmapArrayList.size());

            return "hello";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for(int i=0;i<bitmapArrayList.size();i++){

                ImageView imageView = new ImageView(context);

                imageView.setImageBitmap(bitmapArrayList.get(i));
                imageView.setLayoutParams(new GridView.LayoutParams(160, 160));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
                imageViewList.add(imageView);


            }

            imageGridView = (GridView)findViewById(R.id.imagesgridview);
            imageGridView.setAdapter(new ImageViewAdaptor(context,imageViewList));

            imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(RemoveWallPaper.this, "" + position,
                            Toast.LENGTH_SHORT).show();

                    v.setEnabled(false);

                }
            });




        }

        public static final int MY_PERMISSIONS_MULTIPLE_REQUESTS = 123;
        //public static final int MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS = 456;

        private boolean checkPermission(
                final Context context) {
            System.out.println("Permission1");
            int currentAPIVersion = Build.VERSION.SDK_INT;
            int chkExtStrPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            System.out.println("chkExtStrPermission"+chkExtStrPermission);
            int chkManDocPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_DOCUMENTS);
            System.out.println("chkManDocPermission"+chkManDocPermission);
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                System.out.println("Permission2");
                if ( chkExtStrPermission != PackageManager.PERMISSION_GRANTED || chkManDocPermission != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission3");
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            (Activity) context,
                            Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(
                            (Activity) context,
                            Manifest.permission.MANAGE_DOCUMENTS)) {
                        System.out.println("Permission4");
                        String[] permissionReq = {Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.MANAGE_DOCUMENTS};
                        showDialog("External storage", context, permissionReq);


                    } else {
                        System.out.println("Permission5");
                        ActivityCompat
                                .requestPermissions(
                                        (Activity) context,
                                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE ,Manifest.permission.MANAGE_DOCUMENTS},
                                        MY_PERMISSIONS_MULTIPLE_REQUESTS);
                    }
                    System.out.println("Permission6");
                    return true;
                } else {
                    System.out.println("Permission7");
                    return true;
                }


            } else {
                System.out.println("Permission8");
                return true;
            }
        }

        private void showDialog(final String msg, final Context context,
                               final String[] permission) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Permission necessary");
            alertBuilder.setMessage(msg + " permission is necessary");
            alertBuilder.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    permission,
                                    MY_PERMISSIONS_MULTIPLE_REQUESTS);
                        }
                    });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }





    }



}
