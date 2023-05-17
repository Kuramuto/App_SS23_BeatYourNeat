package com.justingruenberg.beatyourneat;

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

public class AgeDialog extends AppCompatDialogFragment {

    NumberPicker agePicker;
    AgeDialogInterface ageDialogInterface;
    int age;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_agedialog, null);

        builder.setView(view);
        builder.setTitle("Your age");

        agePicker = view.findViewById(R.id.np_ageDialog);
        agePicker.setMinValue(10);
        agePicker.setMaxValue(110);
        agePicker.setValue(18);
        age = agePicker.getValue();

        agePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                age = i1;
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
                ageDialogInterface.onApplyAge(age);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ageDialogInterface = (AgeDialogInterface) context;
    }

    public interface AgeDialogInterface{
        void onApplyAge(int age);
    }
}
