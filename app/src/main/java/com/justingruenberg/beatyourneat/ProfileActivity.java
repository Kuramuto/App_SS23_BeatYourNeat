package com.justingruenberg.beatyourneat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements HeightDialog.HeightDialogInterface, WeightDialog.WeightDialogInterface {

    private ToggleButton tb_profileFemale;
    private ToggleButton tb_profileMale;
    private Button bt_profileHeight;
    private Button bt_profileAge;
    private Button bt_profileWeight;
    private DatePickerDialog datePickerDialog;
    private Button bt_profileNext;
    private String chosenGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        tb_profileFemale = findViewById(R.id.tb_profileFemale);
        tb_profileMale = findViewById(R.id.tb_profileMale);
        bt_profileHeight = findViewById(R.id.bt_profileHeight);
        bt_profileAge = findViewById(R.id.bt_profileAge);
        bt_profileWeight = findViewById(R.id.bt_profileWeight);
        bt_profileNext = findViewById(R.id.bt_profileNext);

        initDatePicker();
        tb_profileFemale.setChecked(true);
        chosenGender = "female";

        tb_profileFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tb_profileMale.setChecked(!isChecked);
                chosenGender = tb_profileFemale.getText().toString().toLowerCase();
            }
        });

        tb_profileMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tb_profileFemale.setChecked(!isChecked);
                chosenGender = tb_profileMale.getText().toString().toLowerCase();
            }
        });

        bt_profileHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHeightDialog2();
            }
        });

        bt_profileAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog();
            }
        });

        bt_profileWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeightDialog2();
            }
        });


    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = dateToString(year, month, day);
                bt_profileAge.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT , dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String dateToString(int year, int month, int day) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {

        switch(month){
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

    public void openHeightDialog(){
        HeightDialog heightDialog = new HeightDialog();
        heightDialog.show(getSupportFragmentManager(), "HeightDialog");
    }

    @Override
    public void onApplyHeight(int height) {
        bt_profileHeight.setText(height + " cm");
    }

    public void openDatePickerDialog(){
        datePickerDialog.show();
    }

    public void openWeightDialog(){
        WeightDialog weightDialog = new WeightDialog();
        weightDialog.show(getSupportFragmentManager(), "WeightDialog");
    }


    @Override
    public void onApplyWeight(int kilos, int grams) {
        bt_profileWeight.setText(kilos + "." + grams + " kg");
    }

    public void openHeightDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        final NumberPicker np = new NumberPicker(this);
        final int[] height = new int[1];
        np.setMinValue(50);
        np.setMaxValue(250);
        np.setValue(175);
        height[0] = np.getValue();
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                height[0] = newValue;
            }
        });
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(np);
        builder.setView(linearLayout);
        builder.setTitle("in cm");

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // logic for continue button
                bt_profileHeight.setText(height[0] + " cm");
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel(); // logic for cancel button
            }
        });
        builder.show();

    }
    public void openWeightDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        builder.setTitle("in kg");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        final NumberPicker kilos = new NumberPicker(this);
        final TextView dot = new TextView(this);
        final NumberPicker grams = new NumberPicker(this);
        final int[] kilo = new int[1];
        final int[] gram = new int[1];

        kilos.setMinValue(30);
        kilos.setMaxValue(300);
        kilos.setValue(80);
        kilo[0] = kilos.getValue();
        kilos.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                kilo[0] = newValue;
            }
        });

        dot.setText(".");
        dot.setTextSize(30);

        grams.setMinValue(0);
        grams.setMaxValue(9);
        grams.setValue(0);
        gram[0] = grams.getValue();
        grams.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                gram[0] = newValue;
            }
        });

        linearLayout.addView(kilos);
        linearLayout.addView(dot);
        linearLayout.addView(grams);
        builder.setView(linearLayout);

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bt_profileWeight.setText(kilo[0] + "." + gram[0] + " kg");
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void openBirthdateDialog(){

    }
}