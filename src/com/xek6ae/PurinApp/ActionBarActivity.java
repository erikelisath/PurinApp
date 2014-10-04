package com.xek6ae.PurinApp;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xek6ae on 01.09.14.
 */
public class ActionBarActivity extends Activity {



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabSelect = actionBar.newTab().setText("Auswahl");
        ActionBar.Tab tabSearch = actionBar.newTab().setText("Suche");
        ActionBar.Tab tabStatistic = actionBar.newTab().setText("Statistik");

        Fragment fragmentSelect = new FragmentSelect();
        Fragment fragmentSearch = new FragmentSearch();
        Fragment fragmentCalendar = new FragmentStatistic();

        tabSelect.setTabListener(new ActionBarTabsListener(fragmentSelect));
        tabSearch.setTabListener(new ActionBarTabsListener(fragmentSearch));
        tabStatistic.setTabListener(new ActionBarTabsListener(fragmentCalendar));

        actionBar.addTab(tabSelect);
        actionBar.addTab(tabSearch);
        actionBar.addTab(tabStatistic);
        Log.d("XEK", "ActionBar created");



        //send bundle with list to Select/Search Fragment
        ArrayList<String> list = loadDatabaseInList();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("SELECT",list);
        fragmentSelect.setArguments(bundle);
        fragmentSearch.setArguments(bundle);
        Log.d("XEK", "ActionBar put Bundle");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_update:
                return true;
            case R.id.action_uber:
                startActivity(new Intent(this, UberActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    protected class ActionBarTabsListener implements ActionBar.TabListener{
        Fragment fragment;

        public ActionBarTabsListener(Fragment frag){
            fragment = frag;
            Log.d("XEK", "Fragment: "+frag.toString());
        }
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            Log.d("XEK", "onTabSelected: " + tab.getText().toString());
            ft.replace(R.id.fragment_container, fragment, tab.getText().toString());
            if(ft.isAddToBackStackAllowed()) ft.addToBackStack(tab.getText().toString());
            //TODO: überprüfen : ft.add nur mit ft.remove oder ft.replace allein
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            Log.d("XEK", "onTabUnselected: "+tab.getText().toString());

            //ft.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            Log.d("XEK", "onTabReselected: " + tab.getText().toString());
        }
    }

    public ArrayList<String> loadDatabaseInList(){
        SQLiteHandler db = new SQLiteHandler(getBaseContext());
        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }
        return db.getAllLabelsArrayList();
    }
}
