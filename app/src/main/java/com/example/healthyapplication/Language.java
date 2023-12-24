package com.example.healthyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import java.util.Locale;

public class Language extends AppCompatActivity {
    private RadioGroup languageRadioGroup;
    private RadioButton englishRadioButton;
    private RadioButton vietnameseRadioButton;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        languageRadioGroup = findViewById(R.id.languageRadioGroup);
        englishRadioButton = findViewById(R.id.englishRadioButton);
        vietnameseRadioButton = findViewById(R.id.vietnameseRadioButton);
        okButton = findViewById(R.id.okButton);

        // Set the initial language based on device configuration
        Locale currentLocale = getResources().getConfiguration().locale;
        if (currentLocale.getLanguage().equals("en")) {
            englishRadioButton.setChecked(true);
        } else {
            vietnameseRadioButton.setChecked(true);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = languageRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                if (selectedRadioButton.getId() == R.id.englishRadioButton) {
                    setLocale("en");
                } else {
                    setLocale("vi");
                }

                // Restart the activity to apply the selected language
                recreate();
            }
        });
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
