package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.movieapp.databinding.ActivityDetailBinding;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        String bundlePoster = bundle.getString("poster");
        Log.d("Test",bundlePoster);
        Double bundleRating = bundle.getDouble("rating");
        Log.d("Test",bundleRating.toString());
        String bundleTitle = bundle.getString("title");
        Log.d("Test",bundleTitle);
        String bundleOverview = bundle.getString("overview");
        Log.d("Test",bundleOverview);

        Picasso.get().load(bundlePoster).into(binding.mPoster);
        binding.mRating.setText(Double.toString(bundleRating));
        binding.mTitle.setText(bundleTitle);
        binding.mDescription.setText(bundleOverview);
    }
}