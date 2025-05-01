package com.ravemaster.recipeapp.api.getfeed.models;

import java.util.ArrayList;

public class Instruction {
    public int start_time;
    public String appliance;
    public int end_time;
    public int temperature;
    public int id;
    public int position;
    public String display_text;
    public ArrayList<Hack> hacks;
}
