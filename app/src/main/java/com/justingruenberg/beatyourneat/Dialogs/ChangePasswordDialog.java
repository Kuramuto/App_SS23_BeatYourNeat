package com.justingruenberg.beatyourneat.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ChangePasswordDialog extends DialogFragment {

    private EditText et_changePasswordDialogOldPassword, et_changePasswordDialogNewPassword;
    private Button bt_changePasswordDialogApply;
    UserManager instance;
    private UserDAO userDAO;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_changepassworddialog, null);

        et_changePasswordDialogOldPassword = view.findViewById(R.id.et_changePasswordDialogOldPassword);
        et_changePasswordDialogNewPassword = view.findViewById(R.id.et_changePasswordDialogNewPassword);
        bt_changePasswordDialogApply = view.findViewById(R.id.bt_changePasswordDialogApply);

        userDAO = new UserDAO(getActivity());
        instance = UserManager.getInstance();

        builder.setView(view)
                .setTitle("Change Password");

        bt_changePasswordDialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = et_changePasswordDialogOldPassword.getText().toString();
                String newPassword = et_changePasswordDialogNewPassword.getText().toString();

                if(!(oldPassword.isEmpty() || newPassword.isEmpty())){
                    if(instance.getCurrentUser().getPassword().equals(oldPassword)){
                        UserModel userModel = new UserModel(instance.getCurrentUser().getUserName(), newPassword, instance.getCurrentUser().getUserProfile());
                        if(userDAO.update(userModel)){
                            instance.setUser(userModel);
                            Toast.makeText(getActivity(), "updated password", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getActivity(), "That's not your password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Password fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }

        });

        return builder.create();
    }
}
