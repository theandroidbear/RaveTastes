package com.ravemaster.recipeapp.viewmodelfactories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ravemaster.recipeapp.viewmodels.FeedViewModel;

public class FeedViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public FeedViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }
    @NonNull
    @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FeedViewModel.class)) {
            return (T) new FeedViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class"); }
}
