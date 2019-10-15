package com.precog.dabbagul;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BaseActivity extends AppCompatActivity {

    private static String TAG = "BaseActivity";

    public static void removeActionBar(AppCompatActivity activity) {
        activity.getSupportActionBar().hide();
    }

    public static void showActionBar(AppCompatActivity activity) {
        activity.getSupportActionBar().show();
    }


    public static void loge(String msg) {
        Log.e(TAG, msg);
    }

    public static void logv(String msg) {
        Log.v(TAG, msg);
    }

    public static void addClickListenerToButton(View.OnClickListener listener, Object o) {
        Button button;
        try {
            button = (Button) o;
            button.setOnClickListener(listener);
        } catch (Exception e) {
            loge("addClickListenerToButton: Object passed is not Button");
        }
    }

}