package com.xek6ae.PurinApp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xek6ae on 01.10.14.
 */
public class SearchActivity extends Activity {

    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        String searchkey = getIntent().getExtras().getString("SEARCHKEY");

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ergebnisse für '"+searchkey+"'");

        //Toast.makeText(getBaseContext(),searchkey, Toast.LENGTH_LONG).show();
        ArrayList<String> list = loadSearchContent(searchkey);
        listView = (ListView)findViewById(R.id.search_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }

    public ArrayList<String> loadSearchContent(String search){
        SQLiteHandler db = new SQLiteHandler(getBaseContext());
        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }
        return db.searchLabel(search);
    }
}