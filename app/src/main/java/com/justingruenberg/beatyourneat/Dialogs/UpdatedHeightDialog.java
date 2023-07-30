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

public class UpdatedHeightDialog extends DialogFragment {

    private NumberPicker np_updatedHeightDialogNumberPicker;
    private TextView tv_updatedHeightDialogSelectHeight, tv_updatedHeightDialogCancel, tv_updatedHeightDialogApply;
    private String height;

    private OnInputSelected onInputSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_updatedheightdialog, container, false);

        np_updatedHeightDialogNumberPicker = view.findViewById(R.id.np_updatedHeightDialogNumberPicker);
        tv_updatedHeightDialogSelectHeight = view.findViewById(R.id.tv_updatedHeightDialogSelectHeight);
        tv_updatedHeightDialogCancel = view.findViewById(R.id.tv_updatedHeightDialogCancel);
        tv_updatedHeightDialogApply = view.findViewById(R.id.tv_updatedHeightDialogApply);

        np_updatedHeightDialogNumberPicker.setMinValue(50);
        np_updatedHeightDialogNumberPicker.setMaxValue(250);
        np_updatedHeightDialogNumberPicker.setValue(175);
        height = String.valueOf(np_updatedHeightDialogNumberPicker.getValue());

        np_updatedHeightDialogNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                height = String.valueOf(newValue);
            }
        });

        tv_updatedHeightDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        tv_updatedHeightDialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!height.equals("")){
                    onInputSelected.sendInput(height);
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
            onInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){

        }
    }

    public interface OnInputSelected{
        void sendInput(String input);
    }
}
