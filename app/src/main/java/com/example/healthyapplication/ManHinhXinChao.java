package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManHinhXinChao extends AppCompatActivity {

    TextView showname, ten, gioitinh, tuoi, chieucao, cannang, huyetap, nhiptim, bmi;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_xin_chao);
        showname    =findViewById(R.id.yourname);

        Intent intent = getIntent();
        if(intent != null) {
            String receivedText = intent.getStringExtra("textFromActivity1");

            showname.setText(receivedText);
        }

        ten=    findViewById(R.id.tennguoidung);
        gioitinh    =findViewById(R.id.gioitinh);
        tuoi    =   findViewById(R.id.tuoiage);
        chieucao    = findViewById(R.id.chieucao1);
        cannang  =  findViewById(R.id.cannang1);
        huyetap =   findViewById(R.id.huyetap1);
       // bmi =   findViewById(R.id.bmi1);
        nhiptim     =      findViewById(R.id.nhiptim1);



        getname();
        gettuoi();
        getgioitinh();

        getchieucao();
        getcannang();
        gethuyetap();
        getnhiptim();
      //  getbmi();


    }

    private void getbmi() {

    }

    private void gethuyetap() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String bloodPressure = dataSnapshot.child("bloodPressure").getValue(String.class);
                    if (bloodPressure != null) {
                        huyetap.setText("Huyết ÁP: "+bloodPressure + " mmHg");
                    } else {
                        huyetap.setText("Huyết áp: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getnhiptim() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String heartRate = dataSnapshot.child("heartRate").getValue(String.class);
                    if (heartRate != null) {
                        nhiptim.setText("Nhịp Tim: "+heartRate + " BPM");
                    } else {
                        nhiptim.setText("Nhịp tim: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getcannang() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Thay đổi kiểu của biến weight từ String thành Long
                    Long weight = dataSnapshot.child("weight").getValue(Long.class);
                    if (weight != null) {
                        cannang.setText("Cân Nặng: "+weight + " KG");
                    } else {
                        cannang.setText("Cân nặng: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getchieucao() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Thay đổi kiểu lấy dữ liệu thành Long
                    Long heightLong = dataSnapshot.child("height").getValue(Long.class);

                    if (heightLong != null) {
                        // Chuyển đổi thành String nếu không phải là null
                        String height = String.valueOf(heightLong);
                        chieucao.setText("Chiều cao: " + height + " cm");
                    } else {
                        chieucao.setText("Chiều cao: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getgioitinh() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String sex = dataSnapshot.child("sex").getValue(String.class);
                    if (sex != null) {
                        gioitinh.setText("Giới tính: "+sex);
                    } else {
                        gioitinh.setText("no data");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void gettuoi() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Thay đổi kiểu của biến weight từ String thành Long
                    Long age = dataSnapshot.child("age").getValue(Long.class);
                    if (age != null) {
                        tuoi.setText(age + " Tuổi");
                    } else {
                        tuoi.setText("Cân nặng: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getname() {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            String userId = user.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        if (username != null) {
                            ten.setText(username);
                        } else {
                            ten.setText("Xin chào NoName");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}