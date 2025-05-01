package com.ravemaster.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.api.autocomplete.models.Result;
import com.ravemaster.recipeapp.clickinterfaces.AutoCompleteClick;

import java.util.ArrayList;

public class AutoCompleteAdapter extends RecyclerView.Adapter<AutoCompleteAdapter.AutoCompleteViewHolder> {
    private Context context;
    private ArrayList<Result> results;
    private AutoCompleteClick click;

    public AutoCompleteAdapter(Context context, ArrayList<Result> results,AutoCompleteClick click) {
        this.context = context;
        this.results = results;
        this.click = click;
    }

    @NonNull
    @Override
    public AutoCompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AutoCompleteViewHolder(LayoutInflater.from(context).inflate(R.layout.auto_complete_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AutoCompleteViewHolder holder, int position) {
        holder.textView.setText(results.get(position).display);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.search(results.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class AutoCompleteViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout layout;
        public AutoCompleteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtAutoComplete);
            layout = itemView.findViewById(R.id.autoCompleteLayout);
        }
    }
}
