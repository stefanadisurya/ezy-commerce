package com.example.ezycommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ezycommerce.fragment.FragmentDetail;
import com.example.ezycommerce.fragment.FragmentHeader;
import com.example.ezycommerce.json.Book;

public class DetailActivity extends AppCompatActivity {

    String img, name, author;
    Integer id;
    Double price;
    CartActivity cart = new CartActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Fragment fragmentHeader;
        fragmentHeader = new FragmentHeader();
        loadHeader(fragmentHeader);

        Fragment fragment;
        fragment = new FragmentDetail();
        loadFragment(fragment);
    }

    private void loadHeader(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frHeader2, fragment);
        transaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frDetail, fragment);
        transaction.commit();
    }

    public void buy(View v) {
        Intent intent = new Intent(this, CartActivity.class);
        if(getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", 1);
            img = getIntent().getStringExtra("img");
            name = getIntent().getStringExtra("name");
            author = getIntent().getStringExtra("author");
            price = getIntent().getDoubleExtra("price", 0);
            Book book = new Book();
            book.id = id;
            book.img = img;
            book.name = name;
            book.author = author;
            book.price = price;
            book.inCart = true;
            cart.listBooks.add(book);
        }
        startActivity(intent);
    }
}