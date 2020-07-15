package com.example.walkmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.walkmenu.MainUi.HomeFragment;
import com.example.walkmenu.MainUi.OffersFragment;
import com.example.walkmenu.MainUi.OrdersFragment;
import com.example.walkmenu.MainUi.ProfileFragment;
import com.example.walkmenu.MainUi.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    HomeFragment homeFragment = new HomeFragment();
    OffersFragment offersFragment = new OffersFragment();
    SearchFragment searchFragment = new SearchFragment();
    OrdersFragment ordersFragment = new OrdersFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,homeFragment).commit();
                return true;
            case R.id.offers:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,offersFragment).commit();
                return true;
            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,searchFragment).commit();
                return true;
            case R.id.orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,ordersFragment).commit();
                return true;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,profileFragment).commit();
                return true;
        }
        return false;
    }
}