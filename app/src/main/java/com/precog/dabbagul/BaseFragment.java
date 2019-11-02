package com.precog.dabbagul;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BaseFragment extends Fragment {

    private static String TAG = "BaseFragment";

    public static UserProfile myProfileObj = BaseActivity.myProfileObj;

    public static void loge(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    public static void logv(String TAG, String msg) {
        Log.v(TAG, msg);
    }
}
