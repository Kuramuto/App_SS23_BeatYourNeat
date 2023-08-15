package com.justingruenberg.beatyourneat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justingruenberg.beatyourneat.Model.DAO.DBHelper;
import com.justingruenberg.beatyourneat.Model.DAO.UserDAO;
import com.justingruenberg.beatyourneat.Model.UserManager;
import com.justingruenberg.beatyourneat.Model.UserModel;
import com.justingruenberg.beatyourneat.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_loginUser, et_loginPassword;
    private Button bt_loginLogin, bt_loginRegister;
    private UserManager instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        et_loginUser = findViewById(R.id.et_loginUser);
        et_loginPassword = findViewById(R.id.et_loginPassword);
        bt_loginLogin = findViewById(R.id.bt_loginLogin);
        bt_loginRegister = findViewById(R.id.bt_loginRegister);
        instance = UserManager.getInstance();

        bt_loginLogin.setOnClickListener(this);
        bt_loginRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(bt_loginLogin)) {
            UserDAO userDAO = new UserDAO(LoginActivity.this);
            String username = et_loginUser.getText().toString();
            String password = et_loginPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "login with your username and password", Toast.LENGTH_SHORT).show();
            } else {
                if (userDAO.userExists(username)) {
                    UserModel user = userDAO.get(username);
                    instance.setUser(user);
                    if (password.equals(user.getPassword())) {
                        if (user.getUserProfile() == null) {
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, AddingWeightActivity.class));
                        }

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