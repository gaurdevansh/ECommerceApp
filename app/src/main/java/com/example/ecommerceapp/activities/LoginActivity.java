package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_register, tv_forgot_password;
    private EditText et_email, et_password;
    private Button login_btn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        applyFullScreen();

        firebaseAuth = FirebaseAuth.getInstance();

        tv_register = findViewById(R.id.tv_register);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v!=null) {
            if(v==findViewById(R.id.login_btn)) {
                loginRegisteredUser();
            }
            else if(v == findViewById(R.id.tv_register)) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            else if(v == findViewById(R.id.tv_forgot_password)) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean validateLoginDetails() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            showErrorSnackBar("Email Id is empty", true);
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            showErrorSnackBar("Password is empty", true);
            return false;
        }
        else
        {
            return true;
        }
    }

    private void loginRegisteredUser() {
        if(validateLoginDetails()) {
            showProgressDialog("Please Wait");
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideProgressDialog();
                            if(task.isSuccessful()) {
                                showErrorSnackBar("You are logged in successfully", false);
                            }
                            else
                            {
                                showErrorSnackBar(task.getException().getMessage(), true);
                            }
                        }
                    });
        }
    }
}