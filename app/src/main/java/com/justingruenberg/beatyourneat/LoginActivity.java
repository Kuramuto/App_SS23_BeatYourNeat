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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_loginUser;
    private EditText et_loginPassword;
    private Button bt_loginLogin;
    private Button bt_loginRegister;
    private UserModelDBHelper userTable;
    private UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        et_loginUser = findViewById(R.id.et_loginUser);
        et_loginPassword = findViewById(R.id.et_loginPassword);
        bt_loginLogin = findViewById(R.id.bt_loginLogin);
        bt_loginRegister = findViewById(R.id.bt_loginRegister);
        bt_loginLogin.setOnClickListener(this);
        bt_loginRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.equals(bt_loginLogin)) {
            userTable = new UserModelDBHelper(LoginActivity.this);
            String username = et_loginUser.getText().toString();
            String password = et_loginPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "login with your username and password", Toast.LENGTH_SHORT).show();
            } else {
                if (userTable.userExists(username)) {
                    user = userTable.get(username);
                    if (password.equals(user.getPassword())) {
                        Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();
                        // Mainpage
                    } else {
                        Toast.makeText(LoginActivity.this, "wrong password for " + username, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, username + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view.equals(bt_loginRegister)) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}