package com.justingruenberg.beatyourneat.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.justingruenberg.beatyourneat.R;

public class UpdatedWeightDialog extends DialogFragment {

    private NumberPicker np_updatedWeightDialogKilo, np_updatedWeightDialogGrams;
    private TextView tv_updatedWeightDialogCancel, tv_updatedWeightDialogApply;

    private onWeightSelected onWeightSelected;

    private String kilo, grams;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_updatedweightdialog, container, false);

        np_updatedWeightDialogKilo = view.findViewById(R.id.np_updatedWeightDialogKilo);
        np_updatedWeightDialogGrams = view.findViewById(R.id.np_updatedWeightDialogGrams);
        tv_updatedWeightDialogApply = view.findViewById(R.id.tv_updatedWeightDialogApply);
        tv_updatedWeightDialogCancel = view.findViewById(R.id.tv_updatedWeightDialogCancel);


        np_updatedWeightDialogKilo.setMinValue(30);
        np_updatedWeightDialogKilo.setMaxValue(300);
        np_updatedWeightDialogKilo.setValue(80);
        kilo = String.valueOf(np_updatedWeightDialogKilo.getValue());
        np_updatedWeightDialogKilo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                kilo = String.valueOf(newValue);
            }
        });

        np_updatedWeightDialogGrams.setMinValue(0);
        np_updatedWeightDialogGrams.setMaxValue(9);
        np_updatedWeightDialogGrams.setValue(0);
        grams = String.valueOf(np_updatedWeightDialogGrams.getValue());
        np_updatedWeightDialogGrams.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                grams = String.valueOf(newValue);
            }
        });

        tv_updatedWeightDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        tv_updatedWeightDialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!kilo.equals("") || !grams.equals("")){
                    onWeightSelected.onInputSelected(kilo, grams);
                }
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onWeightSelected = (UpdatedWeightDialog.onWeightSelected) getTargetFragment();
        }catch (ClassCastException e){
            e.getMessage();
        }

    }

    public interface onWeightSelected{
        void onInputSelected(String kilos, String grams);
    }
}
