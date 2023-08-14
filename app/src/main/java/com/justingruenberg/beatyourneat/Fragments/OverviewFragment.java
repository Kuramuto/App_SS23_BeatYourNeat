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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    private WeightDAO weightDAO;
    UserManager instance;
    Map<String, Double> AverageWeights;


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
        weightDAO = new WeightDAO(getActivity());

        weightList = weightDAO.getAllWeights(instance.getCurrentUser());
        if(weightList.isEmpty()){
            return view;
        }else{
            sortListByDate((ArrayList<WeightModel>) weightList);
            AverageWeights = calculateWeeklyAverages(weightList);
            ArrayList<Entry> averageWeights = (ArrayList<Entry>) mapToEntries(AverageWeights);

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

    public static void sortListByDate(ArrayList<WeightModel> userWeights){
        final SimpleDateFormat format = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);

        Collections.sort(userWeights, new Comparator<WeightModel>() {
            @Override
            public int compare(WeightModel w1, WeightModel w2) {
                try {
                    Date date1 = format.parse(w1.getDate());
                    Date date2 = format.parse(w2.getDate());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

    }

    public static Map<String, Double> calculateWeeklyAverages(List<WeightModel> userWeights) {
        Map<String, Double> weeklyAverages = new LinkedHashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < userWeights.size(); ) {
            try {
                Date startDate = format.parse(userWeights.get(i).getDate());
                cal.setTime(startDate);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Date endDate = cal.getTime();

                double sum = 0.0;
                int count = 0;

                // Loop to aggregate weights within the week
                while (i < userWeights.size() && (format.parse(userWeights.get(i).getDate()).before(endDate))) {
                    sum += userWeights.get(i).getWeight();
                    count++;
                    i++;
                }

                if (count > 0) {
                    double average = sum / count;
                    weeklyAverages.put(format.format(startDate), average);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                i++; // In case of an error, we move to the next item to avoid infinite loops
            }
        }

        return weeklyAverages;
    }
    public List<Entry> mapToEntries(Map<String, Double> weeklyAverages) {
        List<Entry> entries = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);

        // Hole das Startdatum, um die x-Werte zu berechnen
        Date startDate;
        try {
            startDate = format.parse(weeklyAverages.keySet().iterator().next());
        } catch (ParseException e) {
            e.printStackTrace();
            return entries; // Wenn das Startdatum nicht geparst werden kann, geben eine leere Liste zurück
        }

        for (String date : weeklyAverages.keySet()) {
            try {
                Date currentDate = format.parse(date);
                float xValue = (currentDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24); // Anzahl der Tage seit dem Startdatum
                float yValue = weeklyAverages.get(date).floatValue();

                entries.add(new Entry(xValue, yValue));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return entries;
    }


}