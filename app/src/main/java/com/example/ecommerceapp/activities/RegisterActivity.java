package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private TextView tv_login, tv_terms_conditions;
    private CheckBox cb_terms_conditions;
    private EditText et_first_name, et_last_name, et_email, et_password, et_confirm_password;
    private Button register_btn;
    private ImageView back_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        applyFullScreen();
        firebaseAuth = FirebaseAuth.getInstance();

        tv_login = findViewById(R.id.tv_login);
        tv_terms_conditions = findViewById(R.id.tv_terms_and_conditions);
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        register_btn = findViewById(R.id.register_btn);
        cb_terms_conditions = findViewById(R.id.cb_terms_conditions);
        back_btn = findViewById(R.id.back_btn);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setUpActionBar();
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private boolean validateRegisteredDetails() {
        String firstName = et_first_name.getText().toString().trim();
        String lastName = et_last_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirmPassword = et_confirm_password.getText().toString().trim();

        if(TextUtils.isEmpty(firstName)) {
            showErrorSnackBar("First Name is Empty", true);
            return false;
        }
        if(TextUtils.isEmpty(lastName)) {
            showErrorSnackBar("Last Name is Empty", true);
            return false;
        }
        if(TextUtils.isEmpty(email)) {
            showErrorSnackBar("Email ID is Empty", true);
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            showErrorSnackBar("Password is Empty", true);
            return false;
        }
        if(TextUtils.isEmpty(confirmPassword)) {
            showErrorSnackBar("Confirm Password is Empty", true);
            return false;
        }
        if(!(password.equals(confirmPassword))) {
            showErrorSnackBar("Passwords do not match", true);
            return false;
        }
        if(!cb_terms_conditions.isChecked()) {
            showErrorSnackBar("Please Accept Terms and Conditions", true);
            return false;
        }
        else {
            return true;
        }
    }

    private void setUpActionBar() {
        Toolbar toolbar = findViewById(R.id.register_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void registerUser() {
        if(validateRegisteredDetails()) {
            showProgressDialog("Please Wait");
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideProgressDialog();
                            if(task.isSuccessful()) {
                                firebaseUser = task.getResult().getUser();
                                showErrorSnackBar("You are registered Successfully. Your Id is "+firebaseUser.getUid(), false);
                                firebaseAuth.signOut();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                },1000);

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