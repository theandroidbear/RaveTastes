package com.ravemaster.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.api.getfeed.models.Item;
import com.ravemaster.recipeapp.clickinterfaces.OnTrendingClicked;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    private Context context;
    private ArrayList<Item> arrayList;
    private OnTrendingClicked listener;

    public TrendingAdapter(Context context, ArrayList<Item> arrayList, OnTrendingClicked listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrendingViewHolder(LayoutInflater.from(context).inflate(R.layout.trending_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {
        Glide
                .with(context)
                .load(arrayList.get(position).thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        String name = arrayList.get(holder.getAdapterPosition()).name;
        String time = String.valueOf(arrayList.get(holder.getAdapterPosition()).total_time_minutes);

        int positive = arrayList.get(holder.getAdapterPosition()).user_ratings.count_positive;
        int negative = arrayList.get(holder.getAdapterPosition()).user_ratings.count_negative;

        int total = positive + negative;

        double percent = ((double) positive/ total)*100;

        String rating = String.format("%.1f%%",percent);

        holder.name.setText(name);
        holder.name.setSelected(true);
        if (time.equals("0")){
            holder.time.setText("60 min");
        } else {
            holder.time.setText(time+" min");
        }
        holder.ratings.setText(rating);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.moveToRecipeDetails(arrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class TrendingViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, time, ratings;
        MaterialCardView cardView;
        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgTrending);
            name = itemView.findViewById(R.id.txtTrendingName);
            time = itemView.findViewById(R.id.txtTrendingTime);
            ratings = itemView.findViewById(R.id.txtTrendingRating);
            cardView = itemView.findViewById(R.id.trendingCardView);
        }
    }
}
