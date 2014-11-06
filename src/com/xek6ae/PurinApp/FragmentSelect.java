package com.xek6ae.PurinApp;

import android.app.*;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xek6ae on 02.09.14.
 */
class FragmentSelect extends Fragment implements AdapterView.OnItemSelectedListener{

    private int purinValue;
    private int purinInput;
    private Bundle bundle;
    private Bundle dialogbundle;
    private ProgressDialog progressDialog;
    private FragmentManager fm;
    private SpinnerDialog progressSpinner;

    private Spinner s1;
    private TextView textPurin;
    private TextView textCategory;
    private TextView textPurinValue;
    private TextView textHarnValue;
    private TextView textPurinSmall;
    private TextView textPurinCalc;
    private ImageView imageLights;
    private EditText inputPurinValue;
    private Button buttoncalculate;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("XEK", "onCreate Select");
        //TODO: was hier her?
        bundle = this.getArguments();
        dialogbundle  = new Bundle();
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("XEK", "onCreateView Select");
        progressSpinner = new SpinnerDialog();
        progressSpinner.show(fm, null);
        return inflater.inflate(R.layout.fragment_select, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d("XEK", "onActivityCreate Select");

        //## recived bundle from main Activity ( arrayList )
        ArrayList<String> arrayList = bundle.getStringArrayList("SELECT");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1 = (Spinner)getActivity().findViewById(R.id.select_spinner);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(this);

        textPurin = (TextView)getActivity().findViewById(R.id.select_textPurinName);
        textCategory = (TextView)getActivity().findViewById(R.id.select_textCategory);
        textPurinValue = (TextView)getActivity().findViewById(R.id.select_textPurinValue);
        textHarnValue = (TextView)getActivity().findViewById(R.id.select_textHarnValue);
        textPurinSmall = (TextView)getActivity().findViewById(R.id.select_textPurinNameSmall);
        imageLights = (ImageView)getActivity().findViewById(R.id.select_pictogramAttention);
        inputPurinValue = (EditText)getActivity().findViewById(R.id.select_inputNumber);
        inputPurinValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_GO){
                    calculateEvent(v);
                    handled = true;
                }
                return handled;
            }
        });
        buttoncalculate = (Button)getActivity().findViewById(R.id.select_buttonCalculate);
        buttoncalculate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                calculateEvent(v);
            }
        });
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        SQLiteHandler db = new SQLiteHandler(getActivity());
        String label = s1.getSelectedItem().toString();
        String item[];

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

        dialogbundle.putString("NAME", label);
        textPurin.setText(label);
        textPurinSmall.setText(item[1]);
        textCategory.setText(item[2]);
        textPurinValue.setText(item[3]);
        textHarnValue.setText(item[4]);

        purinValue = Integer.parseInt(item[3]);
        if(purinValue>50){
            imageLights.setBackgroundColor(getResources().getColor(R.color.red));
            imageLights.setContentDescription("rot");
        }else if(purinValue>20){
            imageLights.setBackgroundColor(getResources().getColor(R.color.yellow));
            imageLights.setContentDescription("gelb");
        }else{
            imageLights.setBackgroundColor(getResources().getColor(R.color.green));
            imageLights.setContentDescription("grün");
        }

        progressSpinner.dismiss();
        inputPurinValue.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("XEK", "Nothing selected");
    }

    public void calculateEvent (View v){
        String tmp = inputPurinValue.getText().toString();
        if(!(tmp.isEmpty())){
            purinInput = Integer.parseInt(inputPurinValue.getText().toString());

            int actuallyValue = (purinInput*purinValue)/100;

            if(actuallyValue<=99999){
                //textPurinCalc.setText(String.valueOf(actuallyValue)+" mg Purin");
                dialogbundle.putString("CALC", String.valueOf(actuallyValue));
                dialogbundle.putString("INPUT", inputPurinValue.getText().toString());
                CalcDialog dialog = new CalcDialog();
                dialog.setArguments(dialogbundle);
                dialog.show(fm,"Dialog");
            }else{
                Toast.makeText(v.getContext(), "Value too hight, control input.", Toast.LENGTH_SHORT).show();
            }
        }else{
            purinInput = 0;
            Toast.makeText(v.getContext(), "Please insert a value.", Toast.LENGTH_SHORT).show();
        }
    }

}