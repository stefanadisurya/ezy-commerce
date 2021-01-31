package com.example.ezycommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ezycommerce.DetailActivity;
import com.example.ezycommerce.R;
import com.example.ezycommerce.json.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    Context ctx;
    List<Book> listBooks;

    public BooksAdapter(Context ctx) {
        this.ctx = ctx;
        this.listBooks = new ArrayList<>();
    }

    public void setListBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = listBooks.get(position);

        Glide.with(ctx).load(book.img).into(holder.ivThumbnail);
        holder.tvName.setText(book.name);
        holder.tvAuthor.setText(book.author);
        holder.tvPrice.setText("$" + Double.toString(book.price));

        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, DetailActivity.class);
            intent.putExtra("id", book.id);
            intent.putExtra("img", book.img);
            intent.putExtra("name", book.name);
            intent.putExtra("author", book.author);
            intent.putExtra("price", book.price);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvName;
        TextView tvPrice;
        TextView tvAuthor;

        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            linearLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}