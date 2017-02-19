package com.iu.lab.p535.wallmanager;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;


public class WallManMain extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 2;
    public static final String IMAGE_URI = "SELECTED_IMAGE_URI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.print("---onCreate---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_man_main);
    }


    public void addWallpaper(View view){

        System.out.println("addWallpaper 1");

        System.out.println("Build Version="+Build.VERSION.SDK_INT);


        Intent imageintent = new Intent();
        // Show only images, no videos or anything else
        imageintent.setType("image/*");
        imageintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        imageintent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(imageintent, "Select Picture"), PICK_IMAGE_REQUEST);

        /*Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(chooseIntent, 2);*/


        System.out.println("addWallpaper 2");
    }

    public void takePicture(View view){


    }

    public void appSettings(View view){


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println(" In Activity Result");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

                Intent intent = new Intent(this, AddWallpaper.class);

                intent.putExtra(IMAGE_URI,uri.toString());
                startActivity(intent);

        }
    }


}
