package com.precog.dabbagul;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends BaseActivity {

    private static String TAG = "MainActivity";

    // Main Activity Objects
    private FrameLayout mainActivityFrame;
    private BottomNavigationView navView;

    // Fragments for Switching the Fragments in the Main Activity
    Fragment exploreFragment  = new ExploreFragment();
    Fragment profileFragment  = new ProfileFragment();
    Fragment requestsFragment = new RequestsFragment();
    Fragment chatsFragment    = new ChatsFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    selectedFragment = exploreFragment;
                    break;
                case R.id.navigation_chats:
                    selectedFragment = chatsFragment;
                    break;
                case R.id.navigation_requests:
                    selectedFragment = requestsFragment;
                    break;
                case R.id.navigation_profile:
                    selectedFragment = profileFragment;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(mainActivityFrame.getId(), selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Splash Screen
//        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ID
        navView = findViewById(R.id.nav_view);
        mainActivityFrame = findViewById(R.id.main_activity_frame);

        // Start Listener for Bottom Navigation View
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Remove Action Bar from Top
        removeActionBar(this);

        // Setting Height of Fragment
        int ht = navView.getHeight();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mainActivityFrame.getLayoutParams();
        params.setMargins(0, 0, 0, ht);
        mainActivityFrame.setLayoutParams(params);

        //  Setting the Default Fragment
        getSupportFragmentManager().beginTransaction().replace(mainActivityFrame.getId(), exploreFragment).commit();
        navView.setSelectedItemId(R.id.navigation_explore);
    }

}
