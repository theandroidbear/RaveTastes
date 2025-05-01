package com.ravemaster.recipeapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ravemaster.recipeapp.fragments.FeedFragment;
import com.ravemaster.recipeapp.fragments.OfflineFragment;
import com.ravemaster.recipeapp.fragments.SearchFragment;
import com.ravemaster.recipeapp.fragments.SettingsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SearchFragment();
            case 2:
                return new OfflineFragment();
            case 3:
                return new SettingsFragment();
            default:
                return new FeedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
