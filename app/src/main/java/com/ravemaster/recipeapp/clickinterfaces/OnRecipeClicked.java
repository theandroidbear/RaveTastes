package com.ravemaster.recipeapp.clickinterfaces;

import com.ravemaster.recipeapp.api.getrecipelist.models.Result;

public interface OnRecipeClicked {
    void moveToRecipeActivity(Result result);
}
