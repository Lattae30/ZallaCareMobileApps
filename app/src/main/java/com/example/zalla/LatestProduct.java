package com.example.zalla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LatestProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    Dialog mDialog;
    RadioGroup radioGroup;
    Button btnSize;
    DatabaseReference dbProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_product);

        recyclerView = findViewById(R.id.recyclerViewListProduct);

        //CONNECT WITH DATABASE TO GET THE REFERENCE DATA
        dbProducts = FirebaseDatabase.getInstance().getReference("products");


        //Intent Back Button
        ImageView backBtn = findViewById(R.id.latestBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatestProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}