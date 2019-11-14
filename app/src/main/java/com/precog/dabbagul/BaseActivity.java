package com.precog.dabbagul;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BaseActivity extends AppCompatActivity {

    public static String[] tags = new String[]{"veg", "non-veg", "spicy", "salty", "baked", "fried", "sweet", "healthy", "north-indian", "south-indian", "street-style", "chinese", "thai", "italian"};

    // Firebase Auth
    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    public static GoogleSignInClient mGoogleSignInClient;
    public static String userUID = null;
    public static String userEmail = null;
    public static UserProfile myProfileObj = new UserProfile();

    // Firebase Database
    public static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final CollectionReference profilesDB = db.collection("profiles");
    public static final CollectionReference chatsDB = db.collection("chats");
    public static final CollectionReference foodDB = db.collection("food");
    public static final CollectionReference requestsDB = db.collection("requests");
    public static final StorageReference imageStorage = FirebaseStorage.getInstance().getReference();

    // Location
    public static LocationGetter myLocation = null;

    private ProgressDialog mProgressDialog;

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

    protected void showLoading(String message) {
        if(message==null) {
            message = "Loading...";
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


}
