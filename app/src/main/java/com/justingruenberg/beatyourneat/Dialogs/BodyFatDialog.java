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

public class BodyFatDialog extends DialogFragment {

    private NumberPicker np_bodyFatDialogPercent, np_bodyFatDialogComma;
    private TextView tv_bodyFatDialogCancel, tv_bodyFatDialogApply;
    private String percent, comma;
    private onBodyFatSelected onBodyFatSelected;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bodyfatdialog, container, false);

        np_bodyFatDialogPercent = view.findViewById(R.id.np_bodyFatDialogPercent);
        np_bodyFatDialogComma = view.findViewById(R.id.np_bodyFatDialogComma);
        tv_bodyFatDialogCancel = view.findViewById(R.id.tv_bodyFatDialogCancel);
        tv_bodyFatDialogApply = view.findViewById(R.id.tv_bodyFatDialogApply);

        np_bodyFatDialogPercent.setMinValue(3);
        np_bodyFatDialogPercent.setMaxValue(55);
        np_bodyFatDialogPercent.setValue(12);
        percent = String.valueOf(np_bodyFatDialogPercent.getValue());
        np_bodyFatDialogPercent.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                percent = String.valueOf(newValue);
            }
        });

        np_bodyFatDialogComma.setMinValue(0);
        np_bodyFatDialogComma.setMaxValue(9);
        np_bodyFatDialogComma.setValue(0);
        comma = String.valueOf(np_bodyFatDialogComma.getValue());
        np_bodyFatDialogComma.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                comma = String.valueOf(newValue);
            }
        });

        tv_bodyFatDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        tv_bodyFatDialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!percent.equals("") || !comma.equals("")){
                    onBodyFatSelected.onBodyFatSelected(percent, comma);
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
            onBodyFatSelected = (BodyFatDialog.onBodyFatSelected) getTargetFragment();
        }catch (ClassCastException e){
            e.getMessage();
        }

    }

    public interface onBodyFatSelected{
        void onBodyFatSelected(String percent, String comma);
    }
}
