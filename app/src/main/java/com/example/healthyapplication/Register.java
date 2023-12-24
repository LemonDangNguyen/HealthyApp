package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
        SignInButton btnlg;
        MaterialButton btndky;
        EditText txtUserName, txtEmail, txtPass, txtconpass;
        private boolean isPasswordMatch = false;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhxa();
        login();
        txtconpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp nhau không
                isPasswordMatch = TextUtils.equals(editable, txtPass.getText().toString());

                // Hiển thị thông báo nếu mật khẩu không khớp
                if (!isPasswordMatch) {
                    txtconpass.setError("Mật khẩu không khớp");
                }
            }
        });

        btndky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp nhau không
                if (isPasswordMatch) {
                    dangky();
                } else {
                    Toast.makeText(Register.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dangky() {
        String username, email, pass, conpass;

        email= txtEmail.getText().toString();
        pass= txtPass.getText().toString();
      //  conpass = txtconpass.getText().toString();

//        if( email.isEmpty()||pass.isEmpty()||conpass.isEmpty())
//        {
//            Toast.makeText(this,"Vui Lòng ĐIền Đầy Đủ Thông Tin!", Toast.LENGTH_SHORT).show();
//        } else if (!pass.equals(conpass)) {
//            Toast.makeText(this,"Mật Khẩu Không giống nhau", Toast.LENGTH_SHORT).show();
//        }


        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText( getApplicationContext(),  "Tao tai khoan thanh congl!!", Toast. LENGTH_SHORT). show();
                    Intent i = new Intent(Register.this, Home.class);
                    startActivity(i);
                }else {
                    Toast.makeText( getApplicationContext(),  "Tao tai khoan khonng thanh congl!!", Toast. LENGTH_SHORT). show();
                }
            }
        });
    }

    private void login() {
        btnlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, SignIn.class);
                startActivity(i);

            }
        });
    }

    private void anhxa() {
        btndky  =   findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        btnlg = findViewById(R.id.signggbtn);
       // txtUserName =   findViewById(R.id.userName);
        txtEmail    =   findViewById(R.id.inputEmail);
        txtPass     =   findViewById(R.id.inputPassword);
        txtconpass  =   findViewById(R.id.inputConfirmPassword);
    }
}