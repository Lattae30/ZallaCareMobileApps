package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethod extends AppCompatActivity {
    Dialog mDialog;
    private Spinner spinner;
    Button confirmOrderAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        spinner = findViewById(R.id.spinnerPayment);

        List<String> payMethod = new ArrayList<>();
        payMethod.add(0, "Choose Payment Method");
        payMethod.add("Bank BCA");
        payMethod.add("Bank Mandiri");
        payMethod.add("Bank BNI");
        payMethod.add("Bank BRI");

        //style spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, payMethod);

        //Dropdown lyout
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //data adpt to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Choose Payment Method")){
                    //do nothing
                }
                else
                {
                    String item = adapterView.getItemAtPosition(i).toString();

                    //show selected item
                    Toast.makeText(adapterView.getContext(), "Selected: " +item, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button checkBtn = findViewById(R.id.checkoutBtn);
        mDialog = new Dialog(this);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.activity_alert_order);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();

                confirmOrderAlert = mDialog.findViewById(R.id.confirmOrderBtn);
                confirmOrderAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PaymentMethod.this, PaymentCode.class);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                });
            }
        });
    }


}

