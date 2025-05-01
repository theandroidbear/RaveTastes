package com.ravemaster.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.carousel.MaskableFrameLayout;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.api.getsimilarrecipes.models.Result;
import com.ravemaster.recipeapp.clickinterfaces.OnSimilarClicked;
import com.ravemaster.recipeapp.clickinterfaces.OnTrendingClicked;

import java.util.ArrayList;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder> {

    private Context context;
    private ArrayList<Result> arrayList;
    private OnSimilarClicked listener;

    public SimilarAdapter(Context context, ArrayList<Result> arrayList, OnSimilarClicked listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_recipe_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder holder, int position) {
        Glide
                .with(context)
                .load(arrayList.get(position).thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        String name = arrayList.get(holder.getAdapterPosition()).name;

        holder.name.setText(name);
        holder.name.setSelected(true);
        String time = String.valueOf(arrayList.get(holder.getAdapterPosition()).total_time_minutes);

        int positive = arrayList.get(position).user_ratings.count_positive;
        int negative = arrayList.get(position).user_ratings.count_negative;

        int total = positive + negative;

        double percent = ((double) positive/ total)*100;

        String rating = String.format("%.1f%%",percent);
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

    public static class SimilarViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, time, ratings;
        MaterialCardView cardView;
        public SimilarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgSimilar);
            name = itemView.findViewById(R.id.txtSimilarName);
            cardView = itemView.findViewById(R.id.similarCardView);
            time = itemView.findViewById(R.id.txtSimilarTime);
            ratings = itemView.findViewById(R.id.txtSimilarRating);
        }
    }
}
