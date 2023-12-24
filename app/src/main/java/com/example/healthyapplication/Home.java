package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import android.os.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends BaseActivity {
   // private static final int REQUEST_LOCATION_PERMISSION = 123;
 //   private FusedLocationProviderClient fusedLocationProviderClient;
  //  private String apiKey = "YOUR_API_KEY";

    ImageView btnSetting, btnHome, btnDetails, btnPractice;
    TextView thongKeNgay, thoiTiet, huyetAp, nhipTim, buocChan, canNang;
    ImageView   avatar;
private TextView userNameTextView;

    // xử lý nút back
    private boolean isMainActivity = true;

    @Override
    public void onBackPressed() {
        if (isMainActivity == true) {
            finish(); // Thoát ứng dụng khi ở MainActivity
        } else {
            super.onBackPressed(); // Xử lý nút trở về bình thường khi không ở MainActivity
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        isMainActivity = true;





        anhxa();
        underbtn();
        getngayTK();
        getnameUser();
        gethuyetap();
        getnhiptim();
        getbuocchan();
        getcannang();
//        updateWeatherAndLocation();

        thoiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, ManHinh1.class);
                startActivity(i);
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
                        canNang.setText(weight + " KG");
                    } else {
                        canNang.setText("Cân nặng: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
    }


    private void getbuocchan() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Thay đổi kiểu của biến steps từ String thành Long
                    Long steps = dataSnapshot.child("steps").getValue(Long.class);
                    if (steps != null) {
                        buocChan.setText(String.valueOf(steps)+ "bước");
                    } else {
                        buocChan.setText("Bước đi: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
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
                        nhipTim.setText(heartRate + " BPM");
                    } else {
                        nhipTim.setText("Nhịp tim: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });
        
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
                        huyetAp.setText(bloodPressure + " mmHg");
                    } else {
                        huyetAp.setText("Huyết áp: N/A");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có.
            }
        });


        
    }

    private void getnameUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userName = user.getDisplayName();
            if (userName != null) {
                userNameTextView.setText("Xin chào, " + userName);
            } else {
                userNameTextView.setText("Xin chào");
            }
        } else {
            // Người dùng chưa đăng nhập, xử lý theo cách thích hợp
        }
    }


    private void getngayTK() {
        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();

// Định dạng ngày tháng năm dưới dạng chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());

// Hiển thị ngày tháng năm trong TextView
        String textToDisplay = "Thống kê ngày " + formattedDate;
        thongKeNgay.setText(textToDisplay);
    }

    void anhxa(){
        huyetAp = findViewById(R.id.huyetap);
        nhipTim =   findViewById(R.id.nhiptim);
        canNang =   findViewById(R.id.cannang);
        buocChan=   findViewById(R.id.buocchan);
        userNameTextView    =   findViewById(R.id.textNameUser);
        btnSetting = findViewById(R.id.btnsettingh);
        btnHome    = findViewById(R.id.btnhomeh);
        btnDetails = findViewById(R.id.btndetailsh);
        btnPractice= findViewById(R.id.btnpracticeh);
        thongKeNgay=findViewById(R.id.thongke);
        thoiTiet=findViewById(R.id.thoitiet);

    }
    void underbtn() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Setting.class);
                startActivity(i);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Practice.class);
                startActivity(i);
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, DetailsHealth.class);
                startActivity(i);
            }
        });
    }




//    private void updateWeatherAndLocation() {
//        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//
//                        // Gọi API thời tiết với thông tin vị trí
//                        getWeatherData(latitude, longitude);
//                    } else {
//                        // Xử lý trường hợp không có vị trí
//                    }
//                }
//            });
//        } else {
//            // Yêu cầu quyền truy cập vị trí
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_LOCATION_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Quyền truy cập vị trí đã được cấp, bạn có thể gọi lại phương thức updateWeatherAndLocation() ở đây.
//                updateWeatherAndLocation();
//            } else {
//                // Xử lý khi người dùng từ chối cấp quyền truy cập vị trí
//            }
//        }
//    }
//
//    private void getWeatherData(double latitude, double longitude) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
//        Call<WeatherData> call = weatherApi.getWeatherData(latitude, longitude, apiKey);
//
//        call.enqueue(new Callback<WeatherData>() {
//            @Override
//            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
//                if (response.isSuccessful()) {
//                    WeatherData weatherData = response.body();
//                    if (weatherData != null) {
//                        float temperature = weatherData.main.temp - 273.15f; // Chuyển đổi từ độ K thành độ C
//                        String weatherInfo = weatherData.name + ", nhiệt độ " + temperature + "°C";
//                        thoiTiet.setText(weatherInfo);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherData> call, Throwable t) {
//                // Xử lý lỗi khi gọi API thời tiết
//            }
//        });
//    }
}
