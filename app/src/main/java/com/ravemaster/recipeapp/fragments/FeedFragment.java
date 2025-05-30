package com.ravemaster.recipeapp.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.ravemaster.recipeapp.adapters.FeatureAdapter;
import com.ravemaster.recipeapp.adapters.PagerAdapter;
import com.ravemaster.recipeapp.api.getfeed.models.Item;
import com.ravemaster.recipeapp.api.getfeed.models.Recipe;
import com.ravemaster.recipeapp.clickinterfaces.OnFeatureClicked;
import com.ravemaster.recipeapp.clickinterfaces.OnFeedTwoClicked;
import com.ravemaster.recipeapp.viewmodels.FeedViewModel;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.activities.RecipeDetailsActivity;
import com.ravemaster.recipeapp.adapters.MealPlanAdapter;
import com.ravemaster.recipeapp.adapters.TrendingAdapter;
import com.ravemaster.recipeapp.api.RequestManager;
import com.ravemaster.recipeapp.api.getfeed.models.FeedsApiResponse;
import com.ravemaster.recipeapp.clickinterfaces.OnMealPlanClicked;
import com.ravemaster.recipeapp.clickinterfaces.OnTrendingClicked;
import com.ravemaster.recipeapp.viewmodelfactories.FeedViewModelFactory;
import com.ravemaster.recipeapp.utilities.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    public ShimmerFrameLayout featurePlaceHolder, featurePlaceHolder1, mealPlanPlaceHolder,trendingPlaceHolder;
    public LinearLayout featureLayout2,featureLayout1,mealPlanLayout,trendingLayout, mainLayout;
    ImageView imgFeature;
    TextView txtFeatureName, txtFeatureRating, txtFeatureTime, txtFeatureServings,txtMealPlanTitle,txtUsername,feedTitle2,feedTitle3;
    RecyclerView mealPlanRecycler,trendingRecycler,featureRecycler;
    MaterialToolbar toolbar;
    ViewPager2 pager;

    RelativeLayout loadingLayout, errorLayout;

    SwipeRefreshLayout swipeRefreshLayout;

    LottieAnimationView lottie,lottie1,lottie2,lottie4;

    RequestManager manager;
    MealPlanAdapter mealPlanAdapter;
    TrendingAdapter trendingAdapter;
    FeatureAdapter featureAdapter;
    PreferenceManager preferenceManager;
    public int id = 0;
    FeedViewModel feedViewModel;
    FeedViewModelFactory factory;
    boolean isfetched = false;
    List<Item> pagerModels = new ArrayList<>();
    PagerAdapter adapter;

    public FeedFragment() {
        // Required empty public constructor
    }
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new RequestManager(getActivity());
        preferenceManager = new PreferenceManager(requireActivity());
        factory = new FeedViewModelFactory(requireActivity());
        feedViewModel = new ViewModelProvider(this, factory).get(FeedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        fetch();
//        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

        if (!isfetched){
            feedViewModel.fetchFeedList(false);
            isfetched = true;
        } else {
            Toast.makeText(requireActivity(), "Data has already been fetched", Toast.LENGTH_SHORT).show();
            Toast.makeText(requireContext(), String.valueOf(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
        }

        imgFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedViewModel.fetchFeedList(false);
            }
        });
    }
    private void fetch(){
        feedViewModel.getFeedsLiveData().observe(getViewLifecycleOwner(), response ->{
            swipeRefreshLayout.setRefreshing(false);
            hideAnimation();
            stopShimmer();
            showLayouts();
            showData(response);
        });
        feedViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), message->{
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            stopShimmer();
            hideLayouts();
            showAnimation();
        });
        feedViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading->{
            if (isLoading){
                hideAnimation();
                hideLayouts();
                startShimmer();
                Toast.makeText(requireContext(), "Refreshing feed...", Toast.LENGTH_SHORT).show();
            }else{
                swipeRefreshLayout.setRefreshing(false);
                stopShimmer();
                hideAnimation();
                showLayouts();
            }
        });
    }
    private void showAnimation(){
//        errorLayout.setVisibility(VISIBLE);
        lottie.setVisibility(VISIBLE);
        lottie.animate();
        lottie1.setVisibility(VISIBLE);
        lottie1.animate().setStartDelay(2500).setDuration(2500);
        lottie2.setVisibility(VISIBLE);
        lottie2.animate().setStartDelay(5000).setDuration(5000);
    }
    private void hideAnimation(){
//        errorLayout.setVisibility(GONE);
        lottie.setVisibility(GONE);
        lottie1.setVisibility(GONE);
        lottie2.setVisibility(GONE);
    }
    private void startShimmer(){
//        loadingLayout.setVisibility(VISIBLE);
        featurePlaceHolder.setVisibility(VISIBLE);
        featurePlaceHolder.startShimmer();
        mealPlanPlaceHolder.setVisibility(VISIBLE);
        mealPlanPlaceHolder.startShimmer();
        trendingPlaceHolder.setVisibility(VISIBLE);
        trendingPlaceHolder.startShimmer();
        txtFeatureName.setText("");
        txtMealPlanTitle.setText("");
        feedTitle2.setText("");
        feedTitle3.setText("");
    }
    private void stopShimmer(){
//        loadingLayout.setVisibility(GONE);
        featurePlaceHolder.stopShimmer();
        featurePlaceHolder.setVisibility(GONE);
        mealPlanPlaceHolder.stopShimmer();
        mealPlanPlaceHolder.setVisibility(GONE);
        trendingPlaceHolder.stopShimmer();
        trendingPlaceHolder.setVisibility(GONE);
    }
    private void showLayouts(){
//        mainLayout.setVisibility(VISIBLE);
        featureLayout1.setVisibility(VISIBLE);
        mealPlanLayout.setVisibility(VISIBLE);
        trendingLayout.setVisibility(VISIBLE);
        pager.setVisibility(VISIBLE);
    }
    private void hideLayouts(){
//        mainLayout.setVisibility(GONE);
        featureLayout1.setVisibility(GONE);
        mealPlanLayout.setVisibility(GONE);
        trendingLayout.setVisibility(GONE);
        pager.setVisibility(GONE);
    }

    private void showData(FeedsApiResponse response) {
        showFeatured(response);
        showMealPlanAdapter(response);
        showTrendingRecipes(response);
        showAppetizers(response);
    }

    private void showManyFeatured(FeedsApiResponse response) {
        txtFeatureName.setText(response.results.get(0).item.name);
        featureAdapter = new FeatureAdapter(getActivity(),response.results.get(0).item.recipes,onFeatureClicked);
        featureRecycler.setAdapter(trendingAdapter);
        featureRecycler.setHasFixedSize(true);
        featureRecycler.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void showFeatured(FeedsApiResponse response) {
        Item item3 = response.results.get(0).item;
        Glide.with(requireActivity())
                .load(response.results.get(0).item.thumbnail_url)
                .placeholder(R.drawable.placeholder)
                .into(imgFeature);

        id = item3.id;

        String name = item3.name;

        int positive = item3.user_ratings.count_positive;

        int negative = item3.user_ratings.count_negative;
        int total = positive + negative;
        double percent = ((double) positive / total ) * 100;
        String ratings = String.format("%.1f%%",percent);



        String time = String.valueOf(response.results.get(0).item.total_time_minutes);

        String servings = String.valueOf(response.results.get(0).item.num_servings)+ " people";

        txtFeatureName.setText("Featured: "+name);
        txtFeatureName.setSelected(true);
        txtFeatureRating.setText(ratings);
        if (time.isEmpty() || time == null){
            txtFeatureTime.setText("60 min");
        } else {
            txtFeatureTime.setText(time+" min");
        }
        txtFeatureServings.setText(servings);
    }

    private void showTrendingRecipes(FeedsApiResponse response) {
        feedTitle2.setText(response.results.get(5).name);
        trendingAdapter = new TrendingAdapter(getActivity(),response.results.get(5).items,onTrendingClicked);
        trendingRecycler.setAdapter(trendingAdapter);
        trendingRecycler.setHasFixedSize(true);
        trendingRecycler.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void showMealPlanAdapter(FeedsApiResponse response) {
        txtMealPlanTitle.setText(response.results.get(3).name);
        mealPlanAdapter = new MealPlanAdapter(getActivity(),response.results.get(3).items,onMealPlanClicked);
        mealPlanRecycler.setAdapter(mealPlanAdapter);;
    }

    private void showAppetizers(FeedsApiResponse response) {
        feedTitle3.setText(response.results.get(7).name);
        adapter = new PagerAdapter(requireActivity(),onFeedTwoClicked);
        pagerModels = response.results.get(7).items;
        adapter.setPagerModels(pagerModels);
        pager.setAdapter(adapter);

        pager.setOffscreenPageLimit(3);
        pager.setPageTransformer((page, position) -> {
            float offset = position * -(30); // Adjust spacing between items
            page.setTranslationX(offset);
            page.setScaleY(1 - (0.1f * Math.abs(position))); // Optional: Scale effect
        });
    }

    private final OnMealPlanClicked onMealPlanClicked = new OnMealPlanClicked() {
        @Override
        public void moveToRecipeDetails(Item item2) {
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtra("id",item2.id);
            startActivity(intent);
        }
    };

    private final OnTrendingClicked onTrendingClicked = new OnTrendingClicked() {
        @Override
        public void moveToRecipeDetails(Item item) {
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtra("id",item.id);
            startActivity(intent);
        }
    };
    private final OnFeatureClicked onFeatureClicked = new OnFeatureClicked() {
        @Override
        public void moveToRecipeDetails(Recipe item) {
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtra("id",item.id);
            startActivity(intent);
        }
    };
    private final OnFeedTwoClicked onFeedTwoClicked = new OnFeedTwoClicked() {
        @Override
        public void moveToRecipeDetails(Item item) {
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtra("id",item.id);
            startActivity(intent);
        }
    };

    private void initViews(View view) {
        featurePlaceHolder = view.findViewById(R.id.featurePlaceholderLayout);
        mealPlanPlaceHolder = view.findViewById(R.id.mealPlanPlaceholderLayout);
        trendingPlaceHolder = view.findViewById(R.id.trendingPlaceholderLayout);
        featureLayout1 = view.findViewById(R.id.featureLayout);
//        mainLayout = view.findViewById(R.id.feedMainContent);
        mealPlanLayout = view.findViewById(R.id.mealPlanLayout);
        trendingLayout = view.findViewById(R.id.trendingLayout);
        imgFeature = view.findViewById(R.id.imgFeature);
        txtFeatureName = view.findViewById(R.id.txtFeatureRecipeName);
        txtMealPlanTitle = view.findViewById(R.id.txtMealPlanTitle);
        feedTitle2 = view.findViewById(R.id.feedTitle2);
        feedTitle3 = view.findViewById(R.id.feedTitle3);
        txtFeatureTime = view.findViewById(R.id.txtFeatureTime);
        txtFeatureRating = view.findViewById(R.id.txtFeatureRating);
        txtFeatureServings = view.findViewById(R.id.txtFeatureServing);
        mealPlanRecycler = view.findViewById(R.id.mealPlanRecycler);
        featureRecycler = view.findViewById(R.id.featureRecycler);
        trendingRecycler = view.findViewById(R.id.trendingRecycler);
        swipeRefreshLayout = view.findViewById(R.id.feedRefresh);
        pager = view.findViewById(R.id.viewPagerFeed);
        lottie = view.findViewById(R.id.noInternetAnimation);
        lottie1 = view.findViewById(R.id.noInternetAnimation1);
        lottie2 = view.findViewById(R.id.noInternetAnimation2);
//        loadingLayout = view.findViewById(R.id.loadingLayout);
//        errorLayout = view.findViewById(R.id.errorLayout);
        toolbar = view.findViewById(R.id.myToolBar);
    }
}