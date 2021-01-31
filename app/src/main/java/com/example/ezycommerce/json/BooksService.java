package com.example.ezycommerce.json;

import android.app.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BooksService {
    @GET("staging/book")
    Call<BooksResponse> getBooks(
            @Query(value="nim") String nim,
            @Query(value="nama") String nama
    );

    @GET("staging/book/{bookId}")
    Call<BooksResponse> getBookDetails(
            @Path("bookId") Integer bookId,
            @Query(value="nim") String nim,
            @Query(value="nama") String nama
    );
}
