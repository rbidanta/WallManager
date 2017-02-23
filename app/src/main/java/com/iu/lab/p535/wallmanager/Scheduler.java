package com.iu.lab.p535.wallmanager;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;

import java.io.FileDescriptor;
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

                    if(isChecked){
                        //switchStatus.setText("Switch is currently ON");

                        wallManSwitch.setText("Turn off Wallpaper Changer");

                        schedule = (EditText) findViewById(R.id.num);
                        if(null != schedule.getText()) {
                            int time = Integer.parseInt(schedule.getText().toString());
                            interval = time * 1000;
                        }
                        System.out.println("Interval:"+interval);

                        mytimer=new Timer();
                        myWallpaperManager=WallpaperManager.getInstance(Scheduler.this);

                        //ImageDBHandler imageDBHandler = new ImageDBHandler(context);




                        try {
                            //myWallpaperManager.setResource(R.drawable.one);

                            while (!uriList.isEmpty()) {
                                for (String uri : uriList) {
                                    //for (int i = 0; i < uriList.size(); i++){

                                /*intent.setData(Uri.parse(uri));
                                Uri curi = Uri.parse(intent.getData().toString());
                                getContentResolver().takePersistableUriPermission(curi,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(curi));*/

                                    //Uri curi = Uri.parse(uriList.get(i));
                                    Uri curi = Uri.parse(uri);
                                    getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(), curi, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    //getApplicationContext().getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    ParcelFileDescriptor parcelFileDescriptor =
                                            getContentResolver().openFileDescriptor(curi, "r");
                                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

                                    myWallpaperManager.setBitmap(bitmap);

                                 /*   if (i == uriList.size()-1){
                                        i = 0;

                                    }*/
                                    Thread.sleep(interval);


                                }
                            }





                            mytimer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                       /* if(prev==1){
                            //drawable = getResources().getDrawable(R.drawable.two);
                            try {
                                myWallpaperManager.setResource(R.drawable.two);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prev=2;
                        }
                        else if(prev==2){
                            try {
                                myWallpaperManager.setResource(R.drawable.three);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prev=3;
                        }
                        else{
                            try {
                                myWallpaperManager.setResource(R.drawable.four);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prev=1;
                        }*/


                           /* Bitmap wallpaper=((BitmapDrawable)drawable).getBitmap();

                            try {
                                myWallpaperManager.setBitmap(wallpaper);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                                }
                            }, 100, interval);


                        } /*catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }






                    }else{
                        //switchStatus.setText("Switch is currently OFF");



                        wallManSwitch.setText("Turn on Wallpaper Changer");

                        mytimer.cancel();

                    }

                }
            });

            mytimer=new Timer();
            //myWallpaperManager=WallpaperManager.getInstance(Scheduler.this);

            //check the current state before we display the screen
            if(wallManSwitch.isChecked()){
                System.out.println("If the switch is ON");
                //switchStatus.setText("Switch is currently ON");
                try {
                    //myWallpaperManager.setResource(R.drawable.one);
                    mytimer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                       /* if(prev==1){
                            //drawable = getResources().getDrawable(R.drawable.two);
                            try {
                                myWallpaperManager.setResource(R.drawable.two);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prev=2;
                        }
                        else if(prev==2){
                            try {
                                myWallpaperManager.setResource(R.drawable.three);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prev=3;
                        }
                        else{
                            try {
                                myWallpaperManager.setResource(R.drawable.four);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prev=1;
                        }*/


                           /* Bitmap wallpaper=((BitmapDrawable)drawable).getBitmap();

                            try {
                                myWallpaperManager.setBitmap(wallpaper);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                        }
                    }, 100, interval);


                } /*catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else {
                //switchStatus.setText("Switch is currently OFF");
                mytimer.cancel();
            }
        }




    }



}
