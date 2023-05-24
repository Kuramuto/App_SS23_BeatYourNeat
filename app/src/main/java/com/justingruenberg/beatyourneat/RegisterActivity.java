package com.justingruenberg.beatyourneat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justingruenberg.beatyourneat.Model.DBHelper.UserModelDBHelper;
import com.justingruenberg.beatyourneat.Model.UserModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_registerUser;
    private EditText et_registerPassword;
    private EditText et_registerPasswordConfirm;
    private Button bt_registerRegister;
    private Button bt_registerCancel;
    private UserModelDBHelper userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        et_registerUser = findViewById(R.id.et_registerUser);
        et_registerPassword = findViewById(R.id.et_registerPassword);
        et_registerPasswordConfirm = findViewById(R.id.et_registerPasswordConfirm);
        bt_registerRegister = findViewById(R.id.bt_registerRegister);
        bt_registerCancel = findViewById(R.id.bt_registerCancel);

        bt_registerRegister.setOnClickListener(this);
        bt_registerCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.equals(bt_registerRegister)) {
            userTable = new UserModelDBHelper(RegisterActivity.this);
            String username = et_registerUser.getText().toString();
            String password = et_registerPassword.getText().toString();
            String password2 = et_registerPasswordConfirm.getText().toString();

            if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "register with username, password and confirm your password", Toast.LENGTH_SHORT).show();
            } else {
                if (!(userTable.userExists(username))) {
                    if (password.equals(password2)) {
                        userTable.add(new UserModel(username, password, false));
                        startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "passwords must be equal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, username + " already exists", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view.equals(bt_registerCancel)) {
            finish();
        }
    }
}