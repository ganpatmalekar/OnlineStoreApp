package com.example.swapnil.onlinestoreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView customer;
    SessionManagement session;
    public static String BASE_URL = "http://swapnilmalekar-001-site1.htempurl.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar)this.findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MasterFragment masterFragment = new MasterFragment();
        fragmentTransaction.add(R.id.blank_fragment, masterFragment);
        fragmentTransaction.commit();

        session = new SessionManagement(this);

        HashMap<String, String> user = session.getUserDetails();
        String user_id = user.get(SessionManagement.KEY_NAME);

        drawerLayout = (DrawerLayout)this.findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);

        navigationView = (NavigationView)this.findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        customer = (TextView)header.findViewById(R.id.cust_name);
        customer.setText("Login: "+user_id);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home_menu:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        MasterFragment masterFragment = new MasterFragment();
                        fragmentTransaction.replace(R.id.blank_fragment, masterFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.cart_menu:
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        ViewCart viewCart = new ViewCart();
                        fragmentTransaction3.replace(R.id.blank_fragment, viewCart);
                        fragmentTransaction3.addToBackStack(null);
                        fragmentTransaction3.commit();
                        break;
                    case R.id.about_us:
                        FragmentManager fragmentManager1= getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        AboutUs aboutUs = new AboutUs();
                        fragmentTransaction1.replace(R.id.blank_fragment, aboutUs);
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        break;
                    case R.id.contact_us:
                        FragmentManager fragmentManager2= getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        ContactUs contactUs = new ContactUs();
                        fragmentTransaction2.replace(R.id.blank_fragment, contactUs);
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();
                        break;
                    case R.id.logout:
                        session.logoutUser();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        startLogin();
    }

    public void startLogin()
    {
        if (!session.isLoggedIn())
        {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
