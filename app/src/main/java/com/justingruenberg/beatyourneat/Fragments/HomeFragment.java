package com.justingruenberg.beatyourneat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.justingruenberg.beatyourneat.Dialogs.UpdatedHeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedWeightDialog;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, UpdatedWeightDialog.onWeightSelected {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "HomeFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button bt_addingWeightWeight, bt_addingWeightDate, bt_addingWeightApply;
    UserManager instance;
    private double weight;
    private String date;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        bt_addingWeightWeight = view.findViewById(R.id.bt_addingWeightWeight);
        bt_addingWeightDate = view.findViewById(R.id.bt_addingWeightDate);
        bt_addingWeightApply = view.findViewById(R.id.bt_addingWeightApply);
        date = "";
        instance = UserManager.getInstance();

        bt_addingWeightWeight.setOnClickListener(this);
        bt_addingWeightDate.setOnClickListener(this);
        bt_addingWeightApply.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {
        if(view.equals(bt_addingWeightWeight)){
            UpdatedWeightDialog updatedWeightDialog = new UpdatedWeightDialog();
            updatedWeightDialog.setTargetFragment(HomeFragment.this, 1);
            updatedWeightDialog.show(getFragmentManager(), "UpdatedWeightDialog");
        }/*else if(view.equals(bt_addingWeightDate)){
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

        }*/
    }

    /*public void openWeightDialog(){
        WeightDialog weightDialog = new WeightDialog();
        weightDialog.show(getSupportFragmentManager(), "WeightDialog");
    }

    @Override
    public void onApplyWeight(int kilos, int grams) {
        weight = Double.parseDouble(kilos + "." + grams);
        bt_addingWeightWeight.setText(weight + " kg");
    }*/
    @Override
    public void onInputSelected(String kilos, String grams) {
        bt_addingWeightWeight.setText(kilos + "." + grams);
    }
}