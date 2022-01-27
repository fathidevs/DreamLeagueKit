package com.gmail.dreamleaguekit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gmail.dreamleaguekit.create_kit.CreateFragment;
import com.gmail.dreamleaguekit.favorite.FavoriteFragment;
import com.gmail.dreamleaguekit.home.HomeFragment;
import com.gmail.dreamleaguekit.profile.ProfileFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity {


    private BottomNavigationView nMain_bottom_nav;
    private static final int NUMBER_OF_TABS = 3;
    private static final int FIRST_TAB = 0;
    private static final int SECOND_TAB = 1;
    private static final int THIRD_TAB = 2;
    private static final int FOURTH_TAB = 3;
    private final Deque<Integer> integerDeque = new ArrayDeque<>(NUMBER_OF_TABS);
    private boolean flag = true;

    private AdView nAd_view_main;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        adBanner();

        // Add home tab to deque list
        integerDeque.push(R.id.home_tab);
        loadFragment(new HomeFragment());
        // Set home as default
        nMain_bottom_nav.setSelectedItemId(R.id.home_tab);

        onNavItemSelectedListener();
    }

    private void init() {
        nMain_bottom_nav = findViewById(R.id.main_bottom_nav);

        nAd_view_main = findViewById(R.id.ad_view_main);
        adRequest = new AdRequest.Builder().build();
    }

    private void adBanner() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        nAd_view_main.loadAd(adRequest);
    }

    private void loadFragment(Fragment fragment) {
        try {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }catch (Exception e)
        {
            //Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("loadFragment: ", e.getMessage());
        }

    }
    private void setCheckedSelectedItemOnBackPressed(int i) {
        try {

            nMain_bottom_nav.getMenu().getItem(i).setChecked(true);

        }catch (Exception e)
        {
            //Toast.makeText(this, "item checked: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("item checked: ", e.getMessage());
        }
    }
    private Fragment getFragment(int itemId) {
        if (itemId == R.id.home_tab)
        {
            setCheckedSelectedItemOnBackPressed(FIRST_TAB);
            // return home fragment
            return new HomeFragment();
        }
        else if (itemId == R.id.create_tab)
        {
            setCheckedSelectedItemOnBackPressed(SECOND_TAB);
            // return Create fragment
            return new CreateFragment();
        }
        else if (itemId == R.id.fav_tab)
        {
            setCheckedSelectedItemOnBackPressed(THIRD_TAB);
            // return favorite fragment
            return new FavoriteFragment();
        }
        else if (itemId == R.id.profile_tab)
        {
            setCheckedSelectedItemOnBackPressed(FOURTH_TAB);
            // return profile fragment
            return new ProfileFragment();
        }
        // Set home fragement as default
        setCheckedSelectedItemOnBackPressed(0);
        return new HomeFragment();
    }

    private void onNavItemSelectedListener() {
        nMain_bottom_nav.setOnNavigationItemSelectedListener(item -> {
            try {
                int id = item.getItemId();
                if (integerDeque.contains(id)) {
                    if ( id == R.id.home_tab) {
                        if (integerDeque.size() != 1) {
                            if (flag) {
                                // Add home fragment to deque list
                                integerDeque.addFirst(R.id.home_tab);
                                // Set flag to false
                                flag = false;
                            }
                        }
                    }
                    // Remove selected id from deque list
                    integerDeque.remove(id);
                }
                // Push selected id to deque
                integerDeque.push(id);
                // Load fragment
                loadFragment(getFragment(item.getItemId()));
            }catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "item selected: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("item selected: ", e.getMessage());
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        try {
            // Pop to previous fragment
            integerDeque.pop();
                if (!integerDeque.isEmpty()) {
                    loadFragment(getFragment(integerDeque.peek()));
                } else {
                    super.onBackPressed();
                    //finish();
                }
        }catch (Exception e)
        {
            //Toast.makeText(this, "back pressed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("back pressed: ", e.getMessage());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            //Write your fragment name instead of YourFragmentName
            if (fragment instanceof CreateFragment) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }
}