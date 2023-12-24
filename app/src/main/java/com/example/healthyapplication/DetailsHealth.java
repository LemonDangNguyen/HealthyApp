package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DecimalFormat;
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

public class DetailsHealth extends BaseActivity {

    ImageView btnSetting, btnHome, btnDetails, btnPractice;
    TextView dateOnWeek;
    TextView cannang;
    TextView chieucao;
    TextView tuoi;
    TextView bmiyour;
    TextView chieucaoBMI;
    TextView cannangBMI;
    TextView noteBMI;
    private  int cannangBmi, chieucaoBmi;
    float bmr,tuoibmr, caobmr, nangbrm,fbmi;
    public int X, Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_health);
        anhxa();
        underbtn();


        //Ngày--/--/---- đến ngày--/--/----
        showTimeweek();
        
        getcannangbmr();
        getchieucaobmr();
        gettuoibrm();

        getcannangBMI();
        getchieucaoBMI();
        
        getBMI();
        getnoteBMI();
        


    }

    private void getnoteBMI() {
        String notetxtBMI;
        if (fbmi < 18.5) {
            notetxtBMI = "Thiếu cân";
        } else if (fbmi >= 18.5 && fbmi < 25) {
            notetxtBMI = "Bình thường";
        } else if (fbmi >= 25 && fbmi < 30) {
            notetxtBMI = "Thừa cân";
        } else {
            notetxtBMI = "Béo phì";
        }

// Sau khi xác định loại BMI, gán giá trị cho TextView noteBMI
        noteBMI.setText("Tình trạng BMI: " + notetxtBMI);
    }

    private void getBMI() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Long heightLong = dataSnapshot.child("height").getValue(Long.class);

                    Long weightLong = dataSnapshot.child("weight").getValue(Long.class);

                    if (heightLong != null && weightLong != null) {
                        try {

                            int height = heightLong.intValue();
                            int weight = weightLong.intValue();


                            double bmi = calculateBMI(height, weight);


                            DecimalFormat df = new DecimalFormat("#.00");
                            String formattedBMI = df.format(bmi);


                            bmiyour.setText("Your BMI: " + formattedBMI);

                        } catch (NumberFormatException e) {

                        }
                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Hàm tính chỉ số BMI
    private double calculateBMI(int height, int weight) {
        // Công thức tính BMI: BMI = (cân nặng / (chiều cao * chiều cao)) * 10000
        double bmi = (weight / (Math.pow(height , 2))) * 10000;

        return bmi;
    }

    // Sử dụng DecimalFormat để làm tròn kết quả với 2 chữ số sau dấu phẩy
    // DecimalFormat df = new DecimalFormat("#.##");
    // return Double.parseDouble(df.format(bmi));

    private void getchieucaoBMI() {
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
                        chieucaoBMI.setText("Chiều cao: " + height + " cm");
                    } else {
                        chieucaoBMI.setText("Chiều cao: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
    }


    private void getcannangBMI() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Thay đổi kiểu lấy dữ liệu thành Long
                    Long weightLong = dataSnapshot.child("weight").getValue(Long.class);

                    if (weightLong != null) {
                        // Chuyển đổi thành String nếu không phải là null
                        String weight = String.valueOf(weightLong);
                        cannangBMI.setText("Cân nặng: " + weight + " kg");
                    } else {
                        cannangBMI.setText("Cân nặng: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
    }


    private void gettuoibrm() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Thay đổi kiểu lấy dữ liệu thành Long
                    Long ageLong = dataSnapshot.child("age").getValue(Long.class);

                    if (ageLong != null) {
                        // Chuyển đổi thành String nếu không phải là null
                        String age = String.valueOf(ageLong);
                        tuoi.setText("Tuổi: " + age + " tuổi");
                    } else {
                        tuoi.setText("Tuổi: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
    }


    private void getchieucaobmr() {
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
                // Xử lý lỗi nếu có.
            }
        });
    }


    private void getcannangbmr() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object weightObj = dataSnapshot.child("weight").getValue();
                    if (weightObj != null) {
                        String weight = String.valueOf(weightObj);
                        cannang.setText("Cân nặng: " + weight + " kg");
                    } else {
                        cannang.setText("Cân nặng: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
    }



    void anhxa(){
        //cannang, chieucao, tuoi, chieucaoBMI, cannangBMI
        noteBMI =   findViewById(R.id.noteYourBMI);
        bmiyour = findViewById(R.id.yourBMI);
        cannang =findViewById(R.id.txtCannang);
        chieucao    =   findViewById(R.id.txtchieucao);
        tuoi    =   findViewById(R.id.txtTuoi);
        cannangBMI=findViewById(R.id.cannangbmi);
        chieucaoBMI=    findViewById(R.id.chieucaobmi);
        btnSetting = findViewById(R.id.btnsettingdt);
        btnHome    = findViewById(R.id.btnhomedt);
        btnDetails = findViewById(R.id.btndetailsdt);
        btnPractice= findViewById(R.id.btnpracticedt);
        dateOnWeek  = findViewById(R.id.dateonweek);
    }
    void underbtn() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsHealth.this, Setting.class);
                startActivity(i);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsHealth.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsHealth.this, Practice.class);
                startActivity(i);
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsHealth.this, DetailsHealth.class);
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


    /*
    Công thức Harris-Benedict: Công thức này tính toán lượng calo tiêu thụ dựa trên tuổi, giới tính, trọng lượng và chiều cao. Có hai phiên bản, một cho nam và một cho nữ:
	•	Đối với nam: BMR (Basal Metabolic Rate) = 88.362 + (13.397 x trọng lượng kg) + (4.799 x chiều cao cm) - (5.677 x tuổi)
	•	Đối với nữ: BMR = 447.593 + (9.247 x trọng lượng kg) + (3.098 x chiều cao cm) - (4.330 x tuổi)
Sau khi tính được BMR, bạn có thể nhân nó với hệ số hoạt động để tính toán lượng calo cần thiết hàng ngày.
	2.	Hệ số hoạt động: Tính toán thêm lượng calo mà bạn tiêu thụ dựa trên mức hoạt động của bạn. Các hệ số thông thường là:
	•	Rất ít hoặc không vận động: BMR x 1.2
	•	Ít hoạt động (hoạt động nhẹ): BMR x 1.375
	•	Vận động trung bình: BMR x 1.55
	•	Nhiều hoạt động (vận động nặng): BMR x 1.725
	•	Siêng năng, vận động nặng hằng ngày hoặc công việc vận động: BMR x 1.9
     */
}