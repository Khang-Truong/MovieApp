package com.example.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DetailActivity;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.databinding.CardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {
    List<Movie> AdapterItemData;
    Context context;

    public RecyclerViewAdapter(List<Movie> adapterItemData, Context context) {
        AdapterItemData = adapterItemData;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardBinding binding = CardBinding.inflate(inflater, parent, false);
        ItemViewHolder holder = new ItemViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Movie movie = AdapterItemData.get(position);
        Picasso.get().load(movie.getPoster()).into(holder.holderBinding.movieImage); //change from string url into img
        holder.holderBinding.movieTitle.setText(movie.getTitle());
        holder.holderBinding.movieRating.setText((movie.getRating().toString()));
        holder.holderBinding.overview.setText(movie.getOverview());

        holder.holderBinding.movieTitle.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", movie.getTitle());
            bundle.putString("poster", movie.getPoster());
            bundle.putString("overview", movie.getOverview());
            bundle.putDouble("rating", movie.getRating());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        holder.holderBinding.movieImage.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", movie.getTitle());
            bundle.putString("poster", movie.getPoster());
            bundle.putString("overview", movie.getOverview());
            bundle.putDouble("rating", movie.getRating());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        holder.holderBinding.txtViewQuantity.setText(String.valueOf(movie.getQuantity()));
        holder.holderBinding.imgMinus.setOnClickListener((View v1)->{
            if(movie.getQuantity()>0){
                movie.setQuantity(movie.getQuantity()-1);
                holder.holderBinding.txtViewQuantity.setText(String.valueOf(movie.getQuantity()));
            }
        });
        holder.holderBinding.imgPlus.setOnClickListener((View v2)->{
            movie.setQuantity(movie.getQuantity()+1);
            holder.holderBinding.txtViewQuantity.setText(String.valueOf(movie.getQuantity()));
        });
    }

    @Override
    public int getItemCount() {
        return AdapterItemData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardBinding holderBinding;

        public ItemViewHolder(@NonNull CardBinding binding) {
            super(binding.getRoot());
            holderBinding = binding;
        }
    }
}
