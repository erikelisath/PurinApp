package com.xek6ae.PurinApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by xek6ae on 08.11.14.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    Bundle bundle;
    public ViewPagerAdapter(FragmentManager fm, Bundle b){
        super(fm);
        bundle = b;
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        Log.d("XEK", "getItem "+i);
        if(i==0){
            fragment = new FragmentSelectRedesign();
            fragment.setArguments(bundle);
        }if(i==1){
            fragment = new FragmentSearchRedesign();
            fragment.setArguments(bundle);
        }if(i==2){
            fragment = new FragmentStatistic();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
