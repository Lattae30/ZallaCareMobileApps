package com.example.zalla.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zalla.CategoryProducts;
import com.example.zalla.DetailProduct;
import com.example.zalla.R;
import com.example.zalla.model.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    Context context;
    ArrayList<Products> productsArrayList;

    public ProductsAdapter(Context context, ArrayList<Products> productsArrayList){
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product_vertical,
                parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Products products = productsArrayList.get(position);
        holder.productId.setText(products.getProductsId());
        holder.productName.setText(products.getProductName());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID")); // Use "id" for Indonesian Rupiah
        String formattedPrice = format.format(products.getPrice());
        holder.price.setText(formattedPrice);

        //LOAD IMAGE
        String pdImageFile = products.getProductImage();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference pdImageRef = storageReference.child("images/" + pdImageFile);

        pdImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Picasso for Load the Image to Image View
                Picasso.get().load(uri).into(holder.productImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        // GET PRODUCT ID FOR MOVING INTO PRODUCT DETAIL
        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(DetailProduct.EXTRA_PRODUCTS, productsArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder{
        CardView cardViewProduct;
        ImageView productImg;
        TextView productName, price, productId;
        Button btnBuyNow;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewProduct = itemView.findViewById(R.id.cardViewHoriSV);
            productImg = itemView.findViewById(R.id.productImgHoriSV);
            productName = itemView.findViewById(R.id.productNameHoriSV);
            price = itemView.findViewById(R.id.productPriceHoriSV);
            productId = itemView.findViewById(R.id.productId);
//            btnBuyNow = itemView.findViewById(R.id.btnBuyNowHoriSV);
        }
    }
}
