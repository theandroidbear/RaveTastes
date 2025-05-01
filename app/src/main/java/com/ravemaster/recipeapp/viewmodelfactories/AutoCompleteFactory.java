package com.ravemaster.recipeapp.viewmodelfactories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ravemaster.recipeapp.viewmodels.AutoCompleteViewModel;
import com.ravemaster.recipeapp.viewmodels.RecipesViewModel;

public class AutoCompleteFactory implements ViewModelProvider.Factory {
    private final Context context;
    public AutoCompleteFactory(Context context) {
        this.context = context.getApplicationContext();
    }
    @NonNull
    @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AutoCompleteViewModel.class)) {
            return (T) new AutoCompleteViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class"); }
}
