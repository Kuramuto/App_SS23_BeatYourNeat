package com.justingruenberg.beatyourneat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

public class ProfileActivity extends AppCompatActivity implements HeightDialog.HeightDialogInterface, AgeDialog.AgeDialogInterface {

    ToggleButton tb_profileFemale;
    ToggleButton tb_profileMale;
    EditText et_profileHeight;
    EditText et_profileAge;
    EditText et_profileWeight;
    Button bt_profileNext;
    String chosenGender;
    NumberPicker heightPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        tb_profileFemale = findViewById(R.id.tb_profileFemale);
        tb_profileMale = findViewById(R.id.tb_profileMale);
        et_profileHeight = findViewById(R.id.et_profileHeight);
        et_profileAge = findViewById(R.id.et_profileAge);
        et_profileWeight = findViewById(R.id.et_profileWeight);
        bt_profileNext = findViewById(R.id.bt_profileNext);

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

        et_profileHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHeightDialog();
            }
        });

        et_profileAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAgeDialog();
            }
        });


    }

    public void openHeightDialog(){
        HeightDialog heightDialog = new HeightDialog();
        heightDialog.show(getSupportFragmentManager(), "HeightDialog");
    }

    public void openAgeDialog(){
        AgeDialog ageDialog = new AgeDialog();
        ageDialog.show(getSupportFragmentManager(), "AgeDialog");
    }

    @Override
    public void onApplyHeight(int height) {
        et_profileHeight.setText(String.valueOf(height) + " cm");
    }

    @Override
    public void onApplyAge(int age) {
        et_profileAge.setText(String.valueOf(age));
    }
}