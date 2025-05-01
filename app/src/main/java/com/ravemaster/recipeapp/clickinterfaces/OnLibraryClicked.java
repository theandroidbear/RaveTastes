package com.ravemaster.recipeapp.clickinterfaces;

import com.ravemaster.recipeapp.db.models.RecipePojo;

public interface OnLibraryClicked {
    void moveToLibraryActivity(RecipePojo recipePojo);
}
