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
import com.google.android.material.carousel.MaskableFrameLayout;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.api.getfeed.models.Item;
import com.ravemaster.recipeapp.clickinterfaces.OnMealPlanClicked;

import java.util.ArrayList;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealPlanViewHolder> {
    private Context context;
    private ArrayList<Item> urls;
    OnMealPlanClicked onMealPlanClicked;

    public MealPlanAdapter(Context context, ArrayList<Item> urls,OnMealPlanClicked onMealPlanClicked) {
        this.context = context;
        this.urls = urls;
        this.onMealPlanClicked = onMealPlanClicked;
    }

    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealPlanViewHolder(LayoutInflater.from(context).inflate(R.layout.meal_plan_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanViewHolder holder, int position) {

        Glide
                .with(context)
                .load(urls.get(position).thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        holder.name.setText(urls.get(position).name);
        holder.name.setSelected(true);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMealPlanClicked.moveToRecipeDetails(urls.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class MealPlanViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, time, ratings;
        MaskableFrameLayout cardView;
        public MealPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgMealPlan);
            name = itemView.findViewById(R.id.txtMealPlanName);
            cardView = itemView.findViewById(R.id.MealPlanCardView);
        }
    }
}
