package com.justingruenberg.beatyourneat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.justingruenberg.beatyourneat.Dialogs.DateDialog;
import com.justingruenberg.beatyourneat.Dialogs.HeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.WeightDialog;
import com.justingruenberg.beatyourneat.Fragments.CalculatorFragment;
import com.justingruenberg.beatyourneat.Fragments.HomeFragment;
import com.justingruenberg.beatyourneat.Fragments.OverviewFragment;
import com.justingruenberg.beatyourneat.Fragments.SettingsFragment;
import com.justingruenberg.beatyourneat.Model.DAO.WeightDAO;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.WeightModel;
import com.justingruenberg.beatyourneat.R;

import java.util.Calendar;

public class AddingWeightActivity extends AppCompatActivity {


    private BottomNavigationView bnb_addingWeightNavbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_weight);
        bnb_addingWeightNavbar = findViewById(R.id.bnb_addingWeightNavbar);
        changeFragment(new HomeFragment());
        bnb_addingWeightNavbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.navigation_home:
                        changeFragment(new HomeFragment());
                        break;
                    case R.id.navigation_settings:
                        changeFragment(new SettingsFragment());
                        break;
                    case R.id.navigation_overview:
                        changeFragment(new OverviewFragment());
                        break;
                    case R.id.navigation_calculator:
                        changeFragment(new CalculatorFragment());
                        break;
                }
                return false;
            }
        });
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_addingWeightFramelayout, fragment);
        fragmentTransaction.commit();
    }
}