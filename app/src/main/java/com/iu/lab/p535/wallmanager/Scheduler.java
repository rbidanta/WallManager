package com.iu.lab.p535.wallmanager;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler extends AppCompatActivity {

    //private TextView switchStatus;
    private Switch wallManSwitch;
    private EditText schedule;

    Context context = this;
    Timer mytimer;
    int interval;
    WallpaperManager myWallpaperManager;

    ArrayList<String> uriList = null;

    Intent intent = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);


        intent = getIntent();

        // final ArrayList<Uri> contentUri = intent.getParcelableArrayListExtra(WallManMain.IMAGE_URI_LIST);

        // System.out.println("Content Uris"+ contentUri.size());

        uriList = intent.getStringArrayListExtra(WallManMain.IMAGE_URI_LIST);

        {


            wallManSwitch = (Switch) findViewById(R.id.switch1);


            //attach a listener to check for changes in state
            wallManSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked) {
                        //switchStatus.setText("Switch is currently ON");

                        wallManSwitch.setText("Turn off Wallpaper Changer");

                        schedule = (EditText) findViewById(R.id.num);
                        if (null != schedule.getText()) {
                            int time = Integer.parseInt(schedule.getText().toString());
                            interval = time * 1000;
                        }
                        System.out.println("Interval:" + interval);

                        mytimer = new Timer();
                        myWallpaperManager = WallpaperManager.getInstance(Scheduler.this);

                        //ImageDBHandler imageDBHandler = new ImageDBHandler(context);

                        AsyncWallPaperScheduler wallPaperSync = new AsyncWallPaperScheduler();

                        wallPaperSync.execute();




                    } else {
                        //switchStatus.setText("Switch is currently OFF");


                        wallManSwitch.setText("Turn on Wallpaper Changer");

                        mytimer.cancel();

                    }

                }
            });

        }


    }




    private class AsyncWallPaperScheduler extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                //myWallpaperManager.setResource(R.drawable.one);


            while(!uriList.isEmpty()) {
                 for (String uri : uriList) {

                     intent.setData(Uri.parse(uri));
//                    Uri curi = Uri.parse(intent.getData().toString());
//                    getContentResolver().takePersistableUriPermission(curi, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(curi));
                     Uri curi = Uri.parse(intent.getData().toString());
                     getApplicationContext().grantUriPermission(getPackageName(), curi, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                     //final int takeFlags = intent.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                     getApplicationContext().getContentResolver().takePersistableUriPermission(curi, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                     ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(curi, "r");
                     FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                     Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                     myWallpaperManager.setBitmap(bitmap);
                     Thread.sleep(interval);
     }
 }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "hello";
        }
    }


}
