package com.xek6ae.PurinApp;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;

/**
 * Created by xek6ae on 04.10.14.
 */
public class SpinnerDialog extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progressbar, container, false);

        return view;
    }

    public Dialog onCreateDialog(Bundle onSavedState) {
        Dialog dialog = super.onCreateDialog(onSavedState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog;
    }
}