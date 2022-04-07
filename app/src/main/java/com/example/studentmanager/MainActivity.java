package com.example.studentmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private BeginSignInRequest signInRequest;
    private SignInClient oneTapClient;
    private FirebaseAuth mAuth;
    private static final int REQ_ONE_TAP = 27;  // Can be any integer unique to the Activity.
    private final String TAG = "TAG:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside create");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.Login).setOnClickListener(v -> {
//            startActivity(new Intent(this,ClassList.class));
//            this.finish();
            Log.d(TAG, "inside button click");
            Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
            userSignin();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ONE_TAP) {
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                if (idToken != null) {
                    // Got an ID token from Google. Use it to authenticate
                    // with Firebase.
                    Log.d(TAG, "Got ID token. " + idToken);
                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                    mAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.d(TAG, user.getEmail() + user.getDisplayName() + user.getUid());
                                    checkUserData(user.getUid());
                                    Toast.makeText(getApplicationContext(),user.getEmail() + user.getDisplayName() + user.getUid(),Toast.LENGTH_LONG).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "signInWithCredential:failure", task.getException());
                                }
                            });
                }
            } catch (ApiException e) {
                // ...
            }
        }

    }
    private void signInSetup(){
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id_auth))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
    }

    //this is called in two places if user already signup and data doesn't exit or new user is created and data doesn't exit
    private void checkUserData(String userId){
        FirebaseFirestore.getInstance().document("Schools/"+userId).get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()){
                String imageUrl = "https://firebasestorage.googleapis.com/v0/b/student-manager-dee4c.appspot.com/o/school.jpg?alt=media&token=efb6519b-ad73-420c-9cb5-6ef74f8e5815";
                Map<String,String> user = new HashMap<>();
                user.put("name","Sample Name");
                user.put("location","Sample location");
                user.put("image",imageUrl);
                FirebaseFirestore.getInstance().document("Schools/"+userId).set(user)
                        .addOnSuccessListener(unused -> {
                            Log.d(TAG, "User data created successfull");
                        })
                        .addOnFailureListener(e -> {
                            Log.d(TAG, "Failed to create userdata");
                        });
            }else{
                startActivity(new Intent(this,ClassList.class));
                this.finish();
            }
        });
    }

    private void userSignin(){
        if(mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            Log.d(TAG, user.getEmail() + user.getDisplayName() + user.getUid());
            Toast.makeText(getApplicationContext(),user.getEmail() + user.getDisplayName() + user.getUid(),Toast.LENGTH_LONG).show();
            checkUserData(user.getUid());
            return;
        }
        signInSetup();
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("Login: ", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                            Toast.makeText(getApplicationContext(),"Couldn't start One Tap UI: " + e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(this, e -> {
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d("Login: ", e.getLocalizedMessage());
                    Log.d("Login: ", "Login failed");
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                });
    }

}