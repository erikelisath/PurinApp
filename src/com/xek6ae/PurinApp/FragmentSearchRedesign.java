package com.xek6ae.PurinApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by xek6ae on 02.09.14.
 */
public class FragmentSearchRedesign extends Fragment {

    public AutoCompleteTextView autoComplte;
    public Bundle bundle;
    public Button buttonFleisch;
    public Button buttonFisch;
    public Button buttonGemuese;
    public Button buttonObst;
    public Button buttonGetreide;
    public Button buttonMilch;
    public Button buttonPurinL;
    public Button buttonPurinM;
    public Button buttonPurinS;

    public GridView gridView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("XEK", "onCreate Search");
        //TODO: was hier her?
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("XEK", "onCreateView Search");
        return inflater.inflate(R.layout.fragment_search_redesign, container, false);
        //TODO: Views vorbereiten
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d("XEK", "onActivityCreated Search");

        //## Suchfeld
        ArrayList<String> list = bundle.getStringArrayList("SELECT");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        autoComplte = (AutoCompleteTextView)getActivity().findViewById(R.id.autoCompleteTextView);
        autoComplte.setAdapter(adapter);
        autoComplte.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            // Start search if IME_ACTION_SEARCH pressed
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    Bundle bundle = new Bundle();
                    if (!autoComplte.getText().toString().isEmpty()){
                        bundle.putString("SEARCHKEY", autoComplte.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "Search it! "+autoComplte.getText(),Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(),"Bitte Namen eingeben!",Toast.LENGTH_LONG).show();
                    }
                    handled = true;
                }
                return handled;
            }
        });
        autoComplte.addTextChangedListener(new TextWatcher() {
            boolean hide = true; //glaube bekomme noch ärger mit der Style-Police-of-programmer

            // show and hide the cancel button
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("XEK", "onTextChanged"+" start: "+start+" count: "+count+" before: "+before);
                if(start == 0 && count == 1){
                    Log.d("XEK", "show X");
                    //TODO: neues cancel bild einfügen mit neuer position etc
                    autoComplte.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.draw_cancel3), null);
                    hide = false;
                }
                if(start == 0 && count == 0){
                    Log.d("XEK", "hide X");
                    autoComplte.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    hide = true;
                }
                autoComplte.setOnTouchListener(new View.OnTouchListener() {
                    // when pressed in area of the cancel button, it cancel
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_RIGHT = 2;
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            if(!hide){
                                if(event.getRawX() >= (autoComplte.getRight() - (autoComplte.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())*2)){
                                    Log.d("XEK", "Touched X"+autoComplte.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width());
                                    autoComplte.setText("");
                                    return true;
                                }
                            }

                        }
                        return false;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing to do
            }
        });

        //## grid view
        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("XEK", "KATEGORIE"+position);
                String type;
                int range;
                switch (position){
                    case 0: type = "Fisch";
                        filterEvent(type);
                        break;
                    case 1: type = "Fleisch";
                        filterEvent(type);
                        break;
                    case 2: type = "Milchprodukt";
                        filterEvent(type);
                        break;
                    case 3: type = "Getreide";
                        filterEvent(type);
                        break;
                    case 4: type = "Gemüse";
                        filterEvent(type);
                        break;
                    case 5: type = "Obst";
                        filterEvent(type);
                        break;
                    case 6: range = SearchActivity.PURIN_S;
                        filterEvent(range);
                        break;
                    case 7: range = SearchActivity.PURIN_M;
                        filterEvent(range);
                        break;
                    case 8: range = SearchActivity.PURIN_L;
                        filterEvent(range);
                        break;
                }
            }
        });

        /**
        //## Suchfeld -end

        //## Filterbuttons
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
        //## Filterbuttons -end

        //## Sortierbuttons nach Purinmenge
        buttonPurinS = (Button)getActivity().findViewById(R.id.search_buttonPurinS);
        buttonPurinS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = SearchActivity.PURIN_S;
                filterEvent(type);
            }
        });
        buttonPurinM = (Button)getActivity().findViewById(R.id.search_buttonPurinM);
        buttonPurinM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = SearchActivity.PURIN_M;
                filterEvent(type);
            }
        });
        buttonPurinL = (Button)getActivity().findViewById(R.id.search_buttonPurinL);
        buttonPurinL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = SearchActivity.PURIN_L;
                filterEvent(type);
            }
        });
        //## Sortierbuttons -end
        */
    }

    public void filterEvent(String type){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("FILTERKEY", type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void filterEvent(int type){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SORTKEY", type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}