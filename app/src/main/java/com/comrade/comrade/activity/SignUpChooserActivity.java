package com.comrade.comrade.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.databinding.ActivitySignUpChooserBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignUpChooserActivity extends AppCompatActivity {


    private final String TAG = "Sign in Activity";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final int RC_SIGN_IN = 111;
    public GoogleSignInClient mGoogleSignInClient;
    ActivitySignUpChooserBinding binding;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_chooser);

        viewInit();

        onClick();
        dialogBox();
        progressDialog();
        faceBookSetup();

    }

    private void viewInit() {
       /* LottieComposition lottieComposition= LottieCompositionFactory.fromAssetSync(getApplicationContext(), "love_json.json").getValue();
        binding.animationView.setComposition(lottieComposition);//crash on this line
        binding.animationView.playAnimation();*/
    }

    private void onClick() {


        binding.fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.myFbBtn.performClick();

            }
        });

        binding.googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSetup();

            }
        });
        binding.loginWithNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignUpChooserActivity.this, LoginActivity.class));
            }
        });


    }

    private void progressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Google sign in");
        progressDialog.setMessage("Signing in");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    private void dialogBox() {


    }

    private void faceBookSetup() {

        callbackManager = CallbackManager.Factory.create();

        binding.myFbBtn.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.d(TAG, "onSuccess" + loginResult);

                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "onSuccess" + "onCancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e(TAG, "onSuccess" + error.getMessage());
                    }
                });


        authStateListener = firebaseAuth -> {

            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {

                //uploadUserDB();
            }
        };


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        //uploadUserDB();

                        Toast.makeText(getApplicationContext(), "Sign in Successful", Toast.LENGTH_SHORT).show();


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(SignUpChooserActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //google
    private void googleSetup() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signIn();

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
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithCredential:success");
                    // uploadUserDB();

                    Toast.makeText(getApplicationContext(), "Sign in Successful", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> {

            Log.w(TAG, "signInWithCredential:failure" + e.getMessage());

            Toast.makeText(getApplicationContext(), "GoogleVerification Failed", Toast.LENGTH_LONG).show();


        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null) {

            mAuth.removeAuthStateListener(authStateListener);
        }

    }


}