package com.justingruenberg.beatyourneat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.justingruenberg.beatyourneat.Dialogs.DateDialog;
import com.justingruenberg.beatyourneat.Dialogs.HeightDialog;
import com.justingruenberg.beatyourneat.Model.DAO.ProfileDAO;
import com.justingruenberg.beatyourneat.Model.ProfileModel;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.R;
import com.justingruenberg.beatyourneat.Dialogs.WeightDialog;

public class ProfileActivity extends AppCompatActivity implements HeightDialog.HeightDialogInterface, WeightDialog.WeightDialogInterface, View.OnClickListener {

    private ToggleButton tb_profileFemale, tb_profileMale;
    private Button bt_profileHeight, bt_profileBirthdate, bt_profileWeight, bt_profileNext, bt_profileCancel;
    private UserManager instance;
    private String chosenGender, chosenBirthdate;
    private int chosenHeight;
    private double chosenWeight;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        tb_profileFemale = findViewById(R.id.tb_profileFemale);
        tb_profileMale = findViewById(R.id.tb_profileMale);
        bt_profileHeight = findViewById(R.id.bt_profileHeight);
        bt_profileBirthdate = findViewById(R.id.bt_profileBirthdate);
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
        bt_profileBirthdate.setOnClickListener(this);
        bt_profileWeight.setOnClickListener(this);
        bt_profileNext.setOnClickListener(this);
        bt_profileCancel.setOnClickListener(this);


    }

    public void openHeightDialog() {
        HeightDialog heightDialog = new HeightDialog();
        heightDialog.show(getSupportFragmentManager(), "HeightDialog");
    }

    @Override
    public void onApplyHeight(int height) {
        chosenHeight = height;
        bt_profileHeight.setText(chosenHeight + " cm");
    }

    public void openWeightDialog() {
        WeightDialog weightDialog = new WeightDialog();
        weightDialog.show(getSupportFragmentManager(), "WeightDialog");
    }

    @Override
    public void onApplyWeight(int kilos, int grams) {
        chosenWeight = Double.parseDouble(kilos + "." + grams);
        bt_profileWeight.setText(chosenWeight + " kg");
    }

    @Override
    public void onClick(View view) {

        if (view.equals(bt_profileHeight)) {
            openHeightDialog();
        } else if (view.equals(bt_profileBirthdate)) {
            DateDialog.openDatePickerDialog(this, bt_profileBirthdate, chosenBirthdate);
        } else if (view.equals(bt_profileWeight)) {
            openWeightDialog();
        } else if (view.equals(bt_profileNext)) {
            if (bt_profileBirthdate.getText().toString().equals("Select age") || bt_profileWeight.getText().toString().equals("Select weight") || bt_profileHeight.getText().toString().equals("Select height")) {
                Toast.makeText(this, "Please fill out every section", Toast.LENGTH_SHORT).show();
            } else {
                ProfileDAO profileDAO = new ProfileDAO(this);
                ProfileModel userProfile = new ProfileModel(chosenGender, chosenHeight, chosenBirthdate, chosenWeight, instance.getCurrentUser().getUserName());
                instance.getCurrentUser().setUserProfile(userProfile);
                profileDAO.add(userProfile);
                Toast.makeText(this, "Initialisation complete", Toast.LENGTH_SHORT).show();
                // ProfileDAO persistance + open Mainpage

            }
        } else if (view.equals(bt_profileCancel)) {
            finish();
        }
    }
}