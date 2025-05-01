package com.ravemaster.recipeapp.db.models;

public class RecipePojo {
    private String name, description, ingredients, instructions;

    public RecipePojo(String name, String description, String ingredients, String instructions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
