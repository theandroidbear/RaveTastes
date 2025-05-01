package com.ravemaster.recipeapp.api.getrecipelist.interfaces;

import com.ravemaster.recipeapp.api.getrecipelist.models.RecipeListApiResponse;

public interface RecipeListListener {
    void onResponse(RecipeListApiResponse response, String message);
    void onFailure(String message);
    void onLoading(boolean isLoading);
}
