package com.ravemaster.recipeapp.api.getrecipelist.models;

import java.util.ArrayList;

public class Component {
    public String extra_comment;
    public int id;
    public Ingredient ingredient;
    public ArrayList<Measurement> measurements;
    public int position;
    public String raw_text;
    public ArrayList<Hack> hacks;
}
