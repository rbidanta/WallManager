package com.iu.lab.p535.wallmanager;
/**
 * Created by jaidev on 02/19/17.
 */
public class Image {

    private int imageID;
    private String uriString;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getPath() {
        return uriString;
    }

    public void setPath(String path) {
        this.uriString = path;
    }

    public Image(){}

}
