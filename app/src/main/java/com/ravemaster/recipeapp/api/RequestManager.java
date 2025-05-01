package com.ravemaster.recipeapp.api;

import android.content.Context;

import com.ravemaster.recipeapp.api.autocomplete.interfaces.AutoCompleteListener;
import com.ravemaster.recipeapp.api.autocomplete.interfaces.GetAutoComplete;
import com.ravemaster.recipeapp.api.autocomplete.models.AutoCompleteApiResponse;
import com.ravemaster.recipeapp.api.getfeed.feedinterfaces.FeedsListListener;
import com.ravemaster.recipeapp.api.getfeed.feedinterfaces.GetFeed;
import com.ravemaster.recipeapp.api.getfeed.models.FeedsApiResponse;
import com.ravemaster.recipeapp.api.getrecipedetails.interfaces.GetRecipeDetails;
import com.ravemaster.recipeapp.api.getrecipedetails.interfaces.RecipeDetailsListener;
import com.ravemaster.recipeapp.api.getrecipedetails.models.RecipeDetailApiResponse;
import com.ravemaster.recipeapp.api.getrecipelist.interfaces.GetRecipeList;
import com.ravemaster.recipeapp.api.getrecipelist.interfaces.RecipeListListener;
import com.ravemaster.recipeapp.api.getrecipelist.models.RecipeListApiResponse;
import com.ravemaster.recipeapp.api.getsimilarrecipes.interfaces.GetSimilarRecipes;
import com.ravemaster.recipeapp.api.getsimilarrecipes.interfaces.SimilarRecipeListener;
import com.ravemaster.recipeapp.api.getsimilarrecipes.models.SimilarRecipeApiResponse;
import com.ravemaster.recipeapp.apiKey.ApiKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {
    Context context;

    public RequestManager(Context context) {
        this.context = context;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://tasty.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getFeedList(FeedsListListener listener, boolean vegetarian){

        listener.onLoading(true);

        GetFeed getFeed = retrofit.create(GetFeed.class);
        Call<FeedsApiResponse> call = getFeed.getFeedList(ApiKey.getApiKey(),"tasty.p.rapidapi.com",5,"+0300",vegetarian,0);
        call.enqueue(new Callback<FeedsApiResponse>() {
            @Override
            public void onResponse(Call<FeedsApiResponse> call, Response<FeedsApiResponse> response) {
                listener.onLoading(false);
                if (!response.isSuccessful()){
                    listener.onError(String.valueOf(response.code())+" from onResponse");
                    return;
                }
                listener.onResponse(response.body(), response.message());

            }

            @Override
            public void onFailure(Call<FeedsApiResponse> call, Throwable throwable) {
                listener.onLoading(false);
                listener.onError(throwable.getMessage()+ " from onFailure");

            }
        });
    }
    public void getRecipeList(RecipeListListener listener, int from, int size, String query){
        listener.onLoading(true);
        GetRecipeList getRecipeList = retrofit.create(GetRecipeList.class);
        Call<RecipeListApiResponse> call = getRecipeList.getRecipeList(ApiKey.getApiKey(),"tasty.p.rapidapi.com",from,size,query);
        call.enqueue(new Callback<RecipeListApiResponse>() {
            @Override
            public void onResponse(Call<RecipeListApiResponse> call, Response<RecipeListApiResponse> response) {
                listener.onLoading(false);
                if (!response.isSuccessful()){
                    listener.onFailure(String.valueOf(response.code())+" from onResponse");
                    return;
                }
                listener.onResponse(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeListApiResponse> call, Throwable throwable) {
                listener.onLoading(false);
                listener.onFailure(throwable.getMessage()+ " from onFailure");
            }
        });
    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        listener.onLoading(true);
        GetRecipeDetails getDetails = retrofit.create(GetRecipeDetails.class);
        Call<RecipeDetailApiResponse> call = getDetails.getDetails(ApiKey.getApiKey(),"tasty.p.rapidapi.com",id);
        call.enqueue(new Callback<RecipeDetailApiResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailApiResponse> call, Response<RecipeDetailApiResponse> response) {
                listener.onLoading(false);
                if (!response.isSuccessful()){
                    listener.onFailure(String.valueOf(response.code())+" from onResponse");
                    return;
                }
                listener.onResponse(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailApiResponse> call, Throwable throwable) {
                listener.onLoading(false);
                listener.onFailure(throwable.getMessage()+ " from onFailure");
            }
        });
    }
    public void getSimilarRecipes(SimilarRecipeListener listener, int id){
        listener.onLoading(true);
        GetSimilarRecipes getSimilarRecipes = retrofit.create(GetSimilarRecipes.class);
        Call<SimilarRecipeApiResponse> call = getSimilarRecipes.getSimilarRecipes(ApiKey.getApiKey(),"tasty.p.rapidapi.com",id);
        call.enqueue(new Callback<SimilarRecipeApiResponse>() {
            @Override
            public void onResponse(Call<SimilarRecipeApiResponse> call, Response<SimilarRecipeApiResponse> response) {
                listener.onLoading(false);
                if (!response.isSuccessful()){
                    listener.onFailure(String.valueOf(response.code())+" from onResponse");
                    return;
                }
                listener.onResponse(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<SimilarRecipeApiResponse> call, Throwable throwable) {
                listener.onLoading(false);
                listener.onFailure(throwable.getMessage()+ " from onFailure");
            }
        });
    }

    public void getAutoComplete(AutoCompleteListener listener, String prefix){
        listener.onLoading(true);
        GetAutoComplete getAutoComplete = retrofit.create(GetAutoComplete.class);
        Call<AutoCompleteApiResponse> call = getAutoComplete.getAutoComplete(prefix);
        call.enqueue(new Callback<AutoCompleteApiResponse>() {
            @Override
            public void onResponse(Call<AutoCompleteApiResponse> call, Response<AutoCompleteApiResponse> response) {
                listener.onLoading(false);
                if (!response.isSuccessful()){
                    listener.onError(String.valueOf(response.code())+" from onResponse");
                    return;
                }
                listener.onResponse(response.body(), String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<AutoCompleteApiResponse> call, Throwable throwable) {
                listener.onLoading(false);
                listener.onError(throwable.getMessage()+ " from onFailure");
            }
        });
    }


}
