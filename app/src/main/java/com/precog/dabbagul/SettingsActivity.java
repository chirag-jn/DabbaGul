package com.precog.dabbagul;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = "SettingsActivity";

    ImageButton returnBack;
//    Button account;
//    Button notifications;
    Button about;
    Button privacy;
    Button logout;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().hide();

        returnBack = findViewById(R.id.return_from_settings_button);
//        account = findViewById(R.id.settings_account);
//        notifications = findViewById(R.id.settings_notifications);
        about = findViewById(R.id.settings_about);
        privacy = findViewById(R.id.settings_privacy);
        logout = findViewById(R.id.settings_logout);

        returnBack.setOnClickListener(this);
//        account.setOnClickListener(this);
//        notifications.setOnClickListener(this);
        about.setOnClickListener(this);
        privacy.setOnClickListener(this);
        logout = findViewById(R.id.settings_logout);

        mContext = this;
    }

    @Override
    public void onClick(View view) {
        String website;
        Intent intent = new Intent(mContext, webActivity.class);
        switch (view.getId()) {
            case R.id.return_from_settings_button:
                finish();
                break;
//            case R.id.settings_account:
//                break;
//            case R.id.settings_notifications:
//                break;
            case R.id.settings_privacy:
                website = "http://home.iiitd.edu.in/~chirag17041/dabbagul/privacy_policy.html";
                intent.putExtra("web", website);
                mContext.startActivity(intent);
                break;
            case R.id.settings_about:
                website = "http://home.iiitd.edu.in/~chirag17041/dabbagul/index.html";
                intent.putExtra("web", website);
                mContext.startActivity(intent);
                break;
            case R.id.settings_logout:
                mAuth.signOut();
                finish();
                break;
            default:
                logv(TAG, "onClick: Kya click kar rhe ho");
                break;
        }
    }
}
