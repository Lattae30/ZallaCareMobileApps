package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyCode extends AppCompatActivity {
    private EditText otp1, otp2, otp3, otp4;
    private TextView resendBtn;
    //aktif setelah 60 detik
    private boolean resendEnabled = false;
    //resend time
    private int resendTime = 60;
    private int selectedETPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);

        resendBtn = findViewById(R.id.resendBtn);

        final Button verifyBtn = findViewById(R.id.verifyButton);


        // get email and phone number from sign up through intent
        final String getEmail = getIntent().getStringExtra("Email");
        final String getPhone = getIntent().getStringExtra("Phone Number");

        //Intent to Home Page
        ImageButton changeEmailBckBtn = findViewById(R.id.verifyBackBtn);
        changeEmailBckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyCode.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Confirm Button
        Button btnConfirm = findViewById(R.id.verifyButton);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyCode.this, SuccessChangeEmail.class);
                startActivity(intent);
            }
        });

        otp1.addTextChangedListener(textWatcher);
        otp2.addTextChangedListener(textWatcher);
        otp3.addTextChangedListener(textWatcher);
        otp4.addTextChangedListener(textWatcher);

        // by default open keyboard at opt1
        showKeyboard(otp1);

        //mulai count down timer
        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resendEnabled){
                    //handle resend code

                    //start new resend count
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String generatedOtp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();

                if (generatedOtp.length() == 4){
                    //handle otp
                    Intent intent = new Intent(VerifyCode.this, SuccessChangeEmail.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "The Verification Code Must Have 4 Digits", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showKeyboard(EditText otp){

        otp.requestFocus();

        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#6E6E6E"));

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long l) {
                resendBtn.setText("Resend Code("+(l / 1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));
            }
        }.start();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0){
                if (selectedETPosition == 0){

                    selectedETPosition = 1;
                    showKeyboard(otp2);
                }
                else if(selectedETPosition == 1){
                    selectedETPosition = 2;
                    showKeyboard(otp3);
                }
                else if (selectedETPosition == 2){
                    selectedETPosition = 3;
                    showKeyboard(otp4);
                }

            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if (selectedETPosition == 3){

                selectedETPosition=2;
                showKeyboard(otp3);

            }
            else if (selectedETPosition == 2){
                selectedETPosition = 1;
                showKeyboard(otp2);
            }
            else if (selectedETPosition == 1){
                selectedETPosition = 0;
                showKeyboard(otp1);
            }
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }

    }
}
