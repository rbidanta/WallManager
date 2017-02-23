package com.iu.lab.p535.wallmanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Rashmi on 2/19/17.
 */

public class ImageViewAdaptor extends BaseAdapter {

    private Context context;
    private ArrayList<ImageView> imageViewList;


    public ImageViewAdaptor(Context ctxt, ArrayList<ImageView> iVList) {

        context = ctxt;
        imageViewList = iVList;
        // TODO Auto-generated constructor stub
    }


    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        /*if(convertView == null)
        {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(160,160));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        }
        else
        {
            imageView = (ImageView) convertView;
        }*/

        imageView = imageViewList.get(position);


        //imageView.setImageBitmap(bitmap);

        return imageView;
    }
}
