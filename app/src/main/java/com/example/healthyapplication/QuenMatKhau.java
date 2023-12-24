package com.example.healthyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QuenMatKhau extends AppCompatActivity {

    private TextView textViewResendCode;
    private Button buttonConfirm;
    private CountDownTimer countDownTimer;
    EditText editTextNewPassword, editTextConfirmPassword, editTextVerificationCode;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

         editTextNewPassword = findViewById(R.id.editTextNewPassword);
         editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextVerificationCode    = findViewById(R.id.editTextVerificationCode1);
        buttonConfirm = findViewById(R.id.buttonConfirm);

// Bộ lắng nghe sự kiện cho ô nhập liệu
        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                // Kiểm tra nếu hai mật khẩu mới khớp nhau
                if (newPassword.equals(confirmPassword)) {
                    buttonConfirm.setEnabled(true); // Bật nút Xác nhận
                } else {
                    buttonConfirm.setEnabled(false); // Tắt nút Xác nhận
                }
            }
        });

        demnguocresend();

    }

    private void demnguocresend() {


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String emailAddress = currentUser.getEmail();
            textViewResendCode.setOnClickListener(view -> {
                resendVerificationCode(emailAddress);
                startCountdown();
            });
        }

        buttonConfirm.setOnClickListener(view -> {
            String verificationCode = editTextVerificationCode.getText().toString();
            String newPassword = editTextNewPassword.getText().toString();

            // Thực hiện xác nhận mã với mã xác nhận và mật khẩu mới
            confirmVerificationCode(verificationCode, newPassword);
        });



    }
    // Hàm gửi lại mã xác nhận qua email
    private void resendVerificationCode(String emailAddress) {
        if (emailAddress != null) {
            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(QuenMatKhau.this, "Đã gửi lại mã xác nhận qua email", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QuenMatKhau.this, "Gửi lại mã xác nhận thất bại", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(QuenMatKhau.this, "Không thể gửi lại mã xác nhận, địa chỉ email không hợp lệ", Toast.LENGTH_LONG).show();
        }
    }

    // Hàm xác nhận mã
    private void confirmVerificationCode(String code, String newPassword) {
        mAuth.checkActionCode(code)
                .addOnSuccessListener(actionCodeInfo -> {
                    // Xác nhận mã xác nhận đã hợp lệ
                    mAuth.confirmPasswordReset(code, newPassword)
                            .addOnSuccessListener(authResult -> {
                                // Xác nhận mã xác nhận thành công
                                Toast.makeText(QuenMatKhau.this, "Xác nhận mã xác nhận thành công", Toast.LENGTH_LONG).show();
                                // Thực hiện các hành động phù hợp sau khi xác nhận thành công
                            })
                            .addOnFailureListener(e -> {
                                // Xác nhận mã xác nhận thất bại
                                Toast.makeText(QuenMatKhau.this, "Xác nhận mã xác nhận thất bại", Toast.LENGTH_LONG).show();
                                // Xử lý lỗi khi xác nhận mã thất bại
                            });
                })
                .addOnFailureListener(e -> {
                    // Mã xác nhận không hợp lệ hoặc có lỗi xảy ra khi xác nhận
                    Toast.makeText(QuenMatKhau.this, "Mã xác nhận không hợp lệ hoặc có lỗi xảy ra", Toast.LENGTH_LONG).show();
                    // Xử lý lỗi khi mã xác nhận không hợp lệ hoặc có lỗi xảy ra
                });
    }



    // Hàm bắt đầu đếm ngược
    private void startCountdown() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                textViewResendCode.setText("Bạn chưa nhận được mã xác nhận, gửi lại sau " + seconds + "s");
                textViewResendCode.setEnabled(false); // Vô hiệu hóa textView trong quá trình đếm ngược
            }

            @Override
            public void onFinish() {
                textViewResendCode.setText("Gửi lại mã xác nhận"); // Hiển thị lại thông báo gửi lại mã
                textViewResendCode.setEnabled(true); // Cho phép người dùng bấm để gửi lại mã
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đảm bảo huỷ đếm ngược khi Activity bị huỷ
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}