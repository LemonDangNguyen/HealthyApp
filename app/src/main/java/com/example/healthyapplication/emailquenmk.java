package com.example.healthyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.SignInMethodQueryResult;

public class emailquenmk extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonCheckEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailquenmk);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonCheckEmail = findViewById(R.id.buttonCheckEmail);

//        buttonCheckEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = editTextEmail.getText().toString().trim();
//
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(emailquenmk.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
//                } else {
//                    firebaseAuth.fetchSignInMethodsForEmail(email)
//                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                                    if (task.isSuccessful()) {
//                                        SignInMethodQueryResult result = task.getResult();
//                                        if (result != null && result.getSignInMethods() != null && result.getSignInMethods().isEmpty()) {
//                                            // Email chưa được đăng ký
//                                            Toast.makeText(emailquenmk.this, "Email chưa tồn tại", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            // Gửi email đặt lại mật khẩu và chuyển sang màn hình SignIn
//                                            firebaseAuth.sendPasswordResetEmail(email)
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> passwordResetTask) {
//                                                            if (passwordResetTask.isSuccessful()) {
//                                                                // Gửi email thành công, chuyển sang màn hình SignIn
//                                                                Intent intent = new Intent(emailquenmk.this, SignIn.class);
//                                                                startActivity(intent);
//                                                                finish();
//                                                            } else {
//                                                                // Lỗi khi gửi email đặt lại mật khẩu
//                                                                Toast.makeText(emailquenmk.this, "Lỗi khi gửi email đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        }
//                                                    });
//                                        }
//                                    } else {
//                                        // Xảy ra lỗi khi kiểm tra email
//                                        Toast.makeText(emailquenmk.this, "Lỗi khi kiểm tra email", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//            }
//        });
        buttonCheckEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(emailquenmk.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> passwordResetTask) {
                                    if (passwordResetTask.isSuccessful()) {
                                        // Gửi email thành công, chuyển sang màn hình SignIn
                                        Intent intent = new Intent(emailquenmk.this, SignIn.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Lỗi khi gửi email đặt lại mật khẩu
                                        Toast.makeText(emailquenmk.this, "Lỗi khi gửi email đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
