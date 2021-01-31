package com.example.ezycommerce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ezycommerce.R;
import com.example.ezycommerce.json.APIClient;
import com.example.ezycommerce.json.BooksResponse;
import com.example.ezycommerce.json.BooksService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentDetail extends Fragment {

    View v;
    ImageView ivBookImage;
    TextView tvBookName, tvBookAuthor, tvBookPrice, tvBookDescription;
    Integer id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.detail_fragment, container, false);

        ivBookImage = v.findViewById(R.id.ivBookImage);
        tvBookName = v.findViewById(R.id.tvBookName);
        tvBookAuthor = v.findViewById(R.id.tvBookAuthor);
        tvBookPrice = v.findViewById(R.id.tvBookPrice);
        tvBookDescription = v.findViewById(R.id.tvBookDescription);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = APIClient.getRetrofit();
        BooksService service = retrofit.create(BooksService.class);
        getData();
        Call<BooksResponse> call = service.getBookDetails(id, "2201753706", "stefanadisurya");

        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                if(response.body().statusCode == 200) {
                    for (int i=0; i < response.body().products.size(); i++) {
                        if(response.body().products.get(i).id == id) {
                            Glide.with(getContext()).load(response.body().products.get(i).img).into(ivBookImage);
                            tvBookName.setText(response.body().products.get(i).name);
                            tvBookAuthor.setText(response.body().products.get(i).author);
                            tvBookPrice.setText("$" + response.body().products.get(i).price);
                            tvBookDescription.setText(response.body().products.get(i).description);
                        }
                    }
                } else if(response.body().statusCode == 500) {

                }
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getData() {
        if(getActivity().getIntent().hasExtra("id")) {
            id = getActivity().getIntent().getIntExtra("id", 1);
        } else {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
