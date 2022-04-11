package com.amoschoojs.fit3077;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONException;

import java.io.IOException;
import java.text.Collator;

import LoginSystemPackage.LoginAuthentication;
import LoginSystemPackage.LoginSystem;


public class LoginActivity extends AppCompatActivity {

    // insert key here
    private static final String myApiKey = "KEY";
    private static final String rootUrl = "https://fit3077.com/api/v1";

    private EditText inputUsername;
    private EditText inputPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.editTextUsername);
        inputPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        LoginSystem ls = new LoginSystem(getString(R.string.api_key));

        // test username: mbrown123
        // test password: mbrown123
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                try {
                    String jwt = ls.checkCredentials(username, password);

//                    String userid = ls.getUserId(jwt);
                    LoginAuthentication newInstance = LoginAuthentication.getInstance(jwt);
//                    loginAuthentication.getInstance().setJWT(jwt);

//                    VerifyJWTToken(output);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}