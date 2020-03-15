package com.xek6ae.PurinApp;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

/**
 * Created by xek6ae on 11.01.15.
 */
public class SelectMenu {

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

    private View view;
    private String[] item;

    public SelectMenu(View v){
        view = v;

        selectMenu = (RelativeLayout)view.findViewById(R.id.select_menu);
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
                            Toast.makeText(view.getContext(), "DOWN", Toast.LENGTH_SHORT).show();
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

        selectMenuFade = (FrameLayout)view.findViewById(R.id.select_menu_fade);
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

        selectPurinName = (TextView)view.findViewById(R.id.select_menu_header_text);
        menuHeader = (LinearLayout)view.findViewById(R.id.select_menu_header);
        selectPurinValue = (TextView)view.findViewById(R.id.select_menu_purinvalue);
        selectHarnValue = (TextView)view.findViewById(R.id.select_menu_harnvalue);
        inputValue = (EditText)view.findViewById(R.id.select_menu_edittext);
        addButton = (ImageButton)view.findViewById(R.id.addButton);
        addButton.setVisibility(View.GONE);

    }

    public void getMenu(String[] i){
        item = i;

        int purinValue = Integer.parseInt(item[3]);
        if(purinValue>50){
            menuHeader.setBackgroundColor(view.getResources().getColor(R.color.red));
            menuHeader.setContentDescription("rot");
        }else if(purinValue>20){
            menuHeader.setBackgroundColor(view.getResources().getColor(R.color.yellow));
            menuHeader.setContentDescription("gelb");
        }else{
            menuHeader.setBackgroundColor(view.getResources().getColor(R.color.green));
            menuHeader.setContentDescription("grün");
        }

        //## name elements
        selectPurinName.setText(item[1]);
        selectPurinName.setShadowLayer(3,1,1,view.getResources().getColor(R.color.theme_background));

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
                        if(addButton.getVisibility()==View.GONE){
                            addButton.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(view.getContext(), "Wert zu groß, Eingabe kontrollieren.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //100g ist default
                    Toast.makeText(view.getContext(), "Bitte Wert eingeben.", Toast.LENGTH_SHORT).show();
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
                SQLiteHandler db = new SQLiteHandler(view.getContext());
                db.insertValue(Integer.parseInt(selectPurinValue.getText().toString()));
                Log.d("XEK", db.getDatabaseName());
                addButton.setVisibility(View.GONE);
                Toast.makeText(view.getContext(), "Hinzugefügt", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAnimation(){
        animDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_down);
        animUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_up);
        animFade = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_fade);
        animAppear = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_appear);
        animButton = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_button_hide);
    }

    public void hideKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
