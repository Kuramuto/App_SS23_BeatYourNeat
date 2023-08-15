package com.justingruenberg.beatyourneat.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.justingruenberg.beatyourneat.Model.DAO.UserDAO;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.UserModel;
import com.justingruenberg.beatyourneat.R;

public class ChangeUserDialog extends DialogFragment {

    private EditText et_changeUserDialogOldUsername;
    private EditText et_changeUserDialogNewUsername;
    private Button Apply;
    UserManager instance;
    private UserDAO userDAO;

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_changeuserdialog, null);

        et_changeUserDialogOldUsername = view.findViewById(R.id.et_changeUserDialogOldUsername);
        et_changeUserDialogNewUsername = view.findViewById(R.id.et_changeUserDialogNewUsername);
        Apply = view.findViewById(R.id.bt_changeUserDialogApply);
        userDAO = new UserDAO(getActivity());
        instance = UserManager.getInstance();

        builder.setView(view)
                .setTitle("Change Username");

        Apply.setOnClickListener(v -> {
            String oldName = et_changeUserDialogOldUsername.getText().toString();
            String newName = et_changeUserDialogNewUsername.getText().toString();

            if(!(oldName.isEmpty() || newName.isEmpty())){
                if(oldName.equals(instance.getCurrentUser().getUserName())) {
                    boolean isUpdateSuccessful = userDAO.updateAllUsernames(oldName, newName);
                    if(isUpdateSuccessful) {
                        UserModel userModel = new UserModel(newName, instance.getCurrentUser().getPassword(), instance.getCurrentUser().getUserProfile());
                        instance.setUser(userModel);
                        Toast.makeText(getActivity(), "Updated Username", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Username already taken", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "That's not your Username", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Username fields cannot be empty", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        });

        return builder.create();
    }
}
