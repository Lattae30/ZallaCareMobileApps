package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class TopProduct extends AppCompatActivity {
    Dialog mDialog;
    RadioGroup radioGroup;
    Button btnSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_product);

        //Intent to Detail Product
        CardView cardCasualBeige = findViewById(R.id.cardCasualBeigeShirt);
        cardCasualBeige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopProduct.this, DetailProduct.class);
                startActivity(intent);
            }
        });

        //Back Intent
        ImageView backBtn = findViewById(R.id.topProductBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Buy Now Size Overlay
        Button casuaBeigeButtonShirt = findViewById(R.id.btn_casual_Beige);
        mDialog = new Dialog(this);
        casuaBeigeButtonShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.fragment_popup_size);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();

                radioGroup = mDialog.findViewById(R.id.radioGroup);
                radioGroup = mDialog.findViewById(R.id.radioGroup);
                btnSize = mDialog.findViewById(R.id.btn_size);
                btnSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TopProduct.this, PaymentMethod.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}