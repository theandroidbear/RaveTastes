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
import com.ravemaster.recipeapp.api.getrecipelist.models.Result;
import com.ravemaster.recipeapp.clickinterfaces.OnRecipeClicked;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipesViewHolder> {

    private Context context;
    private ArrayList<Result> results;
    private OnRecipeClicked listener;

    public RecipeListAdapter( Context context, ArrayList<Result> results, OnRecipeClicked listener){
        this.context = context;
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipesViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {

        Result result = results.get(position);
        Glide
                .with(context)
                .load(result.thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.name.setText(result.name);
        holder.name.setSelected(true);
        if (result.description == null || result.description.isEmpty()){
            holder.description.setText("(No description available)");
        } else {
            holder.description.setText(result.description);
        }
        holder.description.setSelected(true);
        holder.servings.setText(String.valueOf(result.num_servings)+" people");

        int positive = result.user_ratings.count_positive;
        int negative = result.user_ratings.count_negative;

        int total = positive + negative;

        double percent = ((double) positive/ total)*100;

        String rating = String.format("%.1f%%",percent);
        holder.ratings.setText(rating);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.moveToRecipeActivity(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, description, ratings, servings,keywords,dummy;
        MaterialCardView cardView;
        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgRecipe);
            name = itemView.findViewById(R.id.txtRecipeTitle);
            ratings = itemView.findViewById(R.id.txtRecipeListRating);
            cardView = itemView.findViewById(R.id.recipeCardView);
            servings = itemView.findViewById(R.id.txtRecipeListServing);
            description = itemView.findViewById(R.id.txtRecipeDescription);
        }
    }
}
