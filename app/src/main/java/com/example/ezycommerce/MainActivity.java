package com.example.ezycommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ezycommerce.adapter.ViewPagerAdapter;
import com.example.ezycommerce.fragment.FragmentBooks;
import com.example.ezycommerce.fragment.FragmentBusiness;
import com.example.ezycommerce.fragment.FragmentCookbooks;
import com.example.ezycommerce.fragment.FragmentHeader;
import com.example.ezycommerce.fragment.FragmentMystery;
import com.example.ezycommerce.fragment.FragmentScifi;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter vAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment;
        fragment = new FragmentHeader();
        loadHeader(fragment);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        vAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        vAdapter.addFragment(new FragmentBooks(), "All");
        vAdapter.addFragment(new FragmentBusiness(), "Business");
        vAdapter.addFragment(new FragmentCookbooks(), "Cookbooks");
        vAdapter.addFragment(new FragmentMystery(), "Mystery");
        vAdapter.addFragment(new FragmentScifi(), "Scifi");

        viewPager.setAdapter(vAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadHeader(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frHeader, fragment);
        transaction.commit();
    }
}