package com.example.zalla.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zalla.CategoryProducts;
import com.example.zalla.MainActivity;
import com.example.zalla.R;
import com.example.zalla.Search;
import com.example.zalla.fragment.AllProducts;
import com.example.zalla.model.Categories;
import com.example.zalla.model.Products;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    Context context;
    ArrayList<Categories> categoriesArrayList;

    public CategoriesAdapter(Context context, ArrayList<Categories> categoriesArrayList){
        this.context = context;
        this.categoriesArrayList = categoriesArrayList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category,
                parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Categories categories = categoriesArrayList.get(position);
        holder.categoriesId.setText(categories.getCategoryId());
        holder.categoriesName.setText(categories.getCategoryName());

        holder.categoriesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryProducts.class);
                intent.putExtra(CategoryProducts.EXTRA_CATEGORY_ID, categoriesArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder{
        CardView categoriesCV;
        TextView categoriesId, categoriesName;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesCV = itemView.findViewById(R.id.cardViewHoriSVCategory);
            categoriesId = itemView.findViewById(R.id.categoryId);
            categoriesName = itemView.findViewById(R.id.categoryTV);
        }
    }
}
