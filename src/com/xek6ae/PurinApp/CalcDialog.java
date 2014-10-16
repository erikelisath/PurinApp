package com.xek6ae.PurinApp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xek6ae on 08.09.14.
 */
public class CalcDialog extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calc, container, false);
        String calcValue = getArguments().getString("CALC");
        String inputValue = getArguments().getString("INPUT");
        String calcName = getArguments().getString("NAME");
        TextView textCalc = (TextView)view.findViewById(R.id.dialog_textCalc);
        final int temp = Integer.parseInt(calcValue);

        setCancelable(false);

        Button buttonAdd = (Button)view.findViewById(R.id.dialog_buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: hinzufügen des Wertes in Datenbank
                Toast.makeText(v.getContext(),"Wert wurde Hinzugefügt.", Toast.LENGTH_SHORT).show();
                SQLiteHandler db = new SQLiteHandler(getActivity().getBaseContext());
                db.insertValue(temp);
                dismiss();
            }
        });

        Button buttonCancel = (Button)view.findViewById(R.id.dialog_buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //TODO: calcValue Farbig?
        textCalc.setText(inputValue+"g "+calcName+" hat "+calcValue+" mg Purin.");
        return view;
    }
    /**
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        String calcValue = getArguments().getString("CALC");
        String calcName = getArguments().getString("NAME");
        TextView textCalc = (TextView)getActivity().findViewById(R.id.dialog_textCalc);
        textCalc.setText(calcName+" hat "+calcValue+" mg Purin.");

        Button buttonAdd = (Button)getActivity().findViewById(R.id.dialog_buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Wert wurde Hinzugefügt.", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonCancel = (Button)getActivity().findViewById(R.id.dialog_buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    **/
    public Dialog onCreateDialog(Bundle onSavedState){
        Dialog dialog = super.onCreateDialog(onSavedState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
        /**
        String calcValue = getArguments().getString("CALC");
        String calcName = getArguments().getString("NAME");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(calcName);
        builder.setMessage(calcValue+" mg Purin");
        builder.setView(inflater.inflate(R.layout.dialog_calc, null));
        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();**/
    }
}