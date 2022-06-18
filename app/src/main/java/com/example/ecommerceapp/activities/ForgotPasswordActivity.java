package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText et_email;
    private Button submit_btn;
    private ImageView back_btn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        applyFullScreen();

        firebaseAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        submit_btn = findViewById(R.id.submit_btn);
        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPasswordResetEmail();
            }
        });
    }

    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            showErrorSnackBar("Email Id is Empty", true);
            return false;
        }
        else {
            return true;
        }
    }

    private void sendPasswordResetEmail() {
        if(validateEmail()) {
            showProgressDialog("Please Wait");
            String email = et_email.getText().toString().trim();
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            hideProgressDialog();
                            if(task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Password Reset Email sent", Toast.LENGTH_SHORT).show();
                                finish();
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