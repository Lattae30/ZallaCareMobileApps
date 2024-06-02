package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.zalla.fragment.CartFragment;
import com.example.zalla.fragment.HistoryFragment;
import com.example.zalla.fragment.HomeFragment;
import com.example.zalla.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();
    UserFragment userFragment = new UserFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Replace the container with home fragment when first opening the app
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        //Item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
//                    case R.id.history:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, historyFragment).commit();
//                        return true;
//                    case R.id.cart:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cartFragment).commit();
//                        return true;
                    case R.id.user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,userFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}