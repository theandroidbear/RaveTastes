package com.ravemaster.recipeapp.api.getrecipedetails.interfaces;

import com.ravemaster.recipeapp.api.getrecipedetails.models.RecipeDetailApiResponse;

public interface RecipeDetailsListener {
    void onResponse(RecipeDetailApiResponse response, String message);
    void onFailure(String message);
    void onLoading(boolean isLoading);
}
