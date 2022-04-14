package com.amoschoojs.fit3077;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OnSiteTestingActivity extends AppCompatActivity {

    Boolean feverStatus, coughStatus, throatStatus, fatigueStatus, diarrheaStatus, nauseaStatus, jointStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_site_testing);

        TextView testRecView = findViewById(R.id.RecommendedTestTxt);
        testRecView.setVisibility(View.INVISIBLE);

        RadioGroup feverRadioGroup = findViewById(R.id.FeverRadioGroup);
        Log.d("myTag", String.valueOf(R.id.FeverRadioGroup));
        RadioGroup coughRadioGroup = findViewById(R.id.CoughRadioGroup);
        RadioGroup throatRadioGroup = findViewById(R.id.ThroatRadioGroup);
        RadioGroup fatigueRadioGroup = findViewById(R.id.FatigueRadioGroup);
        RadioGroup diarrheaRadioGroup = findViewById(R.id.DiarrheaRadioGroup);
        RadioGroup nauseaRadioGroup = findViewById(R.id.NauseaRadioGroup);
        RadioGroup jointRadioGroup = findViewById(R.id.JointRadioGroup);

        Log.d("myTag", String.valueOf(R.id.FeverRadioGroup));

        Button recButton = findViewById(R.id.RecButton);
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    feverStatus = checkStatus(feverRadioGroup);
                    coughStatus = checkStatus(coughRadioGroup);
                    throatStatus = checkStatus(throatRadioGroup);
                    fatigueStatus = checkStatus(fatigueRadioGroup);
                    diarrheaStatus = checkStatus(diarrheaRadioGroup);
                    nauseaStatus = checkStatus(nauseaRadioGroup);
                    jointStatus = checkStatus(jointRadioGroup);

                    if (feverStatus || coughStatus || throatStatus || fatigueStatus || diarrheaStatus || nauseaStatus || jointStatus) {
                        // recommend PCR Test
                        testRecView.setText("PCR Test");

                    } else {
                        // recommend RAT Test
                        testRecView.setText("RAT Test");
                    }
                    testRecView.setVisibility(View.VISIBLE);

                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Please Input All Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean checkStatus(RadioGroup v) throws NullPointerException {
        int radioId = v.getCheckedRadioButtonId();

        RadioButton buttonSelected = findViewById(radioId);
        String choice = buttonSelected.getText().toString();
        return choice.equalsIgnoreCase("Yes");
    }
}