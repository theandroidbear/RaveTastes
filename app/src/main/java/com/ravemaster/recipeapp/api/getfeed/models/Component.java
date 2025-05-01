package com.ravemaster.recipeapp.api.getfeed.models;

import java.util.ArrayList;

public class Component {
    public String raw_text;
    public String extra_comment;
    public Ingredient ingredient;
    public int id;
    public int position;
    public ArrayList<Measurement> measurements;
    public ArrayList<Hack> hacks;
    public LinkedRecipe linked_recipe;
}
