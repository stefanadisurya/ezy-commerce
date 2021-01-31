package com.example.ezycommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ezycommerce.CartActivity;
import com.example.ezycommerce.MainActivity;
import com.example.ezycommerce.R;
import com.example.ezycommerce.json.Book;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context ctx;
    List<Book> listBooks;
    TextView subtotal, shipping, tax, total;
    DecimalFormat formatter = new DecimalFormat("0.00");

    public CartAdapter(Context ctx, List<Book> listBooks, TextView subtotal, TextView shipping, TextView tax, TextView total) {
        this.ctx = ctx;
        this.listBooks = new ArrayList<>();
        this.listBooks = listBooks;
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.tax = tax;
        this.total = total;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_cart, parent, false);
        return new CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(ctx).load(listBooks.get(position).img).into(holder.ivCartThumbnail);
        holder.tvCartName.setText(listBooks.get(position).name);
        holder.tvCartAuthor.setText(listBooks.get(position).author);
        holder.tvCartPrice.setText("$" + listBooks.get(position).price);

        holder.btnDecrease.setOnClickListener(v -> {
            if(listBooks.get(position).quantity != 0) {
                listBooks.get(position).quantity -= 1;
                holder.dTax -= 30.0;
            } else if(listBooks.get(position).quantity == 0) {
                holder.btnDecrease.setEnabled(false);
            }

            holder.dSubtotal = 0.0;
            holder.dTax = 0.0;
            holder.dTotal = 0.0;

            if(listBooks.size() > 1) {
                for(int i = 0; i < listBooks.size(); i++) {
                    holder.dSubtotal += listBooks.get(i).quantity * listBooks.get(i).price;
                    holder.dTax += 30.0;
                    holder.dTotal = (holder.dSubtotal + holder.dTax);
                }
            } else if(listBooks.size() == 1) {
                for(int i = 0; i < listBooks.size(); i++) {
                    holder.dSubtotal = listBooks.get(i).quantity * listBooks.get(i).price;
                    holder.dTax = 30.0;
                    holder.dTotal = (holder.dSubtotal + holder.dTax);
                }
            }
            subtotal.setText("$" + formatter.format(holder.dSubtotal));
            shipping.setText("FREE");
            tax.setText("$" + formatter.format(holder.dTax));
            total.setText("$" + formatter.format(holder.dTotal));

            holder.tvQuantity.setText(String.valueOf(listBooks.get(position).quantity));
        });


        holder.btnIncrease.setOnClickListener(v -> {
            holder.btnDecrease.setEnabled(true);
            listBooks.get(position).quantity += 1;

            holder.dSubtotal = 0.0;
            holder.dTax = 0.0;
            holder.dTotal = 0.0;

            if(listBooks.size() > 1) {
                for(int i = 0; i < listBooks.size(); i++) {
                    holder.dSubtotal += listBooks.get(i).quantity * listBooks.get(i).price;
                    holder.dTax += 30.0;
                    holder.dTotal = (holder.dSubtotal + holder.dTax);
                }
            } else if(listBooks.size() == 1) {
                for(int i = 0; i < listBooks.size(); i++) {
                    holder.dSubtotal = listBooks.get(i).quantity * listBooks.get(i).price;
                    holder.dTax = 30.0;
                    holder.dTotal = (holder.dSubtotal + holder.dTax);
                }
            }
            subtotal.setText("$" + formatter.format(holder.dSubtotal));
            shipping.setText("FREE");
            tax.setText("$" + formatter.format(holder.dTax));
            total.setText("$" + formatter.format(holder.dTotal));

            holder.tvQuantity.setText(String.valueOf(listBooks.get(position).quantity));
        });

        holder.btnRemove.setOnClickListener(v -> {
            listBooks.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listBooks.size());

            Intent intent = new Intent(ctx, CartActivity.class);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCartThumbnail;
        TextView tvCartName, tvCartPrice, tvCartAuthor, tvQuantity;
        ImageButton btnDecrease, btnIncrease;
        Button btnRemove;

        LinearLayout mainLayout2;

        Double dSubtotal = 0.0;
        Double dTax = 0.0;
        Double dTotal = 0.0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCartThumbnail = itemView.findViewById(R.id.ivCartThumbnail);
            tvCartName = itemView.findViewById(R.id.tvCartName);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCartAuthor = itemView.findViewById(R.id.tvCartAuthor);

            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);

            mainLayout2 = itemView.findViewById(R.id.mainLayout2);
        }
    }
}
