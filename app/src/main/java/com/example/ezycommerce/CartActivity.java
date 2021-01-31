package com.example.ezycommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ezycommerce.adapter.CartAdapter;
import com.example.ezycommerce.fragment.FragmentHeader;
import com.example.ezycommerce.json.Book;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    TextView tvQuantity, subtotal, shipping, tax, total;
    Double quantity;
    Double dSubtotal;
    Double dTax;
    Double dTotal;

    RecyclerView rvCart;
    CartAdapter adapter;
    Button btnNext, btnCancel;

    static List<Book> listBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setContentView(R.layout.activity_cart);

        btnNext = findViewById(R.id.btnNext);
        btnCancel = findViewById(R.id.btnCancel);

        Fragment fragmentHeader;
        fragmentHeader = new FragmentHeader();
        loadHeader(fragmentHeader);

        tvQuantity = findViewById(R.id.tvQuantity);
        subtotal = findViewById(R.id.subtotal);
        shipping = findViewById(R.id.shipping);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);

        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        quantity = 1.0;
        dSubtotal = 0.0;
        dTax = 0.0;
        dTotal = 0.0;

        adapter = new CartAdapter(this, listBooks, subtotal, shipping, tax, total);
        rvCart.setAdapter(adapter);

        if(listBooks.isEmpty()) {
            btnNext.setEnabled(false);
        }

        rvCart.setItemAnimator(new DefaultItemAnimator());

        countBill();
    }

    private void loadHeader(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frHeader3, fragment);
        transaction.commit();
    }

    private void countBill() {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if(listBooks.size() > 1) {
            for(int i = 0; i < listBooks.size(); i++) {
                dSubtotal += quantity * listBooks.get(i).price;
                dTax += 30.0;
                dTotal = (dSubtotal + dTax);
            }
        } else {
            for(int i = 0; i < listBooks.size(); i++) {
                dSubtotal = quantity * listBooks.get(i).price;
                dTax = 30.0;
                dTotal = (dSubtotal + dTax);
            }
        }
        subtotal.setText("$" + formatter.format(dSubtotal));
        shipping.setText("FREE");
        tax.setText("$" + formatter.format(dTax));
        total.setText("$" + formatter.format(dTotal));
    }

    public void backToDetail(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toHome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void paymentNotification(View view) {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "Payment Dialog");

        btnNext.setText("Main Menu");
        if(btnNext.getText() == "Main Menu") {
            btnNext.setOnClickListener(v -> toHome(v));
            listBooks.clear();
            btnCancel.setEnabled(true);
        }
    }
}