package com.example.swapnil.onlinestoreapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MasterFragment extends Fragment implements View.OnClickListener{

    LinearLayout fruit, bakery, beverage, snacks, eggs, kitchen, clean, oil, beauty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master, container, false);

        fruit = (LinearLayout)view.findViewById(R.id.fruit_layout);
        bakery = (LinearLayout)view.findViewById(R.id.bakery_layout);
        beverage = (LinearLayout)view.findViewById(R.id.beverage_layout);
        snacks = (LinearLayout)view.findViewById(R.id.snacks_layout);
        eggs = (LinearLayout)view.findViewById(R.id.eggs_layout);
        kitchen = (LinearLayout)view.findViewById(R.id.kitchen_layout);
        clean = (LinearLayout)view.findViewById(R.id.clean_layout);
        oil = (LinearLayout)view.findViewById(R.id.oil_layout);
        beauty = (LinearLayout)view.findViewById(R.id.beauty_layout);

        fruit.setOnClickListener(this);
        bakery.setOnClickListener(this);
        beverage.setOnClickListener(this);
        snacks.setOnClickListener(this);
        eggs.setOnClickListener(this);
        kitchen.setOnClickListener(this);
        clean.setOnClickListener(this);
        oil.setOnClickListener(this);
        beauty.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentManager manager;
        FragmentTransaction transaction;

        if (view.getId() == R.id.fruit_layout)
        {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 1;
            transaction.commit();
            transaction.addToBackStack(null);
        }
        if (view.getId() == R.id.bakery_layout)
        {
            FragmentManager manager1 = getFragmentManager();
            FragmentTransaction transaction1 = manager1.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction1.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 2;
            transaction1.commit();
            transaction1.addToBackStack(null);
        }
        if (view.getId() == R.id.beverage_layout)
        {
            FragmentManager manager2 = getFragmentManager();
            FragmentTransaction transaction2 = manager2.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction2.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 3;
            transaction2.commit();
            transaction2.addToBackStack(null);
        }
        if (view.getId() == R.id.snacks_layout)
        {
            FragmentManager manager3 = getFragmentManager();
            FragmentTransaction transaction3 = manager3.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction3.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 4;
            transaction3.commit();
            transaction3.addToBackStack(null);
        }
        if (view.getId() == R.id.eggs_layout)
        {
            FragmentManager manager4 = getFragmentManager();
            FragmentTransaction transaction4 = manager4.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction4.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 5;
            transaction4.commit();
            transaction4.addToBackStack(null);
        }
        if (view.getId() == R.id.kitchen_layout)
        {
            FragmentManager manager5 = getFragmentManager();
            FragmentTransaction transaction5 = manager5.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction5.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 6;
            transaction5.commit();
            transaction5.addToBackStack(null);
        }
        if (view.getId() == R.id.clean_layout)
        {
            FragmentManager manager6 = getFragmentManager();
            FragmentTransaction transaction6 = manager6.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction6.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 7;
            transaction6.commit();
            transaction6.addToBackStack(null);
        }
        if (view.getId() == R.id.oil_layout)
        {
            FragmentManager manager7 = getFragmentManager();
            FragmentTransaction transaction7 = manager7.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction7.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 8;
            transaction7.commit();
            transaction7.addToBackStack(null);
        }
        if (view.getId() == R.id.beauty_layout)
        {
            FragmentManager manager8 = getFragmentManager();
            FragmentTransaction transaction8 = manager8.beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction8.replace(R.id.blank_fragment, fragment);
            fragment.fragment_id = 9;
            transaction8.commit();
            transaction8.addToBackStack(null);
        }
    }
}
