package com.justingruenberg.beatyourneat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.justingruenberg.beatyourneat.Dialogs.HeightDialog;
import com.justingruenberg.beatyourneat.Model.DAO.ProfileDAO;
import com.justingruenberg.beatyourneat.Model.ProfileModel;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.UserModel;
import com.justingruenberg.beatyourneat.R;
import com.justingruenberg.beatyourneat.Dialogs.WeightDialog;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements HeightDialog.HeightDialogInterface, WeightDialog.WeightDialogInterface, View.OnClickListener {

    private ToggleButton tb_profileFemale, tb_profileMale;
    private Button bt_profileHeight, bt_profileAge, bt_profileWeight, bt_profileNext, bt_profileCancel;
    private UserManager instance;
    private DatePickerDialog datePickerDialog;
    private WeightDialog weightDialog;
    private HeightDialog heightDialog;
    private Calendar calendar;
    private String chosenGender, chosenBirthdate;
    private int chosenHeight;
    private double chosenWeight;

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
        bt_profileCancel = findViewById(R.id.bt_profileCancel);

        instance = UserManager.getInstance();
        tb_profileMale.setChecked(true);
        chosenGender = "male";


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

        bt_profileHeight.setOnClickListener(this);
        bt_profileAge.setOnClickListener(this);
        bt_profileWeight.setOnClickListener(this);
        bt_profileNext.setOnClickListener(this);
        bt_profileCancel.setOnClickListener(this);


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

    public void openHeightDialog() {
        heightDialog = new HeightDialog();
        heightDialog.show(getSupportFragmentManager(), "HeightDialog");
    }

    @Override
    public void onApplyHeight(int height) {
        chosenHeight = height;
        bt_profileHeight.setText(chosenHeight + " cm");
    }

    public void openWeightDialog() {
        weightDialog = new WeightDialog();
        weightDialog.show(getSupportFragmentManager(), "WeightDialog");
    }

    @Override
    public void onApplyWeight(int kilos, int grams) {
        chosenWeight = Double.parseDouble(kilos + "." + grams);
        bt_profileWeight.setText(chosenWeight + " kg");
    }

    public void openDatePickerDialog() {

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                chosenBirthdate = dateToString(year, month, day);
                bt_profileAge.setText(chosenBirthdate);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Apply", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", datePickerDialog);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {

        if (view.equals(bt_profileHeight)) {
            openHeightDialog();
        } else if (view.equals(bt_profileAge)) {
            openDatePickerDialog();
        } else if (view.equals(bt_profileWeight)) {
            openWeightDialog();
        } else if (view.equals(bt_profileNext)) {
            if (bt_profileAge.getText().toString().equals("Select age") || bt_profileWeight.getText().toString().equals("Select weight") || bt_profileHeight.getText().toString().equals("Select height")) {
                Toast.makeText(this, "Please fill out every section", Toast.LENGTH_SHORT).show();
            } else {
                ProfileDAO profileDAO = new ProfileDAO(this);
                ProfileModel userProfile = new ProfileModel(chosenGender, chosenHeight, chosenBirthdate, chosenWeight, instance.getCurrentUser());

                Toast.makeText(this, "Initialisation complete for user: ", Toast.LENGTH_SHORT).show();
                // ProfileDAO persistance + open Mainpage

            }
        } else if (view.equals(bt_profileCancel)) {
            finish();
        }
    }
}