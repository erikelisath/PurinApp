package com.xek6ae.PurinApp;

import android.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Bundle dialogbundle;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

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
    private ImageButton buttonadd;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("XEK", "onCreate Select");
        //TODO: was hier her?
        dialogbundle  = new Bundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("XEK", "onCreateView Select");
        progressDialog = new ProgressDialog(container.getContext(), R.style.LoadingDialog);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.setContentView(R.layout.dialog_progressbar);
        progressDialog.setTitle(null);
        progressDialog.show();
        //TODO: custom Progressbar dialog
        return inflater.inflate(R.layout.fragment_select, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d("XEK", "onActivityCreate Select");


        progressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //recived bundle from main Activity ( arrayList )
        Bundle bundle = this.getArguments();
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
        //textPurinCalc = (TextView)getActivity().findViewById(R.id.select_textPurin2);
        imageLights = (ImageView)getActivity().findViewById(R.id.select_pictogramAttention);
        inputPurinValue = (EditText)getActivity().findViewById(R.id.select_inputNumber);


        buttoncalculate = (Button)getActivity().findViewById(R.id.select_buttonCalculate);
        //buttonadd = (ImageButton)getActivity().findViewById(R.id.button);

        buttoncalculate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String tmp = inputPurinValue.getText().toString();
                if(!(tmp.isEmpty())){
                    purinInput = Integer.parseInt(inputPurinValue.getText().toString());

                    FragmentManager fm = getFragmentManager();

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

                //buttonadd.setVisibility(View.VISIBLE);




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

        item = db.getDataArray(label);

        dialogbundle.putString("NAME", label);
        textPurin.setText(label);
        textPurinSmall.setText(item[1]);
        //textPurinCalc.setText(item[1]);
        //textCategory.setText(db.getDataString("SELECT kategorie FROM gericht WHERE name='"+label+"';"));
        //textPurinValue.setText(db.getDataString("SELECT purinwert FROM gericht WHERE name='"+label+"';"));
        //textHarnValue.setText(db.getDataString("SELECT harnsaeurewert FROM gericht WHERE name='"+label+"';"));
        textCategory.setText(item[2]);
        textPurinValue.setText(item[3]);
        textHarnValue.setText(item[4]);


        purinValue = Integer.parseInt(item[3]);
        if(purinValue>50){
            imageLights.setImageResource(R.drawable.red);
        }else if(purinValue>20){
            imageLights.setImageResource(R.drawable.yellow);
        }else{
            imageLights.setImageResource(R.drawable.green);
        }

        progressDialog.dismiss(); //LOL INTERNET ...
        progressBar.setVisibility(View.INVISIBLE);
        inputPurinValue.setText("");
        //textPurinCalc.setText("0 mg Purin");
        //buttonadd.setVisibility(View.GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("XEK", "Nothing selected");
    }

}