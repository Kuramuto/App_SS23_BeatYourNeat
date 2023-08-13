package com.justingruenberg.beatyourneat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.justingruenberg.beatyourneat.Dialogs.BodyFatDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedDateDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedHeightDialog;
import com.justingruenberg.beatyourneat.Dialogs.UpdatedWeightDialog;
import com.justingruenberg.beatyourneat.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment implements BodyFatDialog.onBodyFatSelected, UpdatedWeightDialog.onWeightSelected, UpdatedHeightDialog.OnHeightSelected, UpdatedDateDialog.DateDialogInterface {

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
    private ToggleButton tb_fragmentCalculatorMale, tb_fragmentCalculatorFemale;
    private Spinner s_fragmentCalculatorActivityLevel;

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
    public void onBodyFatSelected(String percent, String comma) {

    }

    @Override
    public void onDateSelected(String date) {

    }

    @Override
    public void onHeightSelected(String input) {

    }

    @Override
    public void onWeightSelected(String kilos, String grams) {

    }
}