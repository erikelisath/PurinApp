package com.xek6ae.PurinApp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xek6ae on 01.10.14.
 */
public class SearchActivity extends Activity {
    public final static int SEARCH = 0;
    public final static int FILTER = 1;
    public final static int SORT = 2;
    public final static int PURIN_S = 1;
    public final static int PURIN_M = 2;
    public final static int PURIN_L = 3;

    public ListView listView;
    public SelectMenu menu;
    public ArrayList<String[]> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        String searchkey = getIntent().getExtras().getString("SEARCHKEY");
        String filterkey = getIntent().getExtras().getString("FILTERKEY");
        int sortkey = getIntent().getExtras().getInt("SORTKEY");
        Log.d("XEK", "SEARCHKEY "+searchkey);
        Log.d("XEK", "FILTERKEY "+filterkey);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ViewGroup view = (ViewGroup)findViewById(android.R.id.content);
        listView = (ListView) findViewById(R.id.result_list);
        menu = new SelectMenu(findViewById(android.R.id.content));
        menu.setAnimation();

        if(searchkey!=null){
            actionBar.setTitle("Ergebnisse für '"+searchkey+"'");
            list = loadSearchContent(SEARCH, searchkey, 0);
            listView.setAdapter(new SearchListViewAdapter(this, list));
        }
        if(filterkey!=null){
            actionBar.setTitle("Ergebnisse für '"+filterkey+"'");
            list = loadSearchContent(FILTER, filterkey, 0);
            listView.setAdapter(new SearchListViewAdapter(this, list));
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
            listView.setAdapter(new SearchListViewAdapter(this, list));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: lösung finden!!!!
                String[] temp = {"", list.get(position)[0], "", list.get(position)[2], "" };
                menu.getMenu(temp);
            }
        });

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