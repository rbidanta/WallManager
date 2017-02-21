package com.iu.lab.p535.wallmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class AddWallpaper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallpaper);

        //Bitmap image= imageView.getDrawingCache();

        Intent intent = getIntent();
        ArrayList<String> imageURIList  = null;
        ArrayList<ImageView> imageViewList = null;

        //getIntent().getStringExtra(WallManMain.IMAGE_URI);

        Bundle extras = getIntent().getExtras();

        try {
            if(extras != null){

                imageURIList = intent.getStringArrayListExtra(WallManMain.IMAGE_URI_LIST);

                final  ArrayList<String> imageURIs = imageURIList;

                if(checkPermissionREAD_EXTERNAL_STORAGE(this)) {

                    imageViewList = new ArrayList<ImageView>();

                    for(int i=0;i<imageURIList.size();i++) {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imageURIList.get(i)));
                        ImageView imageView = new ImageView(this);

                        imageView.setImageBitmap(bitmap);
                        imageView.setLayoutParams(new GridView.LayoutParams(160, 160));
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setPadding(10, 10, 10, 10);
                        imageViewList.add(imageView);

                        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_add_wallpaper);
                        //layout.addView(imageView);
                    }

                    GridView imageGridView = (GridView)findViewById(R.id.imagesgridview);
                    imageGridView.setAdapter(new ImageViewAdaptor(this,imageViewList));

                    imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            Toast.makeText(AddWallpaper.this, "" + position,
                                    Toast.LENGTH_SHORT).show();

                            System.out.println("Get Image URI:"+ imageURIs.get(position));

                            v.setEnabled(false);

                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


}
