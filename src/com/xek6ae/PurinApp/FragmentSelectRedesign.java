package com.xek6ae.PurinApp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by xek6ae on 02.09.14.
 */
public class FragmentSelectRedesign extends ListFragment{

    private Animation animDown;
    private Animation animUp;
    private Animation animFade;
    private Animation animAppear;
    private Animation animButton;

    private RelativeLayout selectMenu;
    private FrameLayout selectMenuFade;
    private LinearLayout menuHeader;
    private TextView selectPurinName;
    private TextView selectPurinValue;
    private TextView selectHarnValue;
    private EditText inputValue;
    private ImageButton addButton;

    private ArrayList<String> arrayList;
    private Bundle bundle;
    private SelectMenu menu;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("XEK", "onCreate Select");
        //TODO: was hier her?
        bundle = this.getArguments();
        /**
        animDown = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_down);
        animUp = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_up);
        animFade = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_fade);
        animAppear = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_appear);
        animButton = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_button_hide);         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("XEK", "onCreateView Select");
        return inflater.inflate(R.layout.fragment_select_redesign, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d("XEK", "onActivityCreate Select");

        //## recived bundle from main Activity ( arrayList )
        arrayList = bundle.getStringArrayList("SELECT");
        //listView = (ListView)getActivity().findViewById(android.R.id.list);
        //listView.setFastScrollEnabled(true);
        menu = new SelectMenu(getView());
        menu.setAnimation();
        //TODO: List Adapter benutzen: https://github.com/twotoasters/SectionCursorAdapter
        setListAdapter(new SelectListAdapter(getActivity(), arrayList));

        Log.d("XEK", " ON ACTIVITY SELECT #########");/**
        selectMenu = (RelativeLayout)getActivity().findViewById(R.id.select_menu);
        selectMenu.setVisibility(View.GONE);
        selectMenu.setOnTouchListener(new View.OnTouchListener() {
            float startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        startY = event.getY();
                        Log.d("XEK", Float.toString(startY));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        float endY = event.getY();
                        Log.d("XEK", Float.toString(endY));
                        //hide selectMenu when touchgesture down
                        if(endY > startY && selectMenu.getVisibility() == View.VISIBLE){
                            Toast.makeText(getActivity(), "DOWN", Toast.LENGTH_SHORT).show();
                            selectMenu.startAnimation(animDown);
                            selectMenu.setVisibility(View.GONE);
                            selectMenuFade.startAnimation(animFade);
                            selectMenuFade.setVisibility(View.GONE);
                            addButton.startAnimation(animButton);
                            addButton.setVisibility(View.GONE);
                            if(v != null){
                                hideKeyboard(v);
                            }
                        }
                        break;
                    }
                }
                return true;
            }
        });

        selectMenuFade = (FrameLayout)getActivity().findViewById(R.id.select_menu_fade);
        selectMenuFade.setVisibility(View.GONE);
        selectMenuFade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP && selectMenu.getVisibility() == View.VISIBLE){
                    selectMenu.startAnimation(animDown);
                    selectMenu.setVisibility(View.GONE);
                    selectMenuFade.startAnimation(animFade);
                    selectMenuFade.setVisibility(View.GONE);
                    addButton.startAnimation(animButton);
                    addButton.setVisibility(View.GONE);
                    if(v != null){
                        hideKeyboard(v);
                    }
                }
                return true;
            }
        });

        selectPurinName = (TextView)getActivity().findViewById(R.id.select_menu_header_text);
        menuHeader = (LinearLayout)getActivity().findViewById(R.id.select_menu_header);
        selectPurinValue = (TextView)getActivity().findViewById(R.id.select_menu_purinvalue);
        selectHarnValue = (TextView)getActivity().findViewById(R.id.select_menu_harnvalue);
        inputValue = (EditText)getActivity().findViewById(R.id.select_menu_edittext);
        addButton = (ImageButton)getActivity().findViewById(R.id.addButton);
        addButton.setVisibility(View.GONE);    */
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("XEK", "onPause Select");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("XEK", "onDestroyView Select");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        SQLiteHandler db = new SQLiteHandler(getActivity());
        String label = arrayList.get(position);
        final String item[];

        //TODO: logik überprüpfen - überflüssig?
        //## db Zugriff minimieren:
        //## ist bundle leer, wenn nicht ist label und bundle_item_1 gleich?
        //## falls, bundle wird übernommen
        //## sonst, wird DB zugegriffen
        bundle = this.getArguments();
        String backuparrays[] = bundle.getStringArray("BACKUPARRAYS");
        if(backuparrays!=null){
            if(label.equals(backuparrays[1])){
                item = backuparrays;
                Log.d("XEK", label+"="+backuparrays[1]+" => backuparrays -> item");
            }else{
                item = db.getDataArray(label);
                Log.d("XEK", "label != bundle => DB zugriff");
            }
        }else{
            item = db.getDataArray(label);
            Log.d("XEK", "!bundle => DB zugriff");
        }
        bundle.putStringArray("BACKUPARRAYS", item);
        onSaveInstanceState(bundle);
        menu.getMenu(item);
        /**
        //## header color
        int purinValue = Integer.parseInt(item[3]);
        if(purinValue>50){
            menuHeader.setBackgroundColor(getResources().getColor(R.color.red));
            menuHeader.setContentDescription("rot");
        }else if(purinValue>20){
            menuHeader.setBackgroundColor(getResources().getColor(R.color.yellow));
            menuHeader.setContentDescription("gelb");
        }else{
            menuHeader.setBackgroundColor(getResources().getColor(R.color.green));
            menuHeader.setContentDescription("grün");
        }

        //## name elements
        selectPurinName.setText(label);
        selectPurinName.setShadowLayer(3,1,1,getResources().getColor(R.color.theme_background));

        //## calc values
        selectPurinValue.setText(item[3]);
        selectHarnValue.setText(item[4]);
        inputValue.setText("");
        inputValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!inputValue.getText().toString().isEmpty()){
                    int purin = Integer.parseInt(item[3]);
                    int harn = Integer.parseInt(item[4]);
                    int value = Integer.parseInt(inputValue.getText().toString());
                    int purinValue = (purin*value)/100;
                    int harnValue = (harn*value)/100;

                    if(purinValue<=99999 && harnValue<=99999){
                        selectPurinValue.setText(String.valueOf(purinValue));
                        selectHarnValue.setText(String.valueOf(harnValue));
                        if(v != null){
                            hideKeyboard(v);
                        }
                    }else{
                        Toast.makeText(getActivity(), "Wert zu groß, Eingabe kontrollieren.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //100g ist default
                    Toast.makeText(getActivity(), "Bitte Wert eingeben.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //## set menu visible
        selectMenu.setVisibility(View.VISIBLE);
        selectMenu.startAnimation(animUp);
        selectMenuFade.setVisibility(View.VISIBLE);
        selectMenuFade.startAnimation(animAppear);

        //## add button
        addButton.setVisibility(View.VISIBLE);
        addButton.startAnimation(animUp);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(getActivity(), selectPurinValue.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        */
        Log.d("XEK", "DIALOG");
    }

    public void hideKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}