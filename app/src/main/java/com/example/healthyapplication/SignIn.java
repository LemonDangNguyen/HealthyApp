package com.example.healthyapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignIn extends AppCompatActivity {
    SignInButton ggsign;
    TextView txtdky, txtquenmk;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInOptions gso;
    GoogleSignInClient gscl;
    ProgressDialog progressDialog;

    EditText txtusername, txtpassword;
    MaterialButton btndn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        txtquenmk   =   findViewById(R.id.forgotpass);
        txtusername =findViewById(R.id.inputNamesign);
        txtpassword = findViewById(R.id.inputPasswordsign);

        ggsign = findViewById(R.id.signggbtn);
        txtdky = findViewById(R.id.textRegesterAccount);
        btndn = findViewById(R.id.buttonSignIn);




        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        progressDialog = new ProgressDialog(  SignIn.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("we are creating your account");


        // 200585081158-bn45d8j9j4s75gb6d0guppanag7k3j39.apps.googleusercontent.com
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build() ;


        gscl = GoogleSignIn.getClient (  this, gso);



        // bấm vào đăng ký tài khoản
        txtdky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Register.class);
                startActivity(intent);

            }
        });


        ggsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIN();

            }
        });


        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInemail();
            }
        });

        txtquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                View view = LayoutInflater.from(SignIn.this).inflate(R.layout.notifix, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

//                Intent i =  new Intent(SignIn.this, emailquenmk.class);
//                startActivity(i);
            }
        });

    }

    private void signInemail() {

            String email, pass;
            email= txtusername.getText().toString();
            pass= txtpassword.getText().toString();

            if(TextUtils. isEmpty(email)){
                Toast.makeText(  this,  "Vui Lòng nhâp email!!", Toast. LENGTH_SHORT). show(); return;}
            if(TextUtils. isEmpty(pass)){
                Toast.makeText(  this,  "Vui Lòng nhâp pass!!", Toast. LENGTH_SHORT). show(); return;}


            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignIn.this, Home.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(), "dang nhap khonggg thanh cong", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }


    private void signIN() {
        Intent signInten = gscl.getSignInIntent();
        startActivityForResult(signInten, 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //navigateToSecondActivity();
                firbaseAuth(account.getIdToken());


            } catch (ApiException e) {
                Toast.makeText(this, "something went wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firbaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider. getCredential(idToken,  null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            //Hàm onComplete trong đoạn mã  cấp là
            // một phần của callback được sử dụng khi thực hiện xác thực người dùng bằng tài khoản Google
            // bằng Firebase Authentication.
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = auth.getCurrentUser();

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", user.getUid());
                    map.put ("name", user.getDisplayName ());
                    map.put ("profile", user.getPhotoUrl().toString());


                    // Lưu thông tin người dùng vào cơ sở dữ liệu Firebase Realtime Database

                    database.getReference().child("users").child(user.getUid()).setValue(map);

                    Intent i = new Intent(SignIn.this, Home.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText (  SignIn.this,
                            "Xảy ra lỗi trong quá trình xác thực",
                            Toast. LENGTH_SHORT). show();}
            }
        });
    }
    // Hàm lưu trạng thái đăng nhập
    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }






}