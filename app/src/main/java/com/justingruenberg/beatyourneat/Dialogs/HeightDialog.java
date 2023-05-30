package com.justingruenberg.beatyourneat.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.justingruenberg.beatyourneat.R;

public class HeightDialog extends AppCompatDialogFragment {

    private NumberPicker heightPicker;
    private HeightDialogInterface heightDialogInterface;
    private int height;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_heightdialog, null);

        builder.setView(view);
        builder.setTitle("In cm");

        heightPicker = view.findViewById(R.id.np_heightDialog);
        heightPicker.setMinValue(50);
        heightPicker.setMaxValue(250);
        heightPicker.setValue(175);
        height = heightPicker.getValue();

        heightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                height = newValue;
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

                heightDialogInterface.onApplyHeight(height);

            }
        });

        return  builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        heightDialogInterface = (HeightDialogInterface) context;
    }

    public interface HeightDialogInterface{
        void onApplyHeight(int height);
    }
}
