package com.ravemaster.recipeapp.api.getrecipelist.interfaces;

import com.ravemaster.recipeapp.api.getrecipelist.models.RecipeListApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetRecipeList {
    @GET("recipes/list")
//    @Headers({
//            "x-rapidapi-key: 7e3d2f10bdmsh70e6fefa71835adp16c240jsnb41f1b9c1073",
//            "x-rapidapi-host: tasty.p.rapidapi.com"
//    })
    Call<RecipeListApiResponse> getRecipeList(
            @Header("x-rapidapi-key")String apiKey,
            @Header("x-rapidapi-host")String apiHost,
            @Query("from") int from,
            @Query("size") int size ,
            @Query("q") String query
    );
}
