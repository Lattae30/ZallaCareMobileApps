package com.example.zalla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.zalla.controller.CategoriesAdapter;
import com.example.zalla.controller.ProductsAdapter;
import com.example.zalla.model.Categories;
import com.example.zalla.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CategoryProducts extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "extra_categoryID";
    private Categories categories;
    private DatabaseReference databaseProductRef, databaseCategoryRef;
    private ProductsAdapter productsAdapter;
    private CategoriesAdapter categoriesAdapter;
    RecyclerView recyclerView;
    private String categoryId= "";
    private String categoryName= "";
    ArrayList<Products> productsArrayList;
    ArrayList<Categories> categoriesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        categories = (Categories) getIntent().getSerializableExtra(EXTRA_CATEGORY_ID);
//        categoryId = categories.getCategoryId();
//        categoryName = categories.getCategoryName();

        //DISPLAYING PRODUCT
        recyclerView = findViewById(R.id.recyclerViewListProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryProducts.this,
                LinearLayoutManager.HORIZONTAL, false));

        //DATABASE CONFIGURATION
        databaseProductRef = FirebaseDatabase.getInstance().getReference("products");
        databaseCategoryRef = FirebaseDatabase.getInstance().getReference("categories");

        //ARRAYLIST FOR DATA
        productsArrayList = new ArrayList<>();

        //Recycler View for product place
        productsAdapter = new ProductsAdapter(CategoryProducts.this, productsArrayList);
        recyclerView.setAdapter(productsAdapter);

        //IF THE CATEGORIES IS NOT NULL
        if(categories != null){
            //Get The Category ID
            categoryId = categories.getCategoryId();
            //Get The Category Name
            categoryName = categories.getCategoryName();

            //DISPLAY THE PRODUCT BASED ON THE SELECTED CATEGORY NAME
            databaseProductRef.orderByChild("categoryName").equalTo(categoryName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String productsId = snapshot.child("productId").getValue(String.class);
                        String productsCode = snapshot.child("productCode").getValue(String.class);
                        String productName = snapshot.child("productName").getValue(String.class);
                        String productImage = snapshot.child("image").getValue(String.class);
                        String categoryName = snapshot.child("categoryName").getValue(String.class);
                        String subCategoryName = snapshot.child("subCategoryName").getValue(String.class);
                        int quantity = Integer.parseInt(snapshot.child("quantity").getValue(String.class));
                        int price = Integer.parseInt(snapshot.child("price").getValue(String.class));
                        float weight = Float.parseFloat(snapshot.child("weight").getValue(String.class));
                        String desc = snapshot.child("desc").getValue(String.class);

                        Products products = new Products(productsId, productsCode, productName, productImage, categoryName,subCategoryName,
                                quantity, price, weight, desc);

                        productsArrayList.add(products);
                    }
                    productsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}