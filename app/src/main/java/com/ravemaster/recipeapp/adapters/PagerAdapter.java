package com.ravemaster.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.api.getfeed.models.Item;
import com.ravemaster.recipeapp.clickinterfaces.OnFeedTwoClicked;

import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.PagerViewHolder> {
    private List<Item> pagerModels;
    private Context context;
    private OnFeedTwoClicked onFeedTwoClicked;

    public PagerAdapter(Context context, OnFeedTwoClicked onFeedTwoClicked) {
        this.context = context;
        this.onFeedTwoClicked = onFeedTwoClicked;
    }

    public void setPagerModels(List<Item> pagerModels) {
        this.pagerModels = pagerModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.viewpager_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder holder, int position) {
        Item item = pagerModels.get(position);
        holder.name.setText(item.name);
        int positive = item.user_ratings.count_positive;
        int negative = item.user_ratings.count_negative;

        int total = positive + negative;

        double percent = ((double) positive/ total)*100;

        String rating = String.format("%.1f%%",percent);
        Glide.with(context)
                .load(item.thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.pagerImage);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedTwoClicked.moveToRecipeDetails(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pagerModels.size();
    }

    static class PagerViewHolder extends RecyclerView.ViewHolder {
        ImageView pagerImage;
        TextView name;
        RelativeLayout layout;
        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);
            pagerImage = itemView.findViewById(R.id.viewPagerImage);
            name = itemView.findViewById(R.id.pagerName);
            layout = itemView.findViewById(R.id.feedTwoLayout);
        }
    }
}
