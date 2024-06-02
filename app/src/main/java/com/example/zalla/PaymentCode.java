package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zalla.MainActivity;

public class PaymentCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_code);

        final TextView vCode = findViewById(R.id.codeVirtual);
        final ImageButton copCode = findViewById(R.id.copyBtn);
        final Button okBtn = findViewById(R.id.okPayBtn);

        copCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView", vCode.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(PaymentCode.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentCode.this, MainActivity.class));
                finish();
            }
        });

    }
}