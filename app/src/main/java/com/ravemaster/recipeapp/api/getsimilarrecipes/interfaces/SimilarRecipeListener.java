package com.ravemaster.recipeapp.api.getsimilarrecipes.interfaces;


import com.ravemaster.recipeapp.api.getsimilarrecipes.models.SimilarRecipeApiResponse;

public interface SimilarRecipeListener {
    void onResponse(SimilarRecipeApiResponse response, String message);
    void onFailure(String message);
    void onLoading(boolean isLoading);
}
