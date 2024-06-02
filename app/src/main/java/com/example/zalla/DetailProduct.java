package com.example.zalla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zalla.controller.ProductsAdapter;
import com.example.zalla.model.Categories;
import com.example.zalla.model.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailProduct extends AppCompatActivity {
    public static final String EXTRA_PRODUCTS = "extra_products";
    private DatabaseReference databaseProductRef;
    private ProductsAdapter productsAdapter;
    private Products products;
    private String productId="";
    ImageButton imageButtonBack, imageButtonCart;
    Button btnSize, btnBuy;
    ImageView detailImg;
    TextView detailNameTitle, detailName, detailPrice, detailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        products = (Products) getIntent().getSerializableExtra(EXTRA_PRODUCTS);

        //DATABASE CONFIGURATION
        databaseProductRef = FirebaseDatabase.getInstance().getReference("products");

        detailNameTitle = findViewById(R.id.detailProductNameTitle);
        detailImg = findViewById(R.id.detailProductImg);
        detailName = findViewById(R.id.detailProductName);
        detailPrice = findViewById(R.id.detailProductPrice);
        detailDesc = findViewById(R.id.detailDescription);

        productId = products.getProductsId();

        detailNameTitle.setText(products.getProductName());
        detailName.setText(products.getProductName());

        //PRICE RUPIAH'S FORMAT
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID")); // Use "id" for Indonesian Rupiah
        String formattedPrice = format.format(products.getPrice());
        detailPrice.setText(formattedPrice);

        detailDesc.setText(products.getDesc());


        //DISPLAYING IMAGE
        String imgFileName = products.getProductImage();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageReference = storageReference.child("images/" + imgFileName);

        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(detailImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        //Intent Back Button to MainActivity
        imageButtonBack = findViewById(R.id.productDetailBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Size Button Buy Now
//        btnBuy = findViewById(R.id.detailBtnBuy);
//        btnBuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }
}