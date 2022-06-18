package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity {

    private Dialog progressDialog;

    void applyFullScreen() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        }
        else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }

    void showErrorSnackBar(String msg, boolean errorMsg) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content), msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        if(errorMsg) {
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorSnackBarError));
        }
        else
        {
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorSnackBarSuccess));
        }
        snackbar.show();
    }

    void showProgressDialog(String text) {
        progressDialog = new Dialog(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        TextView textView = view.findViewById(R.id.tv_progress);
        textView.setText(text);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    void hideProgressDialog() {
        progressDialog.dismiss();
    }
}