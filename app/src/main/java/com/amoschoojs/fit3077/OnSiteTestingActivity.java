package com.amoschoojs.fit3077;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OnSiteTestingActivity extends AppCompatActivity {

    Boolean feverStatus, coughStatus, throatStatus, fatigueStatus, diarrheaStatus, nauseaStatus, jointStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_site_testing);

        RadioGroup feverRadioGroup = findViewById(R.id.FeverRadioGroup);
        RadioGroup coughRadioGroup = findViewById(R.id.CoughRadioGroup);
        RadioGroup throatRadioGroup = findViewById(R.id.ThroatRadioGroup);
        RadioGroup fatigueRadioGroup = findViewById(R.id.FatigueRadioGroup);
        RadioGroup diarrheaRadioGroup = findViewById(R.id.DiarrheaRadioGroup);
        RadioGroup nauseaRadioGroup = findViewById(R.id.NauseaRadioGroup);
        RadioGroup jointRadioGroup = findViewById(R.id.JointRadioGroup);

        feverStatus = checkStatus(feverRadioGroup);
        coughStatus = checkStatus(coughRadioGroup);
        throatStatus = checkStatus(throatRadioGroup);
        fatigueStatus = checkStatus(fatigueRadioGroup);
        diarrheaStatus = checkStatus(diarrheaRadioGroup);
        nauseaStatus = checkStatus(nauseaRadioGroup);
        jointStatus = checkStatus(jointRadioGroup);

        if (feverStatus || coughStatus || throatStatus || fatigueStatus || diarrheaStatus || nauseaStatus || jointStatus) {
            // recommend PCR Test
        } else {
            // recommend RAT Test
        }


    }

    private boolean checkStatus(RadioGroup v) {
        int radioId = v.getCheckedRadioButtonId();

        RadioButton buttonSelected = findViewById(radioId);
        String choice = buttonSelected.getText().toString();
        return choice.equalsIgnoreCase("Yes");
    }
}