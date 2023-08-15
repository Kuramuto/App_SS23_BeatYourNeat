package com.justingruenberg.beatyourneat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.justingruenberg.beatyourneat.Dialogs.ChangeUserDialog;
import com.justingruenberg.beatyourneat.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton ib_settingsFragmentEditUser, ib_settingsFragmentEditPassword;
    private Switch s_settingsFragmentDarkMode, s_settingsFragmentLogout, s_settingsFragmentDeleteAccount;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ib_settingsFragmentEditUser = view.findViewById(R.id.ib_settingsFragmentEditUser);
        ib_settingsFragmentEditPassword = view.findViewById(R.id.ib_settingsFragmentEditPassword);
        s_settingsFragmentDarkMode = view.findViewById(R.id.s_settingsFragmentDarkMode);
        s_settingsFragmentLogout = view.findViewById(R.id.s_settingsFragmentLogout);
        s_settingsFragmentDeleteAccount = view.findViewById(R.id.s_settingsFragmentDeleteAccount);

        ib_settingsFragmentEditUser.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.equals(ib_settingsFragmentEditUser)){
            ChangeUserDialog changeUserDialog = new ChangeUserDialog();
            changeUserDialog.show(getFragmentManager(), "ChangeUserDialog");
        }
        else if(view.equals(ib_settingsFragmentEditPassword)){

        }

    }
}