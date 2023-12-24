package com.example.healthyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;

public class Theme extends AppCompatActivity {
//    private RadioButton radioLight;
//    private RadioButton radioDark;
//    private RadioButton radioSystem;
private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
//        radioLight = findViewById(R.id.radioLight);
//        radioDark = findViewById(R.id.radioDark);
//        radioSystem = findViewById(R.id.radioSystem);

        radioGroup = findViewById(R.id.radioGroup);

    }

    public void onConfirmButtonClick(View view) {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Vui lòng chọn một tùy chọn", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton radioButton = findViewById(selectedId);

            if (radioButton.getId() == R.id.radioLight) {
                setAppTheme(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (radioButton.getId() == R.id.radioDark) {
                setAppTheme(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (radioButton.getId() == R.id.radioSystem) {
                setAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
        }
    }
    private void setAppTheme(int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        recreate(); // Re-apply the theme
    }
}