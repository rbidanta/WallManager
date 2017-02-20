package com.iu.lab.p535.wallmanager;

import android.content.ClipData;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;


public class WallManMain extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 2;
    public static final String IMAGE_URI_LIST = "LIST_OF_SELECTED_IMAGE_URIS";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.print("---onCreate---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_man_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void addWallpaper(View view) {

        System.out.println("addWallpaper 1");

        System.out.println("Build Version=" + Build.VERSION.SDK_INT);


        Intent imageintent = new Intent();
        // Show only images, no videos or anything else
        imageintent.setType("image/*");
        imageintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageintent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(imageintent, "Select Picture"), PICK_IMAGE_REQUEST);

        /*Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(chooseIntent, 2);*/


        System.out.println("addWallpaper 2");
    }

    public void takePicture(View view) {


    }

    public void appSettings(View view) {


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println(" In Activity Result");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {

            ArrayList<String> uri_Al = new ArrayList<String>();


            if (null != data.getData()) {

                Uri uri = data.getData();

                uri_Al.add(uri.toString());


            } else if (null != data.getClipData()) {

                System.out.println(" Multiple Images Selected ");

                ClipData selectedImages = data.getClipData();

                for (int i = 0; i < selectedImages.getItemCount(); i++) {

                    ClipData.Item item = selectedImages.getItemAt(i);
                    Uri uri = item.getUri();
                    uri_Al.add(uri.toString());

                }

            }

            // Add the Images URI to the database

            Intent intent = new Intent(this, AddWallpaper.class);
            intent.putStringArrayListExtra(IMAGE_URI_LIST, uri_Al);
            startActivity(intent);

        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WallManMain Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
