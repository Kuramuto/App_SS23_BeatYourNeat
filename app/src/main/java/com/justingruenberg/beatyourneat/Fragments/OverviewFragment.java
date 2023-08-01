package com.justingruenberg.beatyourneat.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.justingruenberg.beatyourneat.Model.DAO.WeightDAO;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.UserModel;
import com.justingruenberg.beatyourneat.Model.WeightModel;
import com.justingruenberg.beatyourneat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LineChart lv_overviewFragment;
    private List<WeightModel> weightList;
    UserManager instance;


    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        lv_overviewFragment = view.findViewById(R.id.lc_overviewFragment);
        instance = UserManager.getInstance();
        WeightDAO weightDAO = new WeightDAO(getActivity());
        weightList = weightDAO.getAllWeights(instance.getCurrentUser());





        ArrayList<Entry> averageWeights = new ArrayList<>();
        averageWeights.add(new Entry(0, 20));
        averageWeights.add(new Entry(1, 24));
        averageWeights.add(new Entry(2, 2));

        LineDataSet lineDataSet = new LineDataSet(averageWeights, "Average Weights");
        LineData lineData = new LineData(lineDataSet);
        lv_overviewFragment.setData(lineData);
        lv_overviewFragment.invalidate();

        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleRadius(10);
        lineDataSet.setCircleHoleRadius(10);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setLineWidth(5);

        return view;
    }
}