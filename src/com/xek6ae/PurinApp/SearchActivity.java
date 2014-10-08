package com.xek6ae.PurinApp;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xek6ae on 01.10.14.
 */
public class SearchActivity extends ListActivity {
    public final static int SEARCH = 0;
    public final static int FILTER = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        String searchkey = getIntent().getExtras().getString("SEARCHKEY");
        String filterkey = getIntent().getExtras().getString("FILTERKEY");
        ArrayList<String[]> list;
        Log.d("XEK", "SEARCHKEY "+searchkey);
        Log.d("XEK", "FILTERKEY "+filterkey);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(searchkey!=null){
            actionBar.setTitle("Ergebnisse für '"+searchkey+"'");
            list = loadSearchContent(SEARCH, searchkey, null);
            setListAdapter(new ListViewAdapter(this, list));
        }
        if(filterkey!=null){
            actionBar.setTitle("Ergebnisse für '"+filterkey+"'");
            list = loadSearchContent(FILTER, null, filterkey);
            setListAdapter(new ListViewAdapter(this, list));
        }

    }

    public void onListItemClick(ListView l, View v, int position, long id){

    }

    public ArrayList<String[]> loadSearchContent(int mode, String search, String type){
        SQLiteHandler db = new SQLiteHandler(getBaseContext());
        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }
        return db.getDataMode(mode, search, type);
    }
}