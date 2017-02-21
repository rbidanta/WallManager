package com.iu.lab.p535.wallmanager;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler extends AppCompatActivity {

    //private TextView switchStatus;
    private Switch mySwitch;

    Timer mytimer;
    int interval;
    Drawable drawable;
    WallpaperManager myWallpaperManager;
    int prev=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        EditText num = (EditText) findViewById(R.id.num);
        int time = Integer.parseInt(num.getText().toString());
        interval = time*1000;

        //switchStatus = (TextView) findViewById(R.id.);
        mySwitch = (Switch) findViewById(R.id.switch1);

        //set the switch to ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    //switchStatus.setText("Switch is currently ON");
                    mySwitch.setText("Turn off Wallpaper Changer");
                }else{
                    //switchStatus.setText("Switch is currently OFF");
                    mySwitch.setText("Turn on Wallpaper Changer");
                }

            }
        });

        mytimer=new Timer();
        myWallpaperManager=WallpaperManager.getInstance(Scheduler.this);

        //check the current state before we display the screen
        if(mySwitch.isChecked()){
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
