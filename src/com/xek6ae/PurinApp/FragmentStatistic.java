package com.xek6ae.PurinApp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xek6ae on 02.09.14.
 */
public class FragmentStatistic extends Fragment implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private ListView listView;
    private String[] auswahl = {"Heute", "Letzten 10 Tage", "Letzten Monat"};
    private StatisticListViewAdapter adapterList;
    private ArrayList<String[]> arrayList = new ArrayList<String[]>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, auswahl);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) getActivity().findViewById(R.id.statistic_spinner);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);



        listView = (ListView) getActivity().findViewById(R.id.statistic_list);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        arrayList = loadDataContent(position);
        adapterList = new StatisticListViewAdapter(getActivity(), arrayList);
        listView.setAdapter(adapterList);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public ArrayList<String[]> loadDataContent(int mode){
        SQLiteHandler db = new SQLiteHandler(getActivity());
        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }
        return db.getDateValues(mode);
    }
}