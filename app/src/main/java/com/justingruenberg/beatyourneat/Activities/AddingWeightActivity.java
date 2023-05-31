package com.justingruenberg.beatyourneat.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.justingruenberg.beatyourneat.Dialogs.HeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.WeightDialog;
import com.justingruenberg.beatyourneat.R;

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


    }


    @Override
    public void onClick(View view) {
        if(view.equals(bt_addingWeightWeight)){
            openWeightDialog();
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
}