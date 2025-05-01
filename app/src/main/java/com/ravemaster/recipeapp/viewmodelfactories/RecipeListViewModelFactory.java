package com.ravemaster.recipeapp.viewmodelfactories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ravemaster.recipeapp.viewmodels.FeedViewModel;
import com.ravemaster.recipeapp.viewmodels.RecipesViewModel;

public class RecipeListViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public RecipeListViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }
    @NonNull
    @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class"); }
}
