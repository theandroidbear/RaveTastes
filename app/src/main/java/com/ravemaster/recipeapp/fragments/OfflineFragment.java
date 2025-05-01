package com.ravemaster.recipeapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.ravemaster.recipeapp.activities.LibraryActivity;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.adapters.LibraryAdapter;
import com.ravemaster.recipeapp.clickinterfaces.OnLibraryClicked;
import com.ravemaster.recipeapp.db.DBHelper;
import com.ravemaster.recipeapp.db.models.RecipePojo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfflineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfflineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;

    DBHelper helper;

    LibraryAdapter adapter;

    LottieAnimationView lottie;

    public OfflineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfflineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OfflineFragment newInstance(String param1, String param2) {
        OfflineFragment fragment = new OfflineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        helper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline, container, false);

        helper = new DBHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        adapter = new LibraryAdapter(getActivity(),getRecipes(),onLibraryClicked);

        getData();
    }

    private void getData(){

        adapter = new LibraryAdapter(getActivity(),getRecipes(),onLibraryClicked);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private final OnLibraryClicked onLibraryClicked = new OnLibraryClicked() {
        @Override
        public void moveToLibraryActivity(RecipePojo recipePojo) {
            Intent intent = new Intent(getActivity(), LibraryActivity.class);
            intent.putExtra("name", recipePojo.getName());
            intent.putExtra("description", recipePojo.getDescription());
            intent.putExtra("ingredients", recipePojo.getIngredients());
            intent.putExtra("instructions", recipePojo.getInstructions());
            startActivity(intent);
        }
    };

    private ArrayList<RecipePojo> getRecipes(){
        Cursor cursor = helper.getRecipes();
        ArrayList<RecipePojo> pojos = new ArrayList<>();
        if (cursor.getCount() == 0){
            lottie.setVisibility(View.VISIBLE);
            lottie.animate();
        } else {
            lottie.setVisibility(View.INVISIBLE);
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String ingredients = cursor.getString(3);
                String instructions = cursor.getString(4);
                pojos.add(new RecipePojo(name, description, ingredients, instructions));
            }
        }
        return pojos;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.libraryRecycler);
        lottie = view.findViewById(R.id.noInternetAnimation6);
    }
}