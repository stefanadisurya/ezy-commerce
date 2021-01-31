package com.example.ezycommerce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.R;
import com.example.ezycommerce.adapter.BooksAdapter;
import com.example.ezycommerce.json.APIClient;
import com.example.ezycommerce.json.Book;
import com.example.ezycommerce.json.BooksResponse;
import com.example.ezycommerce.json.BooksService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentScifi extends Fragment {

    View v;
    RecyclerView rvScifi;
    BooksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.scifi_fragment, container, false);
        rvScifi = (RecyclerView) v.findViewById(R.id.rvScifi);

        adapter = new BooksAdapter(getContext());
        rvScifi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvScifi.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = APIClient.getRetrofit();
        BooksService service = retrofit.create(BooksService.class);
        Call<BooksResponse> call = service.getBooks("2201753706", "stefanadisurya");

        call.enqueue(new Callback<BooksResponse>() {
            List<Book> listBooks = new ArrayList<>();
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                for(int i = 0; i < response.body().products.size(); i++) {
                    if(response.body().products.get(i).category.equals("scifi")) {
                        listBooks.add(response.body().products.get(i));
                        adapter.setListBooks(listBooks);
                    }
                }
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
