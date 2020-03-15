package com.xek6ae.PurinApp;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.ArrayList;

/**
 * Created by xek6ae on 26.12.14.
 */
public class SelectListAdapter extends ArrayAdapter implements SectionIndexer {

    int[] count;
    String[] sections;

    public SelectListAdapter(Context context, ArrayList<String> arrayList){
        super(context, R.layout.select_listviewadapter_row, R.id.text_select_listitem, arrayList);
        ArrayList<String> chars = new ArrayList<String>();
        count = new int[arrayList.size()];
        int j =0;
        String temp;
        for(int i=0; i<arrayList.size();i++){
            temp = arrayList.get(i).substring(0,1);
            if(!chars.isEmpty()){
                if(!(chars.get(chars.size()-1).equals(temp))){
                    chars.add(temp);
                    j++;
                }
            }else{
                chars.add(temp);
            }
            count[i] = j;
        }

        sections = new String[chars.size()];
        sections = chars.toArray(new String[chars.size()]);
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        Log.d("XEK", "getPositionForSelection: "+section+" "+count[section]+" "+sections[section]);
        return count[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        Log.d("XEK", "getSectionForPosition: "+position);
        return count[position];
    }
}
