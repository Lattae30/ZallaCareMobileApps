package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EditPassword extends AppCompatActivity {
    Dialog mDialog;
    ImageView passwordIcon, conPasswordIcon;
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        final EditText password = findViewById(R.id.passwordField);
        final EditText conPassword = findViewById(R.id.conPasswordField);
        final ImageView passwordIcon = findViewById(R.id.pwIcon);
        final ImageView conPasswordIcon = findViewById(R.id.conPasswordIcon);

        //Intent to Home Page
        ImageButton changeEmailBckBtn = findViewById(R.id.changePasswordBackBtn);
        changeEmailBckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Alert Send Verification Code
        Button btnChange = findViewById(R.id.btnChangePw);
        mDialog = new Dialog(this);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.alert_change_password);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();

                Button cancel = mDialog.findViewById(R.id.cancelBtn);
                Button btnOk = mDialog.findViewById(R.id.OkChangePw);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EditPassword.this, UserEdit.class);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EditPassword.this, SuccessChangePassword.class);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                });
            }
        });

        //Visibility Password text
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check password
                if (passwordShowing){
                    passwordShowing = false;

                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                }
                else {
                    passwordShowing = true;

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check password
                if (conPasswordShowing){
                    conPasswordShowing = false;

                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                }
                else {
                    conPasswordShowing = true;

                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_hide);
                }
                conPassword.setSelection(conPassword.length());
            }
        });
    }
}