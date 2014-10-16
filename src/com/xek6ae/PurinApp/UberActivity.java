package com.xek6ae.PurinApp;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by xek6ae on 12.09.14.
 */
public class UberActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uber_activity);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Über");

        SQLiteHandler db = new SQLiteHandler(getBaseContext());
        TextView textCount = (TextView)findViewById(R.id.uber_counting);
        textCount.setText("The Database list "+db.countEntries()+" Items.");
    }
}