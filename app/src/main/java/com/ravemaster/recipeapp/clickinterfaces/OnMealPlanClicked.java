package com.ravemaster.recipeapp.clickinterfaces;

import com.ravemaster.recipeapp.api.getfeed.models.Item;

public interface OnMealPlanClicked {
    void moveToRecipeDetails(Item item);
}
