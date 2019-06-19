package com.example.insertactivity;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil
{
    public static FirebaseDatabase mFirebaseDatabse;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseutill;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static ArrayList<TravelDeal> mDeals;
    private static Activity caller;
    private static final int RC_SIGN_IN = 123;
    private FirebaseUtil(){}
    public static  void openFbReffernce(String ref, final Activity callerActivity)
    {
        if(firebaseutill == null)
        {
            firebaseutill = new FirebaseUtil();
            mFirebaseDatabse = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
                {
                    if(firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signIn();
                        //String firebaseUser = mFirebaseAuth.getCurrentUser().getUid();
                        //Log.d("checcc",firebaseUser);
                    }
                    Toast.makeText(callerActivity.getBaseContext(),"welcome", Toast.LENGTH_LONG).show();

                }
            };

        }
        mDeals = new ArrayList<>();
    //    mDatabaseReference = mFirebaseDatabse.getReference().child(ref);
        mDatabaseReference = mFirebaseDatabse.getReference().child("users");
;

    }

    private static  void signIn()
    {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
                /*new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build())*/

// Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
    public static void  attachListener()
    {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public static void  detach()
    {
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }
}
