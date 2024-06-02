package com.example.zalla.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.zalla.R;
import com.example.zalla.Search;
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

public class HomeFragment extends Fragment {
    private DatabaseReference databaseProductRef, databaseCategoryRef;
    private ProductsAdapter productsAdapter;
    private CategoriesAdapter categoriesAdapter;
    RecyclerView recyclerView, recyclerViewCategory;
    Dialog mDialog;
    Button casualBeigebtn;
    Button btnTop, btnSize;
    ImageSlider imageSlider;
    ImageView imageView;
    DatabaseReference dbProducts;
    TextView seeMore;
    ArrayList<Products> productsArrayList;
    ArrayList<Categories> categoriesArrayList;
    private Activity activity;

    public HomeFragment(){
        //Required empty constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //DISPLAYING PRODUCT
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        //DISPLAYING CATEGORY
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategories);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        //DATABASE CONFIGURATION
        databaseProductRef = FirebaseDatabase.getInstance().getReference("products");
        databaseCategoryRef = FirebaseDatabase.getInstance().getReference("categories");

        //ARRAYLIST FOR DATA
        productsArrayList = new ArrayList<>();
        categoriesArrayList = new ArrayList<>();

        //Recycler View for product place
        productsAdapter = new ProductsAdapter(getContext(), productsArrayList);
        recyclerView.setAdapter(productsAdapter);

        //RECYCLERVIEW FOR CATEGORY
        categoriesAdapter = new CategoriesAdapter(getContext(), categoriesArrayList);
        recyclerViewCategory.setAdapter(categoriesAdapter);

        //Intent to ALL Product
        seeMore = (TextView) view.findViewById(R.id.seeMore);
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment allProductsFragment = new AllProducts();
                FragmentTransaction fmTrans = getActivity().getSupportFragmentManager().beginTransaction();
                fmTrans.replace(R.id.container, allProductsFragment).commit()
























                ;
            }
        });


        //Carousel
        imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slide_1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slide_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slide_3, ScaleTypes.FIT));

        imageSlider.setImageList((slideModels));


        loadZAllFromDatabaseFirebase();
        loadAllCategoryFromDatabase();

        return view;

    }


    //LOAD PRODUCT
    private void loadZAllFromDatabaseFirebase(){
        Set<String> productId = new HashSet<>();

                databaseProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        productsArrayList.clear();
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            String productsId = snapshot.getKey();
                            if(productsId != null){
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
                            }System.out.println("PRODUCT LIST:  " + productsArrayList);
                        }
                        productsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadAllCategoryFromDatabase(){
        databaseCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                categoriesArrayList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String categoryId = snapshot.getKey();
                    if(categoryId != null){
                        String categoryCode = snapshot.child("categoryCode").getValue(String.class);
                        String categoryName = snapshot.child("categoryName").getValue(String.class);

                        Categories categories = new Categories(categoryId, categoryCode, categoryName);

                        categoriesArrayList.add(categories);
                    }System.out.println("CATEGORY LIST:  " + categoriesArrayList);
                }
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

