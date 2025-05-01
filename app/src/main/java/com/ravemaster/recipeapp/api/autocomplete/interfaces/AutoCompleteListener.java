package com.ravemaster.recipeapp.api.autocomplete.interfaces;

import com.ravemaster.recipeapp.api.autocomplete.models.AutoCompleteApiResponse;

public interface AutoCompleteListener {
    void onResponse(AutoCompleteApiResponse response, String message);
    void onError( String message);
    void onLoading( boolean isLoading);
}
