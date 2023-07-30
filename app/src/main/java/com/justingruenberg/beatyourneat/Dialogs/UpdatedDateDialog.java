package com.justingruenberg.beatyourneat.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class UpdatedDateDialog extends DialogFragment {


    private DateDialogInterface dateDialogInterface;
    private String date;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                date = dateToString(year, month, day);
                dateDialogInterface.onDateSelected(date);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Apply", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", datePickerDialog);
        return datePickerDialog;
    }

    private static String dateToString(int year, int month, int day) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private static String getMonthFormat(int month) {

        switch (month) {
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dateDialogInterface = (DateDialogInterface) getTargetFragment();
    }

    public interface DateDialogInterface{
        void onDateSelected(String date);
    }
}
