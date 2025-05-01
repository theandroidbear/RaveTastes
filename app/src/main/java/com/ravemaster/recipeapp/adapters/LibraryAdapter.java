package com.ravemaster.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.clickinterfaces.OnLibraryClicked;
import com.ravemaster.recipeapp.db.models.RecipePojo;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private Context context;
    private ArrayList<RecipePojo> pojoArrayList;
    private OnLibraryClicked onLibraryClicked;

    public LibraryAdapter(Context context, ArrayList<RecipePojo> pojoArrayList, OnLibraryClicked onLibraryClicked) {
        this.context = context;
        this.pojoArrayList = pojoArrayList;
        this.onLibraryClicked = onLibraryClicked;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LibraryViewHolder(LayoutInflater.from(context).inflate(R.layout.library_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        holder.name.setText(pojoArrayList.get(position).getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLibraryClicked.moveToLibraryActivity(pojoArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoArrayList.size();
    }

    public static class LibraryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout linearLayout;
        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.libraryName);
            linearLayout = itemView.findViewById(R.id.selectLibrary);
        }
    }

}


