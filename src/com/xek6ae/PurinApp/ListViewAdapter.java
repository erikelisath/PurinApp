package com.xek6ae.PurinApp;

import android.content.Context;
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
    ArrayList<String> list = null;

    public  ListViewAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View row = layoutInflater.inflate(R.layout.list_adapter_row, parent, false);
        TextView textName = (TextView)row.findViewById(R.id.textView);
        TextView textKategorie = (TextView)row.findViewById(R.id.textView2);
        TextView textPurin = (TextView)row.findViewById(R.id.textView3);

        textName.setText(list.get(position));

        return row;
    }
}
