package com.precog.dabbagul;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseFragment extends Fragment {

    private static String TAG = "BaseFragment";

    public static UserProfile myProfileObj = BaseActivity.myProfileObj;

    public static final FirebaseFirestore db = BaseActivity.db;
    public static final CollectionReference profilesDB = BaseActivity.profilesDB;
    public static final CollectionReference chatsDB = BaseActivity.chatsDB;
    public static final CollectionReference foodDB = BaseActivity.foodDB;
    public static final CollectionReference requestsDB = BaseActivity.requestsDB;

    public static LocationGetter myLocation = BaseActivity.myLocation;

    public static void loge(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    public static void logv(String TAG, String msg) {
        Log.v(TAG, msg);
    }
}
