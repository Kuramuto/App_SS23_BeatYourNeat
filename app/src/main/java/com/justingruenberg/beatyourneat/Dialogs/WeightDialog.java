package com.justingruenberg.beatyourneat.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.justingruenberg.beatyourneat.R;

public class WeightDialog extends AppCompatDialogFragment {

    private NumberPicker kiloPicker, gramPicker;
    private WeightDialogInterface weightDialogInterface;
    private int kilos, grams;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_weightdialog, null);

        builder.setView(view);
        builder.setTitle("in kg");

        kiloPicker = view.findViewById(R.id.np_weightDialogKilo);
        gramPicker = view.findViewById(R.id.np_weightDialogGram);

        kiloPicker.setMinValue(30);
        kiloPicker.setMaxValue(300);
        kiloPicker.setValue(80);
        kilos = kiloPicker.getValue();
        kiloPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                kilos = newValue;
            }
        });

        gramPicker.setMinValue(0);
        gramPicker.setMaxValue(9);
        gramPicker.setValue(0);
        grams = gramPicker.getValue();
        gramPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                grams = newValue;
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                weightDialogInterface.onApplyWeight(kilos, grams);
            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        weightDialogInterface = (WeightDialogInterface) context;
    }

    public interface WeightDialogInterface{
        public void onApplyWeight(int kilos, int grams);
    }
}
