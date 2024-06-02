package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EditEmail extends AppCompatActivity {
    Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        //Intent to Home Page
        ImageButton changeEmailBckBtn = findViewById(R.id.changeEmailBackBtn);
        changeEmailBckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEmail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Alert Send Verification Code
        Button btnSendVerifCode = findViewById(R.id.btnSendVerify);
        mDialog = new Dialog(this);
        btnSendVerifCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.alert_verify);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();

                Button btnOk = mDialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EditEmail.this, VerifyCode.class);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                });
            }
        });
    }
}