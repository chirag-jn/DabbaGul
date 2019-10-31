package com.precog.dabbagul;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {

    // Firebase Auth
    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    public static GoogleSignInClient mGoogleSignInClient;
    public static String userUID = null;
    public static UserProfile myProfileObj = null;

    // Firebase Database
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final CollectionReference profilesDB = db.collection("profiles");

    // Location
    public static LocationGetter myLocation = null;

    private static String TAG = "BaseActivityTag";

    public static void removeActionBar(AppCompatActivity activity) {
        activity.getSupportActionBar().hide();
    }

    public static void showActionBar(AppCompatActivity activity) {
        activity.getSupportActionBar().show();
    }


    public static void loge(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    public static void logv(String TAG, String msg) {
        Log.v(TAG, msg);
    }

    public static void addClickListenerToButton(View.OnClickListener listener, Object o) {
        Button button;
        try {
            button = (Button) o;
            button.setOnClickListener(listener);
        } catch (Exception e) {
            loge(TAG, "addClickListenerToButton: Object passed is not Button");
        }
    }

}
