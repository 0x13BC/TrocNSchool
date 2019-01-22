package fr.ydays.trocynov.trocnshcool.vue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.ydays.trocynov.trocnshcool.R;
import okhttp3.HttpUrl;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{

    private static final String TAG = "LoginActivity";

    private String FIREBASE_CLOUD_FUNCTION_LOGIN_URL="https://us-central1-trocynovbackend.cloudfunctions.net/login ";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(isLogin()){
            startMainActivity();
        }


        setContentView(R.layout.activity_login);
        // Set up the login form.

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button registerButton = (Button) findViewById(R.id.register_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToRegister();
            }
        });


    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Store values at the time of the login attempt.
        Email = mEmailView.getText().toString();
        String Password = mPasswordView.getText().toString();


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(Password) || !isPasswordValid(Password)) {
            Toast.makeText(LoginActivity.this,
                    getString(R.string.error_invalid_password),
                    Toast.LENGTH_SHORT).show();
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(Email) || !isEmailValid(Email)) {
            Toast.makeText(LoginActivity.this,
                    getString(R.string.error_field_required),
                    Toast.LENGTH_SHORT).show();
        }



            HttpUrl.Builder httpBuider = prepareRegRequestBuilder(Email, Password);
            sendMessageToCloudFunction(httpBuider);
    }


    private void jumpToRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);////////
        startActivity(intent);

    }

    private void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, ProfilActivity.class);////////
        startActivity(intent);
        finish();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    boolean isLogin(){
        String token = getLoginToken();
        if(token == null || token.isEmpty() || token.equals("0")){
            return false;
        }else{
            return true;
        }
    }
    String getLoginToken(){

        SharedPreferences sharedPref = PreferenceManager.
                getDefaultSharedPreferences(this.getApplication());

        String token = sharedPref.getString(getString(R.string.auth_token), null);
        return token;
    }


    //FIREBASE

    Runnable responseRunnable(final String responseStr) {
        Runnable resRunnable = new Runnable() {
            public void run() {
                Log.d(TAG, responseStr);
                //login success
                if(responseStr.contains("token")){
                    //retrieve token from response and save it in shared preference
                    //so that token can be sent in the request to services

                    String tokenStr[] = responseStr.split(":");
                    Log.d(TAG, tokenStr[1]);
                    sharedPref = PreferenceManager.
                            getDefaultSharedPreferences(
                                    LoginActivity.this.getApplication());

                    editor = sharedPref.edit();
                    editor.putString(getString(R.string.auth_email), Email);
                    editor.putString(getString(R.string.auth_token), tokenStr[1]);
                    editor.commit();

                    Toast.makeText(LoginActivity.this,
                            "Login Successful.",
                            Toast.LENGTH_SHORT).show();

                    startMainActivity();
                }else {
                    Toast.makeText(LoginActivity.this,
                            responseStr,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        return resRunnable;
    }

    private HttpUrl.Builder prepareRegRequestBuilder(String Email, String Password) {
        HttpUrl.Builder httpBuider =
                HttpUrl.parse(FIREBASE_CLOUD_FUNCTION_LOGIN_URL).newBuilder();
        httpBuider.addQueryParameter("email", Email);
        httpBuider.addQueryParameter("password", Password);
        Log.e("TAG", httpBuider.toString());
        return httpBuider;
    }



}

