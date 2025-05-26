package com.ravemaster.recipeapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.activities.RecipeDetailsActivity;
import com.ravemaster.recipeapp.adapters.AutoCompleteAdapter;
import com.ravemaster.recipeapp.adapters.RecipeListAdapter;
import com.ravemaster.recipeapp.api.RequestManager;
import com.ravemaster.recipeapp.api.autocomplete.models.AutoCompleteApiResponse;
import com.ravemaster.recipeapp.api.getrecipelist.models.RecipeListApiResponse;
import com.ravemaster.recipeapp.api.getrecipelist.models.Result;
import com.ravemaster.recipeapp.clickinterfaces.AutoCompleteClick;
import com.ravemaster.recipeapp.clickinterfaces.OnRecipeClicked;
import com.ravemaster.recipeapp.viewmodelfactories.AutoCompleteFactory;
import com.ravemaster.recipeapp.viewmodelfactories.RecipeListViewModelFactory;
import com.ravemaster.recipeapp.viewmodels.AutoCompleteViewModel;
import com.ravemaster.recipeapp.viewmodels.RecipesViewModel;

public class SearchFragment extends Fragment {

    public ShimmerFrameLayout recipePlaceHolder;
    public LinearLayout recipeLayout;
    SearchBar searchBar;
    SearchView searchView;
    LottieAnimationView lottie;

    ChipGroup chipGroup;

    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView,autoRecycler;
    RecipeListAdapter adapter;
    AutoCompleteAdapter autoCompleteAdapter;

    RequestManager manager;

    RecipesViewModel recipesViewModel;
    RecipeListViewModelFactory factory;

    AutoCompleteViewModel viewModel;
    AutoCompleteFactory autoCompleteFactory;

    public int offset = 0;
    public String mainQuery = "";
    boolean isFetched = false;

    public SearchFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = new RecipeListViewModelFactory(requireActivity());
        autoCompleteFactory = new AutoCompleteFactory(requireActivity());
        recipesViewModel = new ViewModelProvider(requireActivity(),factory).get(RecipesViewModel.class);
        viewModel = new ViewModelProvider(requireActivity(),autoCompleteFactory).get(AutoCompleteViewModel.class);
        manager = new RequestManager(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        fetch();

        if (!isFetched){
            recipesViewModel.fetchRecipesList(offset,20,"",getMainQuery());
            viewModel.fetchAutoComplete("lasagna");
            isFetched = true;
            return;
        }

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()){
                recipesViewModel.fetchRecipesList(0,20,"",mainQuery);
            } else {
                for (int i:
                        checkedIds){
                    if (i == R.id.chipVegetarian){
                        recipesViewModel.fetchRecipesList(0,20,"vegetarian",mainQuery);
                    }
                    else if (i == R.id.chipUnder30Mins){
                        recipesViewModel.fetchRecipesList(0,20,"under_30_minutes",mainQuery);
                    }else if (i == R.id.chipBreakfast){
                        recipesViewModel.fetchRecipesList(0,20,"breakfast",mainQuery);
                    }
                    else if (i == R.id.chipDinner){
                        recipesViewModel.fetchRecipesList(0,20,"dinner",mainQuery);
                    }
                    else if (i == R.id.chipParty){
                        recipesViewModel.fetchRecipesList(0,20,"party",mainQuery);
                    }
                    else if (i == R.id.chipThai){
                        recipesViewModel.fetchRecipesList(0,20,"thai",mainQuery);
                    }
                    else if (i == R.id.chipItalian){
                        recipesViewModel.fetchRecipesList(0,20,"italian",mainQuery);
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            recipesViewModel.fetchRecipesList(offset,20,"",getMainQuery());
            searchBar.setText("");
        });

        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.fetchAutoComplete(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchView.setupWithSearchBar(searchBar);

        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                String query = v.getText().toString();

                recipesViewModel.fetchRecipesList(offset,20,"",setMainQuery(query));
                searchBar.setText(searchView.getText());
                searchView.hide();
                // Perform your search logic here
                return true;
            }
            return false;
        });

    }

    private void fetch(){
        recipesViewModel.getFeedsLiveData().observe(getViewLifecycleOwner(), response -> {
            //dismiss swipe refresh
            swipeRefreshLayout.setRefreshing(false);
            //hide animations and shimmers
            stopAnimations();
            stopShimmer();
            //show layouts and data
            showLayouts();
            showData(response);

        } );
        recipesViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireActivity(), "Unable to load recipes", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            //hide layouts and shimmer
            stopShimmer();
            hideLayouts();
            //start animation
            startAnimations();
        } );
        recipesViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading){
                hideLayouts();
                stopAnimations();
                startShimmer();
            } else {
                swipeRefreshLayout.setRefreshing(false);
                stopAnimations();
                stopShimmer();
            }
        } );
        viewModel.getFeedsLiveData().observe(getViewLifecycleOwner(), this::showAutoCompleteRecycler);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), message ->{
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        });
        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading->{

        });
    }

    private void startShimmer(){
        recipePlaceHolder.setVisibility(View.VISIBLE);
        recipePlaceHolder.startShimmer();
    }

    private void stopShimmer(){
        recipePlaceHolder.stopShimmer();
        recipePlaceHolder.setVisibility(View.GONE);
    }

    private void showLayouts(){
        recipeLayout.setVisibility(View.VISIBLE);
    }

    private void hideLayouts(){
        recipeLayout.setVisibility(View.GONE);
    }

    private void startAnimations(){
        lottie.setVisibility(View.VISIBLE);
        lottie.animate();
    }

    private void stopAnimations(){
        lottie.setVisibility(View.GONE);
    }


    private void showAutoCompleteRecycler(AutoCompleteApiResponse response) {
        if (response != null){
            autoCompleteAdapter = new AutoCompleteAdapter(getActivity(),response.results,autoCompleteClick);
            autoRecycler.setAdapter(autoCompleteAdapter);
            autoRecycler.setHasFixedSize(true);
            autoRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }

    private void showData(RecipeListApiResponse response) {
        adapter = new RecipeListAdapter(getActivity(),response.results,onRecipeClicked);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    private final OnRecipeClicked onRecipeClicked = new OnRecipeClicked() {
        @Override
        public void moveToRecipeActivity(Result result) {
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtra("id",result.id);
            startActivity(intent);
        }
    };

    private final AutoCompleteClick autoCompleteClick = new AutoCompleteClick() {
        @Override
        public void search(com.ravemaster.recipeapp.api.autocomplete.models.Result result) {
            searchView.hide();
            searchBar.setText(result.display);
            recipeLayout.setVisibility(View.INVISIBLE);
            recipePlaceHolder.setVisibility(View.VISIBLE);
            recipePlaceHolder.startShimmer();
            recipesViewModel.fetchRecipesList(offset,10,"", result.display);
        }
    };
    private String getMainQuery(){
        return mainQuery;
    }
    private String setMainQuery(String q){
        return this.mainQuery = q;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recipesRecycler);
        recipeLayout = view.findViewById(R.id.recipesLayout);
        recipePlaceHolder = view.findViewById(R.id.recipesPlaceholderLayout);
        autoRecycler = view.findViewById(R.id.autoCompleteRecycler);
        swipeRefreshLayout = view.findViewById(R.id.searchRefresh);
        searchBar = view.findViewById(R.id.mySearch_bar);
        searchView = view.findViewById(R.id.mySearchView);
        lottie = view.findViewById(R.id.noInternetAnimation3);
        chipGroup = view.findViewById(R.id.tagsChips);
    }
}