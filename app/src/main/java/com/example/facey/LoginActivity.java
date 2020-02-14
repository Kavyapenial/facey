package com.example.facey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.facey.config.DataManager;
import com.example.facey.config.Session;
import com.example.facey.interfaces.RetrofitCallBack;
import com.example.facey.models.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    MaterialButton signInButton;
    FirebaseAuth firebaseAuth;
    GoogleApiClient gGoogleApiClient;
    GoogleSignInClient googleSignInClient;


    private  static  final  String TAG = "LOGIN_ACTIVITY";
    private  static final int RQ_SIGN_IN = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Initialize ui components
        signInButton = findViewById(R.id.googleSignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RQ_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RQ_SIGN_IN);
    }

    private  void signOut(){
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Signing out");
            }
        });
    }

    private HashMap<String, String> getEmailParams(String email) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        return hashMap;
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.d(TAG, "Account : "+account.getEmail());

            DataManager.getDataManager().verifyEmail(getEmailParams(account.getEmail()), new RetrofitCallBack<Auth>() {
                @Override
                public void Success(Auth data) {
                    if (data == null)
                        return;
                    Session.setAccessToken(data.getAccessToken());
                    Session.setName(data.getName());
                    Session.setDesignation(data.getDesignation());
                    Session.setEmail(data.getEmail());
                    Session.setUserVerification(true);
                    DataManager.getDataManager().init(LoginActivity.this);
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    firebaseAuthWithGoogle(account);
                }

                @Override
                public void Failure(String error) {
                    Toast.makeText(LoginActivity.this, "Cant find teacher", Toast.LENGTH_SHORT).show();
                    googleSignInClient.signOut().addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("LoginActivity", "Login failed");
                        }
                    });

                }
            });

            // TODO send the email address to the server and verify the user then only call the firebase auth function
//
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                @Override
                                public void onSuccess(GetTokenResult result) {
                                    String idToken = result.getToken();
                                }
                            });
                            Log.d(TAG, "signInWithCredential:success" );
                            startActivity(new Intent(getApplication(), MainActivity.class));
                            finish();
    
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Something went wrong plese try again", Toast.LENGTH_LONG).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }
}
