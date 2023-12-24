package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Practice extends BaseActivity {
    ImageView btnSetting, btnHome, btnDetails, btnPractice;
    TextView txtBuocChan, txtHuyetAp, txtNhipTim, txtnoteNhipTim, txtnoteHuyetAp;
    TextView dateOnWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        anhxa();
        underbtn();
        //Ngày--/--/---- đến ngày--/--/----
        showTimeweek();
        nhipTim();
        huyetap();

    }

    private void huyetap() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long bloodPressure = null;
                    String bloodPressureString = snapshot.child("bloodPressure").getValue(String.class);

                    if (bloodPressureString != null) {
                        // Nếu giá trị không phải là null, thì chuyển đổi thành kiểu Long
                        try {
                            bloodPressure = Long.parseLong(bloodPressureString);
                        } catch (NumberFormatException e) {
                            // Xử lý khi không thể chuyển đổi thành công
                            e.printStackTrace();
                        }
                    }

                    // Hiển thị giá trị huyết áp vào txtHuyetAp
                    txtHuyetAp.setText(bloodPressureString != null ? bloodPressureString : "Giá trị không khả dụng");

                    // Xử lý nhận xét
                    if (bloodPressure != null) {
                        if (bloodPressure >= 60 && bloodPressure <= 100) {
                            txtnoteHuyetAp.setText("Bình Thường");
                        } else if (bloodPressure < 60) {
                            txtnoteHuyetAp.setText("Thấp");
                        } else {
                            txtnoteHuyetAp.setText("Cao");
                        }
                    } else {
                        // Xử lý khi giá trị bloodPressure là null (nếu có)
                        txtnoteHuyetAp.setText("Giá trị không khả dụng");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }



    private void nhipTim() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String heartRate = snapshot.child("heartRate").getValue(String.class);

                    // Hiển thị giá trị nhịp tim vào txtNhipTim
                    txtNhipTim.setText(heartRate);

                    // Xử lý nhận xét
                    int heartRateValue = Integer.parseInt(heartRate);

                    if (heartRateValue >= 60 && heartRateValue <= 100) {
                        txtnoteNhipTim.setText("Bình Thường");
                    } else if (heartRateValue < 60) {
                        txtnoteNhipTim.setText("Thấp");
                    } else {
                        txtnoteNhipTim.setText("Cao");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
            }
        });

    }

    void anhxa(){
        txtHuyetAp  =   findViewById(R.id.txthuyetAp);
        txtnoteHuyetAp  =   findViewById(R.id.noteHuyetAp);
        txtNhipTim  =   findViewById(R.id.nhipTimtxt);
        txtnoteNhipTim  =   findViewById(R.id.noteTim);
        btnSetting = findViewById(R.id.btnsettingp);
        btnHome    = findViewById(R.id.btnhomep);
        btnDetails = findViewById(R.id.btndetailsp);
        btnPractice= findViewById(R.id.btnpracticep);
        dateOnWeek= findViewById(R.id.getday);
    }
    void underbtn() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Practice.this, Setting.class);
                startActivity(i);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Practice.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Practice.this, Practice.class);
                startActivity(i);
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Practice.this, DetailsHealth.class);
                startActivity(i);
            }
        });
    }
    private void showTimeweek() {
        Calendar calendar = Calendar.getInstance();

        // Tìm ngày bắt đầu (Thứ 2)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startDate = calendar.getTime();

        // Tìm ngày kết thúc (Chủ Nhật)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date endDate = calendar.getTime();

        // Định dạng ngày thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateStr = dateFormat.format(startDate);
        String endDateStr = dateFormat.format(endDate);

        // Hiển thị ngày trong TextView
        dateOnWeek.setText("Ngày " + startDateStr + " đến ngày " + endDateStr);
    }
}