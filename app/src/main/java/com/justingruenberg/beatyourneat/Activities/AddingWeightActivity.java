package com.justingruenberg.beatyourneat.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.justingruenberg.beatyourneat.Dialogs.DateDialog;
import com.justingruenberg.beatyourneat.Dialogs.HeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.WeightDialog;
import com.justingruenberg.beatyourneat.Model.DAO.WeightDAO;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.WeightModel;
import com.justingruenberg.beatyourneat.R;

import java.util.Calendar;

public class AddingWeightActivity extends AppCompatActivity implements View.OnClickListener, WeightDialog.WeightDialogInterface {

    private Button bt_addingWeightWeight, bt_addingWeightDate, bt_addingWeightApply;
    UserManager instance;
    private double weight;
    private String date;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_weight);

        bt_addingWeightWeight = findViewById(R.id.bt_addingWeightWeight);
        bt_addingWeightDate = findViewById(R.id.bt_addingWeightDate);
        bt_addingWeightApply = findViewById(R.id.bt_addingWeightApply);
        date = "";
        instance = UserManager.getInstance();

        bt_addingWeightWeight.setOnClickListener(this);
        bt_addingWeightDate.setOnClickListener(this);
        bt_addingWeightApply.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if(view.equals(bt_addingWeightWeight)){
            openWeightDialog();
        }else if(view.equals(bt_addingWeightDate)){
            DateDialog.openDatePickerDialog(this, bt_addingWeightDate);
        }else if(view.equals(bt_addingWeightApply)){
            if(bt_addingWeightWeight.getText().toString().equals("Weight") || bt_addingWeightDate.getText().toString().equals("Date")){
                Toast.makeText(this, "Choose a tracked weight for a day!", Toast.LENGTH_SHORT).show();
            }else{
                date = bt_addingWeightDate.getText().toString();
                WeightModel userWeight = new WeightModel(date, weight, instance.getCurrentUser());
                WeightDAO weightDAO = new WeightDAO(this);
                if(weightDAO.add(userWeight)){
                    Toast.makeText(this, "Added weight successfully", Toast.LENGTH_SHORT).show();
                }
            }

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