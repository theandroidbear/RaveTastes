package com.ravemaster.recipeapp.clickinterfaces;

import com.ravemaster.recipeapp.api.getsimilarrecipes.models.Result;

public interface OnSimilarClicked {
    void moveToRecipeDetails(Result result);
}
