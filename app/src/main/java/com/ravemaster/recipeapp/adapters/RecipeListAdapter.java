package com.ravemaster.recipeapp.adapters;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
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
        TextView name, time, ratings, servings,keywords,dummy;
        RelativeLayout cardView;
        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgRecipe);
//            name = itemView.findViewById(R.id.txtRecipeName);
//            time = itemView.findViewById(R.id.txtRecipeTime);
//            ratings = itemView.findViewById(R.id.txtRecipeRating);
            cardView = itemView.findViewById(R.id.recipeCardView);
//            servings = itemView.findViewById(R.id.txtRecipeServing);
//            keywords = itemView.findViewById(R.id.txtRecipeKeywords);
//            dummy = itemView.findViewById(R.id.txtKeywordsDummy);
        }
    }
}
