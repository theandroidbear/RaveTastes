package com.ravemaster.recipeapp.api.getsimilarrecipes.interfaces;


import com.ravemaster.recipeapp.api.getsimilarrecipes.models.SimilarRecipeApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetSimilarRecipes {
    @GET("recipes/list-similarities")
//    @Headers({
//            "x-rapidapi-key: 7e3d2f10bdmsh70e6fefa71835adp16c240jsnb41f1b9c1073",
//            "x-rapidapi-host: tasty.p.rapidapi.com"
//    })
    Call<SimilarRecipeApiResponse> getSimilarRecipes(
            @Header("x-rapidapi-key")String apiKey,
            @Header("x-rapidapi-host")String apiHost,
            @Query("recipe_id") int id
    );
}
