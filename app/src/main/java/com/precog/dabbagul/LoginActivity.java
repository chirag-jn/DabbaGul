package com.precog.dabbagul;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class LoginActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 7;
    SignInButton button;

    private Class switchClass = MainActivity.class;
    private boolean isNotLoggedIn;

    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myLocation = new LocationGetter();
        myLocation.initialize(this);
        mAuth = FirebaseAuth.getInstance();

        logv(TAG, "flag 1");

        //TODO: Remove this
//        mAuth.signOut();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        currentUser = mAuth.getCurrentUser();

        logv(TAG, "flag 2");
        updateUI();

        if(isNotLoggedIn) {
            setContentView(R.layout.activity_login);
            button = findViewById(R.id.google_sign_in);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
        } else {
            switchAct();
        }
    }

    private void updateUI() {
        currentUser = mAuth.getCurrentUser();
        isNotLoggedIn = currentUser == null;
    }

    private void initializeUserProfile() {

        DocumentReference myProfileDB = profilesDB.document(userEmail);
        myProfileDB.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e!=null) {
                    loge(TAG, "Error in initializeUserProfile");
                }
                myProfileObj = documentSnapshot.toObject(UserProfile.class);
            }
        });
    }

    private void switchAct() {
        finish();
        logv(TAG, "flag 3");
        currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            userUID = currentUser.getUid();
            userEmail = currentUser.getEmail();
        }
        initializeUserProfile();
        Intent intent = new Intent(this, switchClass);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null)
                    firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                loge(TAG, "sign in failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        logv(TAG, "firebaseAuthWithGoogle: " + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            logv(TAG, "signInWithCredential:success");
                            updateUI();
                            switchAct();
                        } else {
                            // If sign in fails, display a message to the user.
                            loge(TAG, "signInWithCredential:failure " + task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI();
                        }
                    }
                });
    }
}
