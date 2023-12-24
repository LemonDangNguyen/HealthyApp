package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Setting extends BaseActivity {
    ImageView btnSetting, btnHome, btnDetails, btnPractice;
    LinearLayout btnSignOut, Theme, Language, proFile;
    GoogleSignInOptions gso;
    GoogleSignInClient gscl;

    private TextView userNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
            anhxa();
            underbtn();

            getnameuser();



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ;
        gscl = GoogleSignIn.getClient (  this, gso);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        Theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme();
            }
        });
        Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, Language.class);
                startActivity(i);
            }
        });
        proFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, Profile.class);
                startActivity(i);
            }
        });


    }

    private void getnameuser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid(); // Lấy ID người dùng

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    if (username != null) {
                        userNameTextView.setText("Xin chào, " + username);
                    } else {
                        userNameTextView.setText("Xin chào NoName");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
    }


    private void changeTheme() {
        Intent i = new Intent(Setting.this, Theme.class);
        startActivity(i);
    }


    void anhxa(){
        proFile =   findViewById(R.id.btnProfile);
        Language    =   findViewById(R.id.btnLanguage);
        Theme      =    findViewById(R.id.btnTheme);
        userNameTextView    =   findViewById(R.id.txtUserX);
        btnSetting = findViewById(R.id.btnsetting);
        btnHome    = findViewById(R.id.btnhome);
        btnDetails = findViewById(R.id.btndetails);
        btnPractice= findViewById(R.id.btnpractice);
        btnSignOut  =    findViewById(R.id.btnlogout);
    }
    void underbtn() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, Setting.class);
                startActivity(i);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, Practice.class);
                startActivity(i);
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, DetailsHealth.class);
                startActivity(i);
            }
        });
    }
    private void signOut() {
        gscl.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                Toast.makeText(getApplicationContext(), "dang xuat thanh cong", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Setting.this, SignIn.class));
               // Toast.makeText(getApplicationContext(), "dang xuat thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}