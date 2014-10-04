package com.xek6ae.PurinApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.List;

public class SelectActivity extends Activity implements AdapterView.OnItemSelectedListener{
    /**
     * Diese Aktivität zeigt Purinwerte aus der Datenbank an.
     * + Berechnet aktuellen Purinwert
     * + Fügt aktuellen Purinwert dem Tag hinzu <--- noch zu implemntieren
     *
     * Create by xek6ae
     * Version 1.2
     */
    private int purinValue;
    private int purinInput;

    private Spinner s1;
    private TextView textName = null;
    private TextView textNameKlein = null;
    private TextView textKategorie = null;
    private TextView textPurin = null;
    private TextView textHarnsaeure = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);

        s1 = (Spinner)findViewById(R.id.spinner);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.auswahlListe, android.R.layout.simple_spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(this);

        loadSpinnerData();

        final EditText inputValue = (EditText)findViewById(R.id.editText);
        //inputValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final TextView textAktuellPurin = (TextView)findViewById(R.id.textView11);
        //textAktuellPurin.setText(inputValue.getText().toString()); //siehe 92
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp = inputValue.getText().toString();
                /**
                 * Code if it possible to insert Chars
                if(!(temp.isEmpty())){
                    try{
                        purinInput = Integer.parseInt(inputValue.getText().toString());
                    }catch (NumberFormatException e){
                        Log.d("parse Integer", e.getMessage());
                        Toast.makeText(v.getContext(), "Please Use only Digits", Toast.LENGTH_LONG).show();
                    }
                } **/
                if(!(temp.isEmpty())){
                    purinInput = Integer.parseInt(inputValue.getText().toString());
                }else{
                    purinInput = 0;
                    Toast.makeText(v.getContext(), "Please insert a value.", Toast.LENGTH_SHORT).show();
                }

                int aktuellerWert = (purinInput*purinValue)/100;
                if(aktuellerWert<=99999){
                    textAktuellPurin.setText(String.valueOf(aktuellerWert));
                }else{
                    Toast.makeText(v.getContext(), "Value too hight, control input.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loadSpinnerData(){
        SQLiteHandler db = new SQLiteHandler(getBaseContext());

        try{
            db.importIfNotExist();
        }catch (IOException e){
            e.printStackTrace();
        }

        List<String> lables = db.getAllLabels();

        // ##heavy thing: MyActivity.this, android.R.layout.simple_list_item_1
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectActivity.this, android.R.layout.simple_list_item_1, lables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String label = parent.getItemAtPosition(position).toString();
        SQLiteHandler db = new SQLiteHandler(getBaseContext());

        //## Info Ausgabe
        //Toast.makeText(parent.getContext(), "You selected: "+label, Toast.LENGTH_SHORT).show();

        textName = (TextView)findViewById(R.id.textView);
        textName.setText(s1.getSelectedItem().toString());

        textKategorie = (TextView)findViewById(R.id.textView2);
        //Log.d("MyActivity", tmp);
        textKategorie.setText(db.getDataString("SELECT kategorie FROM gericht WHERE name='" + s1.getSelectedItem().toString() + "'"));

        textPurin = (TextView)findViewById(R.id.textView3);
        textPurin.setText(db.getDataString("SELECT purinwert FROM gericht WHERE name='" + s1.getSelectedItem().toString() + "'"));

        ImageView icon = (ImageView)findViewById(R.id.imageView);
        purinValue = db.getDataInt("SELECT purinwert FROM gericht WHERE name='"+s1.getSelectedItem().toString()+"'");
        if(purinValue>50){
            icon.setImageResource(R.drawable.red);
        }else if(purinValue>20){
            icon.setImageResource(R.drawable.yellow);
        }else{
            icon.setImageResource(R.drawable.green);
        }

        textHarnsaeure = (TextView)findViewById(R.id.textView6);
        textHarnsaeure.setText(db.getDataString("SELECT harnsaeurewert FROM gericht WHERE name='" + s1.getSelectedItem().toString() + "'"));

        textNameKlein = (TextView)findViewById(R.id.textView13);
        textNameKlein.setText(s1.getSelectedItem().toString());
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }
}
