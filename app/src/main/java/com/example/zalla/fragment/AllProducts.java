package com.example.zalla.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zalla.R;
import com.example.zalla.controller.ProductsAdapter;
import com.example.zalla.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllProducts extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseProductRef, databaseCategoryRef;
    private ProductsAdapter productsAdapter;
    RecyclerView recyclerView;
    ArrayList<Products> productsArrayList;

    public AllProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static AllProducts newInstance(String param1, String param2) {
        AllProducts fragment = new AllProducts();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //DISPLAYING PRODUCT
        recyclerView = view.findViewById(R.id.recyclerViewListProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));


        //DATABASE CONFIGURATION
        databaseProductRef = FirebaseDatabase.getInstance().getReference("products");
        databaseCategoryRef = FirebaseDatabase.getInstance().getReference("categories");

        productsArrayList = new ArrayList<>();

        //Recycler View for product place
        productsAdapter = new ProductsAdapter(getContext(), productsArrayList);
        recyclerView.setAdapter(productsAdapter);

        loadZAllFromDatabaseFirebase();

        // Inflate the layout for this fragment
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
}