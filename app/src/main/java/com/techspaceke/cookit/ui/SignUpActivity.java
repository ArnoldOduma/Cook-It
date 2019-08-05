package com.techspaceke.cookit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techspaceke.cookit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SignUpActivity extends AppCompatActivity{
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private FirebaseAuth mAuth;

    @BindView(R.id.accountLogin_SignUpTextView)TextView haveAccountTextview;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
//    @BindView(R.id.signUpButtonTextView) TextView mSignUpButtonTextView;
    @BindView(R.id.signUpButtonTextView) TextView mSignUpButtonTextView;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;

    Fragment fragmentLogIn = new LogInFragment();
    FragmentManager fm = getSupportFragmentManager();
    TextView b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        hideProgressDialog();

        //initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
    }

    //Check user on start
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    public void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                           WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    public void hideProgressDialog(){
        mProgressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void createAccount(String email, String password) {
        if (!validateForm()){
            return;
        }
        showProgressDialog();
        //CREATE USER WITH EMAIL  AND PASSWORD
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.e(TAG, "Creating user with email successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else {
                            Log.e(TAG, task.getException().getLocalizedMessage());
                            Toast.makeText(SignUpActivity.this, "Authentication faiilure !!!" + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.e(TAG, "Sign in :"+ email);
        if (!validateForm()){
            return;
        }

        //START SIGN IN WITH EMAIL
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "Sign in with email successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else {
                            Log.e(TAG, "Sign in with email failed !!!!!!!!!!!!!");
                            Toast.makeText(SignUpActivity.this, "Authentication failure !!!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void sendEmailVerification(){
        // Disable button
//        findViewById(R.id.verifyEmailButton).setEnabled(false);

        //SEND VERIFICATION EMAIL
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //enable button
//                        findViewById(R.id.verifyEmailButton).setEnabled(true);
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(SignUpActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(){
        ButterKnife.bind(this);
        boolean valid = true;
        String email = mEmailEditText.getText().toString();
        if (TextUtils.isEmpty(email)){
            mEmailEditText.setError("Required");
            valid = false;
        }else {
             mEmailEditText.setError(null);
        }

        String password = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)){
            mPasswordEditText.setError("Required");
            valid = false;
        }else if (mPasswordEditText.getText().length() < 6){
            mPasswordEditText.setError("Password should be at least 6 characters long.");
            valid = false;
        }else if(!(mPasswordEditText.getText().toString().matches(mConfirmPasswordEditText.getText().toString())) ){
//            mPasswordEditText.setError("The two password do not match.");
            mConfirmPasswordEditText.setError("The two password do not match.");
            mConfirmPasswordEditText.findFocus();
            valid = false;
        } else{
            mPasswordEditText.setError(null);
        }
        return valid;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null){
            Log.e(TAG,"Congrats !!!!!" + user.getEmail());
             Log.e(TAG, user.getUid());
             Toast.makeText(SignUpActivity.this,
                                    "user already logged in " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
             Log.e(TAG,"COULD NOT AUTHENTICATE !!!!!!!!");
        }
    }


    public void logIn(View view){
        ButterKnife.bind(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            if (fragmentLogIn.isAdded()){
                mSignUpButtonTextView.setVisibility(View.VISIBLE);
                transaction.remove(fragmentLogIn);
                haveAccountTextview.setText("Already have an account? Log In");
                Log.e("ON LOGIN", "showing");
            }else {
                mSignUpButtonTextView.setVisibility(View.INVISIBLE);
                transaction.add(R.id.signUp_fragment_container, fragmentLogIn);
                haveAccountTextview.setText("Don't have an account? Sign Up");
            }
        transaction.commit();
    }

    public void authenticate(View v){
        ButterKnife.bind(this);
        createAccount(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        Toast.makeText(SignUpActivity.this,
                                    "Authenticating"+mEmailEditText.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
//        sendEmailVerification();
    }

    public void loginUser(View view){
        Toast.makeText(SignUpActivity.this,
                                    "Logging in"+mEmailEditText.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
        // your code
        Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
            startActivity(intent);
        return true;
    }

        return super.onKeyDown(keyCode, event);
    }

}
