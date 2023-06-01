package com.justingruenberg.beatyourneat.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.justingruenberg.beatyourneat.Dialogs.HeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.WeightDialog;
import com.justingruenberg.beatyourneat.R;

import java.util.Calendar;

public class AddingWeightActivity extends AppCompatActivity implements View.OnClickListener, WeightDialog.WeightDialogInterface {

    private Button bt_addingWeightWeight, bt_addingWeightDate;
    private double weight;
    private String date;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_weight);

        bt_addingWeightWeight = findViewById(R.id.bt_addingWeightWeight);
        bt_addingWeightDate = findViewById(R.id.bt_addingWeightDate);

        bt_addingWeightWeight.setOnClickListener(this);
        bt_addingWeightDate.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if(view.equals(bt_addingWeightWeight)){
            openWeightDialog();
        }else if(view.equals(bt_addingWeightDate)){
            openDatePickerDialog();
        }
    }

    public void openWeightDialog(){
        WeightDialog weightDialog = new WeightDialog();
        weightDialog.show(getSupportFragmentManager(), "WeightDialog");
    }

    @Override
    public void onApplyWeight(int kilos, int grams) {
        weight = Double.parseDouble(kilos + "." + grams);
        bt_addingWeightWeight.setText(weight + " kg");
    }

    public void openDatePickerDialog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = dateToString(year, month, day);
                bt_addingWeightDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Apply", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", datePickerDialog);
        datePickerDialog.show();
    }


    private String dateToString(int year, int month, int day) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {

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
}