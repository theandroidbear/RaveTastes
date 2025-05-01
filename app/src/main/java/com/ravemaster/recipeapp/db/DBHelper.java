package com.ravemaster.recipeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "DatabaseOne.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table RecipeTable(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,description TEXT,ingredients TEXT,instructions TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists RecipeTable");

    }

    public boolean insertData( String name,String description,String ingredients,String instructions){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("description",description);
        contentValues.put("ingredients",ingredients);
        contentValues.put("instructions",instructions);
        long result = database.insert("RecipeTable",null,contentValues);
        return result != -1;
    }

    public Cursor getRecipes(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("Select * from RecipeTable order by id DESC",null);
    }

}
