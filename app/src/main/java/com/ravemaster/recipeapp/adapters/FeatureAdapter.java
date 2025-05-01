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
import com.ravemaster.recipeapp.api.getfeed.models.Recipe;
import com.ravemaster.recipeapp.clickinterfaces.OnFeatureClicked;

import java.util.ArrayList;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewholder> {

    private Context context;
    private ArrayList<Recipe> arrayList;
    private OnFeatureClicked listener;

    public FeatureAdapter(Context context, ArrayList<Recipe> arrayList, OnFeatureClicked listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeatureViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeatureViewholder(LayoutInflater.from(context).inflate(R.layout.feature_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewholder holder, int position) {
        Glide
                .with(context)
                .load(arrayList.get(position).thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        String name = arrayList.get(holder.getAdapterPosition()).name;
        String time = String.valueOf(arrayList.get(holder.getAdapterPosition()).cook_time_minutes);

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

    public static class FeatureViewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, time, ratings;
        MaterialCardView cardView;
        public FeatureViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgFeatureItem);
            name = itemView.findViewById(R.id.txtFeatureItemName);
            time = itemView.findViewById(R.id.txtFeatureItemTime);
            ratings = itemView.findViewById(R.id.txtFeatureItemRating);
            cardView = itemView.findViewById(R.id.featureItemCardView);
        }
    }
}
