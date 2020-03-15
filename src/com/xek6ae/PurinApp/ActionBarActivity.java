package com.xek6ae.PurinApp;

import android.app.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xek6ae on 01.09.14.
 */
public class ActionBarActivity extends FragmentActivity implements ActionBar.TabListener {

    ViewPager viewPager;
    ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //send bundle with list to Select/Search Fragment
        ArrayList<String> list = loadDatabaseInList();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("SELECT",list);


        Log.d("XEK", "ActionBar put Bundle");

        viewPager = (ViewPager)findViewById(R.id.fragment_container);
        ViewPagerAdapter viewAdapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle);
        viewPager.setAdapter(viewAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                Log.d("XEK", "onPageScroll "+i+","+i2);
            }

            @Override
            public void onPageSelected(int i) {
                Log.d("XEK", "onPageSelected "+i);
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabSelect = actionBar.newTab().setText("Auswahl").setTabListener(this);
        ActionBar.Tab tabSearch = actionBar.newTab().setText("Suche").setTabListener(this);
        ActionBar.Tab tabStatistic = actionBar.newTab().setText("Statistik").setTabListener(this);

        //Fragment fragmentSelect = new FragmentSelect();
        //Fragment fragmentSearch = new FragmentSearch();
        //Fragment fragmentCalendar = new FragmentStatistic();

        //tabSelect.setTabListener(new ActionBarTabsListener(fragmentSelect));
        //tabSearch.setTabListener(new ActionBarTabsListener(fragmentSearch));
        //tabStatistic.setTabListener(new ActionBarTabsListener(fragmentCalendar));

        actionBar.addTab(tabSelect);
        actionBar.addTab(tabSearch);
        actionBar.addTab(tabStatistic);
        Log.d("XEK", "ActionBar created");

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("XEK", "Main Activity onResume()");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //swipe animation
        if(sharedPreferences.getBoolean(SettingActivity.PREF_FRAG_ANI_KEY, false)){
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }
        //todo: anderen pagetransformer nutzen?
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
                startActivity(new Intent(this, SettingActivity.class));
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
    /**
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
    }*/

    public ArrayList<String> loadDatabaseInList(){
        SQLiteHandler db = new SQLiteHandler(getBaseContext());
        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }
        return db.getAllLabelsArrayList();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("XEK", "Tab Selected "+tab.getText());
        viewPager.setCurrentItem(tab.getPosition());
        View focus = getCurrentFocus();
        if(focus != null){
            hideKeyboard(focus);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    //for more fun, yey!
    protected class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public void hideKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
