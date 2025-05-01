package com.ravemaster.recipeapp.api.getfeed.feedinterfaces;


import com.ravemaster.recipeapp.api.getfeed.models.FeedsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface GetFeed {

    @GET("feeds/list")
//    @Headers({
//            "x-rapidapi-key: 7e3d2f10bdmsh70e6fefa71835adp16c240jsnb41f1b9c1073",
//            "x-rapidapi-host: tasty.p.rapidapi.com"
//    })
    Call<FeedsApiResponse> getFeedList(
            @Header("x-rapidapi-key")String apiKey,
            @Header("x-rapidapi-host")String apiHost,
            @Query("size") int size,
            @Query("timezone") String timezone ,
            @Query("vegetarian") boolean vegetarian,
            @Query("from") int from
    );

}
