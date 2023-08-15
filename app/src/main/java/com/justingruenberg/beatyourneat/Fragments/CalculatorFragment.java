package com.justingruenberg.beatyourneat.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.justingruenberg.beatyourneat.Dialogs.BodyFatDialog;
import com.justingruenberg.beatyourneat.Dialogs.ImageDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedDateDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedHeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedWeightDialog;
import com.justingruenberg.beatyourneat.Model.DAO.ProfileDAO;
import com.justingruenberg.beatyourneat.Model.DAO.WeightDAO;
import com.justingruenberg.beatyourneat.Model.ProfileModel;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.WeightModel;
import com.justingruenberg.beatyourneat.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment implements BodyFatDialog.onBodyFatSelected, UpdatedWeightDialog.onWeightSelected, UpdatedHeightDialog.OnHeightSelected, UpdatedDateDialog.DateDialogInterface, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button bt_fragmentCalculatorHeight, bt_fragmentCalculatorBirthdate, bt_fragmentCalculatorWeight,
            bt_fragmentCalculatorBodyFat, bt_fragmentCalculatorExamples, bt_fragmentCalculatorCalculate;
    private TextView tv_fragmentCalculatorKcal, tv_fragmentCalculatorProtein, tv_fragmentCalculatorCarbs, tv_fragmentCalculatorFats;
    private Spinner s_fragmentCalculatorActivityLevel;
    private UserManager instance;
    private ProfileDAO profileDAO;
    private WeightDAO weightDAO;
    private double weight, bodyFat, bmr;
    private int age, height;
    private List<WeightModel> weightList;






    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        bt_fragmentCalculatorWeight = view.findViewById(R.id.bt_fragmentCalculatorWeight);
        bt_fragmentCalculatorHeight = view.findViewById(R.id.bt_fragmentCalculatorHeight);
        bt_fragmentCalculatorBirthdate = view.findViewById(R.id.bt_fragmentCalculatorBirthdate);
        bt_fragmentCalculatorBodyFat = view.findViewById(R.id.bt_fragmentCalculatorBodyFat);
        bt_fragmentCalculatorExamples = view.findViewById(R.id.bt_fragmentCalculatorExamples);
        bt_fragmentCalculatorCalculate = view.findViewById(R.id.bt_fragmentCalculatorCalculate);
        tv_fragmentCalculatorKcal = view.findViewById(R.id.tv_fragmentCalculatorKcal);
        tv_fragmentCalculatorProtein = view.findViewById(R.id.tv_fragmentCalculatorProtein);
        tv_fragmentCalculatorCarbs = view.findViewById(R.id.tv_fragmentCalculatorCarbs);
        tv_fragmentCalculatorFats = view.findViewById(R.id.tv_fragmentCalculatorFats);
        s_fragmentCalculatorActivityLevel = view.findViewById(R.id.s_fragmentCalculatorActivityLevel);


        instance = UserManager.getInstance();
        profileDAO = new ProfileDAO(getActivity());
        weightDAO = new WeightDAO(getActivity());

        weightList = (ArrayList<WeightModel>) weightDAO.getAllWeights(instance.getCurrentUser());
        height = profileDAO.get(instance.getCurrentUser().getUserName()).getHeight();
        age = calculateAge(profileDAO.get(instance.getCurrentUser().getUserName()).getBirthdate());

        bt_fragmentCalculatorBodyFat.setOnClickListener(this);
        bt_fragmentCalculatorWeight.setOnClickListener(this);
        bt_fragmentCalculatorHeight.setOnClickListener(this);
        bt_fragmentCalculatorBirthdate.setOnClickListener(this);
        bt_fragmentCalculatorExamples.setOnClickListener(this);
        bt_fragmentCalculatorCalculate.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ActivityLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_fragmentCalculatorActivityLevel.setAdapter(adapter);
        s_fragmentCalculatorActivityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedActivityLevel = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(weightList.isEmpty()){
            weight = profileDAO.get(instance.getCurrentUser().getUserName()).getWeight();
        }
        else{
            OverviewFragment.sortListByDate((ArrayList<WeightModel>) weightList);
            Map<String, Double> AverageWeights = OverviewFragment.calculateWeeklyAverages(weightList);
            weight = roundTo2DecimalPlaces((double) AverageWeights.values().toArray()[AverageWeights.size() - 1]);
        }

        bt_fragmentCalculatorHeight.setText(height + "cm");
        bt_fragmentCalculatorBirthdate.setText(profileDAO.get(instance.getCurrentUser().getUserName()).getBirthdate());
        bt_fragmentCalculatorWeight.setText(weight + "kg");


        return view;
    }

    @Override
    public void onBodyFatSelected(String percent, String comma) {
        bodyFat = Double.parseDouble(percent + "." + comma);
        bt_fragmentCalculatorBodyFat.setText(percent + "." + comma + "%");
    }

    @Override
    public void onDateSelected(String date) {
        age = calculateAge(date);
        bt_fragmentCalculatorBirthdate.setText(date);
    }

    @Override
    public void onHeightSelected(String input) {
        height = Integer.parseInt(input);
        bt_fragmentCalculatorHeight.setText(input + "cm");
    }

    @Override
    public void onWeightSelected(String kilos, String grams) {
        weight = Double.parseDouble(kilos + "." + grams);
        bt_fragmentCalculatorWeight.setText(kilos + "." + grams + "kg");
    }

    @Override
    public void onClick(View view) {
        if(view.equals(bt_fragmentCalculatorBodyFat)){
            BodyFatDialog bodyFatDialog = new BodyFatDialog();
            bodyFatDialog.setTargetFragment(CalculatorFragment.this, 1);
            bodyFatDialog.show(getFragmentManager(), "BodyFatDialog");
        }
        else if(view.equals(bt_fragmentCalculatorHeight)){
            UpdatedHeightDialog updatedHeightDialog = new UpdatedHeightDialog();
            updatedHeightDialog.setTargetFragment(CalculatorFragment.this, 2);
            updatedHeightDialog.show(getFragmentManager(), "UpdatedHeightDialog");
        }
        else if (view.equals(bt_fragmentCalculatorBirthdate)) {
            UpdatedDateDialog updatedDateDialog = new UpdatedDateDialog();
            updatedDateDialog.setTargetFragment(CalculatorFragment.this, 3);
            updatedDateDialog.show(getFragmentManager(), "UpdatedDateDialog");
        }
        else if (view.equals(bt_fragmentCalculatorWeight)) {
            UpdatedWeightDialog updatedWeightDialog = new UpdatedWeightDialog();
            updatedWeightDialog.setTargetFragment(CalculatorFragment.this, 4);
            updatedWeightDialog.show(getFragmentManager(), "UpdatedWeightDialog");
        }
        else if(view.equals(bt_fragmentCalculatorExamples)){
            ImageDialog imageDialog = new ImageDialog(getActivity());
            imageDialog.show(R.drawable.body_fat_examples);
        }
        else if(view.equals(bt_fragmentCalculatorCalculate)){
            if(s_fragmentCalculatorActivityLevel.getSelectedItemPosition() != AdapterView.INVALID_POSITION){
                String selectedItem = (String) s_fragmentCalculatorActivityLevel.getSelectedItem();
                if(!(bt_fragmentCalculatorBodyFat.getText().equals("select body fat"))){
                    bmr = 370 + (21.6 * (weight - (weight * (bodyFat/100))));
                    double cal = roundTo2DecimalPlaces(bmr * translateActivityFactor(selectedItem));
                    tv_fragmentCalculatorKcal.setText("Kcal " + cal);
                    tv_fragmentCalculatorProtein.setText("Proteins " + calculateProteins(weight));
                    tv_fragmentCalculatorCarbs.setText("Carbs " + calculateCarbs(cal, weight));
                    tv_fragmentCalculatorFats.setText("Fats " + calculateFats(weight));
                }
                else if(profileDAO.get(instance.getCurrentUser().getUserName()).equals("male")){
                    bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
                    double cal = roundTo2DecimalPlaces(bmr * translateActivityFactor(selectedItem));
                    tv_fragmentCalculatorKcal.setText("Kcal " + cal);
                    tv_fragmentCalculatorProtein.setText("Proteins " + calculateProteins(weight));
                    tv_fragmentCalculatorCarbs.setText("Carbs " + calculateCarbs(cal, weight));
                    tv_fragmentCalculatorFats.setText("Fats " + calculateFats(weight));
                }
                else{
                    bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
                    double cal = roundTo2DecimalPlaces(bmr * translateActivityFactor(selectedItem));
                    tv_fragmentCalculatorKcal.setText("Kcal " + cal);
                    tv_fragmentCalculatorProtein.setText("Proteins " + calculateProteins(weight));
                    tv_fragmentCalculatorCarbs.setText("Carbs " + calculateCarbs(cal, weight));
                    tv_fragmentCalculatorFats.setText("Fats " + calculateFats(weight));
                }
            }
            else{
                Toast.makeText(getActivity(), "Select your activity level", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int calculateAge(String birthdate) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        Date dateOfBirth = null;
        try {
            dateOfBirth = formatter.parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(dateOfBirth);

        Calendar todayCal = Calendar.getInstance();

        int age = todayCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        if (todayCal.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) ||
                (todayCal.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) &&
                        todayCal.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

    //Methode schreiben für den Spinner ausgewähltes aktivitätslevel
    public double translateActivityFactor(String activityLevel){
        switch (activityLevel) {
            case "not exhausting (Netflix)":
                return 1.375;
            case "little strenuous (Office job)":
                return 1.55;
            case "exhausting (Gastronomy)":
                return 1.725;
            case "very exhausting (Construction site)":
                return 1.9;
            default:
                throw new IllegalArgumentException("Unknown activity level: " + activityLevel);
        }
    }

    public double roundTo2DecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public double calculateProteins(double weight){
        return weight * 2;
    }
    public double calculateFats(double weight){
        return weight;
    }
    public double calculateCarbs(double calories, double weight){
        return roundTo2DecimalPlaces((calories - (calculateProteins(weight) * 4.1) - (calculateFats(weight) * 9.3)) / 4.1);
    }

}