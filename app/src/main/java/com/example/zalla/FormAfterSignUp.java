package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormAfterSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_after_sign_up);

        //Intent to Home Page
        Button btnConfirm = findViewById(R.id.confirmUserProfileForm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormAfterSignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}