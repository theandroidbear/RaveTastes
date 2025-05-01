package com.ravemaster.recipeapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ravemaster.recipeapp.api.RequestManager;
import com.ravemaster.recipeapp.api.getrecipelist.interfaces.RecipeListListener;
import com.ravemaster.recipeapp.api.getrecipelist.models.RecipeListApiResponse;

public class RecipesViewModel extends ViewModel {

    private final Context context;
    private RequestManager manager;
    private final MutableLiveData<RecipeListApiResponse> feedsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public RecipesViewModel(Context context){
        this.context = context.getApplicationContext();
        manager = new RequestManager(context);
    }

    public LiveData<RecipeListApiResponse> getFeedsLiveData() {
        return feedsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void fetchRecipesList(int offset, int size, String query){
        loadingLiveData.setValue(true);
        manager.getRecipeList(listener,offset,size, query);
    }

    private final RecipeListListener listener = new RecipeListListener() {
        @Override
        public void onResponse(RecipeListApiResponse response, String message) {
            loadingLiveData.setValue(false);
            feedsLiveData.setValue(response);
        }

        @Override
        public void onFailure(String message) {
            loadingLiveData.setValue(false);
            errorLiveData.setValue(message);
        }

        @Override
        public void onLoading(boolean isLoading) {
            loadingLiveData.setValue(isLoading);
        }
    };
}
