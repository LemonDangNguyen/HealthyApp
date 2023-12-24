package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {
    private EditText usernameEditText, ageEditText, sexEditText, heightEditText, weightEditText, heartRateEditText, bloodPressureEditText, stepsEditText;
    private Button confirmButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameEditText = findViewById(R.id.usernameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        sexEditText = findViewById(R.id.sexEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heartRateEditText = findViewById(R.id.heartRateEditText);
        bloodPressureEditText = findViewById(R.id.bloodPressureEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        confirmButton = findViewById(R.id.confirmButton);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String userId = user.getUid(); // Lấy ID người dùng

        // Lấy tham chiếu đến Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                int age = Integer.parseInt(ageEditText.getText().toString());
                String sex = sexEditText.getText().toString();
                int height = Integer.parseInt(heightEditText.getText().toString());
                int weight = Integer.parseInt(weightEditText.getText().toString());
                String heartRate = heartRateEditText.getText().toString();
                String bloodPressure = bloodPressureEditText.getText().toString();
                int steps = Integer.parseInt(stepsEditText.getText().toString());

                Users user = new Users(username, age, sex, height, weight, heartRate, bloodPressure, steps);
                databaseReference.setValue(user);

                // Lưu dữ liệu vào Firebase Realtime Database
            }
        });
    }
}
