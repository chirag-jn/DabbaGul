package com.precog.dabbagul;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    mTextMessage.setText(R.string.nav_title_explore);
                    return true;
                case R.id.navigation_chats:
                    mTextMessage.setText(R.string.nav_title_chats);
                    return true;
                case R.id.navigation_requests:
                    mTextMessage.setText(R.string.nav_title_requests);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.nav_title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        removeActionBar(this);


    }

}
