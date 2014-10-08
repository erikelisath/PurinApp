package com.xek6ae.PurinApp;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by xek6ae on 02.09.14.
 */
public class FragmentSearch extends Fragment {

    public Button buttonFleisch;
    public Button buttonFisch;
    public Button buttonGemuese;
    public Button buttonObst;
    public Button buttonGetreide;
    public Button buttonMilch;
    public Button buttonPurinL;
    public Button buttonPurinM;
    public Button buttonPurinS;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("XEK", "onCreate Search");
        //TODO: was hier her?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("XEK", "onCreateView Search");
        return inflater.inflate(R.layout.fragment_search, container, false);
        //TODO: Views vorbereiten
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d("XEK", "onActivityCreated Search");
        SearchView search = (SearchView) getActivity().findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SEARCHKEY", query);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        buttonFisch = (Button)getActivity().findViewById(R.id.search_buttonFisch);
        buttonFisch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String type = "Fisch";
                filterEvent(type);
            }
        });
        buttonFleisch = (Button)getActivity().findViewById(R.id.search_buttonFleisch);
        buttonFleisch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Fleisch";
                filterEvent(type);
            }
        });
        buttonGemuese = (Button)getActivity().findViewById(R.id.search_buttonGemuese);
        buttonGemuese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Gemüse";
                filterEvent(type);
            }
        });
        buttonGetreide = (Button)getActivity().findViewById(R.id.search_buttonGetreide);
        buttonGetreide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Getreide";
                filterEvent(type);
            }
        });
        buttonMilch = (Button)getActivity().findViewById(R.id.search_buttonMilch);
        buttonMilch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Milchprodukt";
                filterEvent(type);
            }
        });
        buttonObst = (Button)getActivity().findViewById(R.id.search_buttonObst);
        buttonObst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Obst";
                filterEvent(type);
            }
        });

    }

    public void filterEvent(String type){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("FILTERKEY", type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}