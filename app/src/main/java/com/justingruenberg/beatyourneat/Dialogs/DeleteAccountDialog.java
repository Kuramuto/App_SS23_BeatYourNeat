package com.justingruenberg.beatyourneat.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.justingruenberg.beatyourneat.Activities.LoginActivity;
import com.justingruenberg.beatyourneat.Model.DAO.UserDAO;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.R;

public class DeleteAccountDialog extends DialogFragment {

    private EditText et_passwordConfirmation;
    private TextView tv_confirm, tv_cancel;
    private UserManager instance;
    private UserDAO userDAO;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_deleteaccountdialog, null);

        et_passwordConfirmation = view.findViewById(R.id.et_passwordConfirmation);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_cancel = view.findViewById(R.id.tv_cancel);

        userDAO = new UserDAO(getActivity());
        instance = UserManager.getInstance();

        builder.setView(view);
        builder.setTitle("Delete Account");

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_passwordConfirmation.getText().toString();

                if (password.equals(instance.getCurrentUser().getPassword())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure?");
                    builder.setMessage("Deleting your account will permanently remove all your data. This action cannot be undone.");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userDAO.delete(instance.getCurrentUser());
                            instance.reset();
                            Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(getActivity(), "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }
}

