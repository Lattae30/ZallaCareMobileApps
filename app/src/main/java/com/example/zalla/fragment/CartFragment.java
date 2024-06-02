package com.example.zalla.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zalla.PaymentMethod;
import com.example.zalla.R;

import org.w3c.dom.Text;

public class CartFragment extends Fragment {
    Button btnIncrease, btnDecrease, btnBuyNow;
    TextView txtQuantity;
    int count = 0;

    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        btnIncrease = (Button) view.findViewById(R.id.btnIncrement);
        btnDecrease = (Button) view.findViewById(R.id.btnDecrement);
        txtQuantity = (TextView) view.findViewById(R.id.txtQty);
        btnBuyNow = (Button) view.findViewById(R.id.cartBuyNow);

        //Increament item
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count ++;
                txtQuantity.setText(""+count);
            }
        });

        //Decrement Item
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=0){
                    count = 0;
                } else{
                    count--;
                    txtQuantity.setText(""+count);
                }
            }
        });

        //Intent to payment method
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentMethod.class);
                startActivity(intent);
            }
        });
        return view;
    }
}