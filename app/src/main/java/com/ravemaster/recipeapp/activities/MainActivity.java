package com.ravemaster.recipeapp.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.fragments.FeedFragment;
import com.ravemaster.recipeapp.fragments.OfflineFragment;
import com.ravemaster.recipeapp.fragments.SearchFragment;
import com.ravemaster.recipeapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private FeedFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

//        viewPager2.setAdapter(new ViewPagerAdapter(this));
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//            }
//        });
        fragment = new FeedFragment();
        replaceFragment(fragment);
        bottomNavigationView.setOnItemSelectedListener(bottomListener);

    }

    private final NavigationBarView.OnItemSelectedListener bottomListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.idHome){
//                item.setCheckable(true);
//                item.setEnabled(true);
//                item.setChecked(true);
//                viewPager2.setCurrentItem(0);
                replaceFragment(new FeedFragment());
                return true;
            } else if (item.getItemId() == R.id.idSearch) {
//                item.setCheckable(true);
//                item.setEnabled(true);
//                item.setChecked(true);
//                viewPager2.setCurrentItem(1);
                replaceFragment(new SearchFragment());
                return true;
            }  else if (item.getItemId() == R.id.idLibrary) {
//                item.setCheckable(true);
//                item.setEnabled(true);
//                item.setChecked(true);
//                viewPager2.setCurrentItem(2);
                replaceFragment(new OfflineFragment());
                return true;
            } else if (item.getItemId() == R.id.idSettings) {
//                item.setCheckable(true);
//                item.setEnabled(true);
//                item.setChecked(true);
//                viewPager2.setCurrentItem(3);
                replaceFragment(new SettingsFragment());
                return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment);
        transaction.commit();
    }

    private void initViews(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        viewPager2 = findViewById(R.id.myViewPager);
    }
}