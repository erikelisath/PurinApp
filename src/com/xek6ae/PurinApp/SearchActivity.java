package com.xek6ae.PurinApp;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xek6ae on 01.10.14.
 */
public class SearchActivity extends ListActivity {
    public final static int SEARCH = 0;
    public final static int FILTER = 1;
    public final static int SORT = 2;
    public final static int PURIN_S = 1;
    public final static int PURIN_M = 2;
    public final static int PURIN_L = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        String searchkey = getIntent().getExtras().getString("SEARCHKEY");
        String filterkey = getIntent().getExtras().getString("FILTERKEY");
        int sortkey = getIntent().getExtras().getInt("SORTKEY");
        ArrayList<String[]> list;
        Log.d("XEK", "SEARCHKEY "+searchkey);
        Log.d("XEK", "FILTERKEY "+filterkey);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(searchkey!=null){
            actionBar.setTitle("Ergebnisse für '"+searchkey+"'");
            list = loadSearchContent(SEARCH, searchkey, 0);
            setListAdapter(new SearchListViewAdapter(this, list));
        }
        if(filterkey!=null){
            actionBar.setTitle("Ergebnisse für '"+filterkey+"'");
            list = loadSearchContent(FILTER, filterkey, 0);
            setListAdapter(new SearchListViewAdapter(this, list));
        }
        if(sortkey!=0){
            if(sortkey==PURIN_S){
                actionBar.setTitle("Ergebnisse für wenig Purin");
            }
            if(sortkey==PURIN_M){
                actionBar.setTitle("Ergebnisse für mittel Purin");
            }
            if(sortkey==PURIN_L){
                actionBar.setTitle("Ergebnisse für viel Purin");
            }
            list = loadSearchContent(SORT, null, sortkey);
            setListAdapter(new SearchListViewAdapter(this, list));
        }

    }

    public void onListItemClick(ListView l, View v, int position, long id){

    }

    public ArrayList<String[]> loadSearchContent(int mode, String search, int type){
        SQLiteHandler db = new SQLiteHandler(getBaseContext());
        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }
        return db.getDataByMode(mode, search, type);
    }
}