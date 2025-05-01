package com.ravemaster.recipeapp.api.getrecipedetails.interfaces;

import com.ravemaster.recipeapp.api.getrecipedetails.models.RecipeDetailApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface GetRecipeDetails {
    @GET("recipes/get-more-info")
//    @Headers({
//            "x-rapidapi-key: 7e3d2f10bdmsh70e6fefa71835adp16c240jsnb41f1b9c1073",
//            "x-rapidapi-host: tasty.p.rapidapi.com"
//    })
    Call<RecipeDetailApiResponse> getDetails(
            @Header("x-rapidapi-key")String apiKey,
            @Header("x-rapidapi-host")String apiHost,
            @Query("id") int id
    );
}
