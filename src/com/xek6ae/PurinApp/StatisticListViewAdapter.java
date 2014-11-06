package com.xek6ae.PurinApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xek6ae on 02.11.14.
 */
public class StatisticListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> list = null;
    DateFormat dateFormat = new DateFormat(DateFormat.DEUTSCH);

    public StatisticListViewAdapter(Context context, ArrayList<String[]> list){
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
        TextView textDatum;
        TextView textPurinWert;

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.statistic_listviewadapter_row, parent, false);
            textDatum = (TextView)convertView.findViewById(R.id.adapter_textDatum);
            textPurinWert = (TextView)convertView.findViewById(R.id.adapter_textPurinwert);
            convertView.setTag(new ViewHolder(textDatum, textPurinWert));
        }else{
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            textDatum = viewHolder.textDatum;
            textPurinWert = viewHolder.textPurinWert;
        }
        String[] string = getItem(position);
        textDatum.setText(dateFormat.getDateFormat(string[2]));
        textPurinWert.setText("Gesamt Purinwert: "+string[0]+" mg/100g");

        return convertView;
    }

    private static class ViewHolder{
        TextView textDatum;
        TextView textPurinWert;

        public ViewHolder(TextView datum, TextView purinwert){
            textDatum = datum;
            textPurinWert = purinwert;
        }
    }
}
