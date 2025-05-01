package com.ravemaster.recipeapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ravemaster.recipeapp.api.RequestManager;
import com.ravemaster.recipeapp.api.autocomplete.interfaces.AutoCompleteListener;
import com.ravemaster.recipeapp.api.autocomplete.models.AutoCompleteApiResponse;
import com.ravemaster.recipeapp.api.getrecipelist.interfaces.RecipeListListener;
import com.ravemaster.recipeapp.api.getrecipelist.models.RecipeListApiResponse;

public class AutoCompleteViewModel extends ViewModel {

    private final Context context;
    private RequestManager manager;
    private final MutableLiveData<AutoCompleteApiResponse> feedsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public AutoCompleteViewModel(Context context){
        this.context = context.getApplicationContext();
        manager = new RequestManager(context);
    }

    public LiveData<AutoCompleteApiResponse> getFeedsLiveData() {
        return feedsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void fetchAutoComplete(String query){
        manager.getAutoComplete(listener,query);
    }

    private AutoCompleteListener listener = new AutoCompleteListener() {
        @Override
        public void onResponse(AutoCompleteApiResponse response, String message) {
            loadingLiveData.setValue(false);
            feedsLiveData.setValue(response);
        }

        @Override
        public void onError(String message) {
            loadingLiveData.setValue(false);
            errorLiveData.setValue(message);
        }

        @Override
        public void onLoading(boolean isLoading) {
            loadingLiveData.setValue(isLoading);
        }
    };
}
