package com.ravemaster.recipeapp.clickinterfaces;

import com.ravemaster.recipeapp.api.getfeed.models.Recipe;

public interface OnFeatureClicked {
    void moveToRecipeDetails(Recipe item);
}
