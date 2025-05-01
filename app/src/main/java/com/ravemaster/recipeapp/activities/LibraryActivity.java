package com.ravemaster.recipeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ravemaster.recipeapp.R;

public class LibraryActivity extends AppCompatActivity {

    TextView name, description, ingredients, instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library);

        initViews();

        Intent intent = getIntent();

        String s1 = intent.getStringExtra("name");
        String s2 = intent.getStringExtra("description");
        String s3 = intent.getStringExtra("ingredients");
        String s4 = intent.getStringExtra("instructions");

        name.setText(s1);
        if (s2 == null){
            description.setText("Decription unavailable");
        } else {
            description.setText(s2);
        }
        ingredients.setText(s3);
        instructions.setText(s4);

    }

    private void initViews() {
        name = findViewById(R.id.txtLibName);
        description = findViewById(R.id.txtlibDescription);
        ingredients = findViewById(R.id.txtLibIngredients);
        instructions = findViewById(R.id.txtLibInstructions);
    }
}