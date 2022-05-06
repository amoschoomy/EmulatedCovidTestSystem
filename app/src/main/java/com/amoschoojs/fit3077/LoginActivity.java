package com.amoschoojs.fit3077;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

import models.ExceptionPackage.InvalidCredentialsException;
import models.ExceptionPackage.InvalidRoleException;
import models.LoginSystemPackage.LoginAuthentication;
import models.LoginSystemPackage.LoginSystem;

public class LoginActivity extends AppCompatActivity {


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

        // Creating the drop down menu
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
    // test username: jrymdr
    // test password: jrymdr
    loginButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();
            String userRole = userRoleSelected[0];

            try {
              String jwt = ls.checkCredentials(username, password);

              //                    LoginSystem.VerifyJWTToken(jwt);
              LoginAuthentication newInstance = LoginAuthentication.getInstance();
              newInstance.setUser(jwt, userRole);

              Intent switchActivityIntent =
                  new Intent(getApplicationContext(), HomeActivity.class);
              startActivity(switchActivityIntent);
              Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_LONG).show();

            } catch (IOException | JSONException e) {
              e.printStackTrace();
            } catch (InvalidRoleException | InvalidCredentialsException e) {
              Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
          }
        });
    }


}