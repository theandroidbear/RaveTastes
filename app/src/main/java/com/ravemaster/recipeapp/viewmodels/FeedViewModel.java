package com.ravemaster.recipeapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ravemaster.recipeapp.api.RequestManager;
import com.ravemaster.recipeapp.api.getfeed.feedinterfaces.FeedsListListener;
import com.ravemaster.recipeapp.api.getfeed.models.FeedsApiResponse;

public class FeedViewModel extends ViewModel {

    private final Context context;
    private RequestManager manager;
    private final MutableLiveData<FeedsApiResponse> feedsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public FeedViewModel(Context context){
        this.context = context.getApplicationContext();
        manager = new RequestManager(context);
    }

    public LiveData<FeedsApiResponse> getFeedsLiveData() {
        return feedsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void fetchFeedList(boolean vegetarian) {
        loadingLiveData.setValue(true);
        manager.getFeedList(listener, vegetarian);
    }


    private final FeedsListListener listener = new FeedsListListener() {
        @Override
        public void onResponse(FeedsApiResponse response, String message) {
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
