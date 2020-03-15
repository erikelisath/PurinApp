package com.xek6ae.PurinApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by xek6ae on 10.01.15.
 */
public class ImageAdapter extends BaseAdapter {

    public Context context;
    public int[] thumbID = {R.drawable.fish_food, R.drawable.fish_food, R.drawable.fish_food,
                            R.drawable.fish_food, R.drawable.fish_food, R.drawable.fish_food,
                            R.drawable.fish_food, R.drawable.fish_food, R.drawable.fish_food};
    public String[] textID = {"Fisch", "Fleisch", "Milchprodukt", "Getreide", "Gemüse", "Obst", "wenig Purin", "erhötes Purin", "viel Purin"};
    public ImageAdapter(Context c){
         context = c;
    }
    @Override
    public int getCount() {
        return thumbID.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return thumbID[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView textView;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_column, parent, false);
            convertView.setTag(R.id.gridView_image, convertView.findViewById(R.id.gridView_image));
            convertView.setTag(R.id.gridView_text, convertView.findViewById(R.id.gridView_text));
        }

        imageView = (ImageView)convertView.getTag(R.id.gridView_image);
        textView = (TextView)convertView.getTag(R.id.gridView_text);

        imageView.setImageResource(thumbID[position]);
        textView.setText(textID[position]);
        return convertView;
    }
}
