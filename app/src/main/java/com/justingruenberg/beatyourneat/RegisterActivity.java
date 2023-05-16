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

public class RegisterActivity extends AppCompatActivity {

    EditText et_registerUser;
    EditText et_registerPassword;
    EditText et_registerPasswordConfirm;
    Button bt_registerRegister;
    Button bt_registerCancel;
    UserModelDBHelper userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_registerUser = findViewById(R.id.et_registerUser);
        et_registerPassword = findViewById(R.id.et_registerPassword);
        et_registerPasswordConfirm = findViewById(R.id.et_registerPasswordConfirm);
        bt_registerRegister = findViewById(R.id.bt_registerRegister);
        bt_registerCancel = findViewById(R.id.bt_registerCancel);

        bt_registerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTable = new UserModelDBHelper(RegisterActivity.this);
                String username = et_registerUser.getText().toString();
                String password = et_registerPassword.getText().toString();
                String password2 = et_registerPasswordConfirm.getText().toString();

                if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "register with username, password and confirm your password", Toast.LENGTH_SHORT).show();
                } else {
                    if (!(userTable.checkUserExists(username))) {
                        if (password.equals(password2)) {
                            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                            userTable.addUser(new UserModel(username, password));
                            Toast.makeText(RegisterActivity.this, "welcome! Your registration was successful", Toast.LENGTH_SHORT).show();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "passwords must be equal", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, username + " already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bt_registerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}