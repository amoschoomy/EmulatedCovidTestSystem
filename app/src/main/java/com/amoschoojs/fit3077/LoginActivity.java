package com.amoschoojs.fit3077;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONException;

import java.io.IOException;
import java.text.Collator;

import LoginSystemPackage.LoginAuthentication;
import LoginSystemPackage.LoginSystem;
import UserPackage.InvalidRoleException;


public class LoginActivity extends AppCompatActivity {

    // insert key here
    private static final String myApiKey = "KEY";
    private static final String rootUrl = "https://fit3077.com/api/v1";

    private EditText inputUsername;
    private EditText inputPassword;
    private Button loginButton;
    private Spinner inputRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.editTextUsername);
        inputPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        inputRole = findViewById(R.id.dropdownmenu);
        String[] roles = new String[] {"Customer", "Receptionist", "Healthcare Worker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        inputRole.setAdapter(adapter);

        final String[] userRoleSelected = {null};
        inputRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userRoleSelected[0] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LoginSystem ls = new LoginSystem(getString(R.string.api_key));

        // test username: mbrown123
        // test password: mbrown123
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                String userRole = userRoleSelected[0];

                try {
                    String jwt = ls.checkCredentials(username, password);
                    LoginAuthentication newInstance = LoginAuthentication.getInstance();
                    newInstance.setUser(jwt, userRole);
//                   VerifyJWTToken(output);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } catch (InvalidRoleException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}