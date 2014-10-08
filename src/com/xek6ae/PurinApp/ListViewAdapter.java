package com.xek6ae.PurinApp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by xek6ae on 06.10.14.
 */
public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> list = null;

    public  ListViewAdapter(Context context, ArrayList<String[]> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String[] getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textName;
        TextView textKategorie;
        TextView textPurin;

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_adapter_row, parent, false);
            textName = (TextView)convertView.findViewById(R.id.adapter_textName);
            textKategorie= (TextView)convertView.findViewById(R.id.adapter_textKategorie);
            textPurin= (TextView)convertView.findViewById(R.id.adapter_textPurin);
            convertView.setTag(new ViewHolder(textName, textKategorie, textPurin));
        }else{
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            textName = viewHolder.textName;
            textKategorie = viewHolder.textKategorie;
            textPurin = viewHolder.textPurin;
        }

        String[] string = getItem(position);
        textName.setText(string[0]);
        textKategorie.setText(string[1]);
        textPurin.setText("Purinwert "+string[2]+" mg/100g");

        return convertView;
    }

    private static class ViewHolder{
        TextView textName;
        TextView textKategorie;
        TextView textPurin;

        public ViewHolder(TextView name, TextView kategorie, TextView purin){
            textName = name;
            textKategorie = kategorie;
            textPurin = purin;
        }
    }
}
