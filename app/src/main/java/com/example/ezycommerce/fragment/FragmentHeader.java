package com.example.ezycommerce.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ezycommerce.CartActivity;
import com.example.ezycommerce.R;
import com.example.ezycommerce.json.APIClient;
import com.example.ezycommerce.json.BooksResponse;
import com.example.ezycommerce.json.BooksService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentHeader extends Fragment {

    View v;
    TextView tvMyName;
    ImageButton btnCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.header_fragment, container, false);

        tvMyName = v.findViewById(R.id.tvMyName);
        btnCart = v.findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CartActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = APIClient.getRetrofit();
        BooksService service = retrofit.create(BooksService.class);
        Call<BooksResponse> call = service.getBooks("2201753706", "stefanadisurya");

        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                tvMyName.setText(response.body().nama + "\n" + response.body().nim);
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
