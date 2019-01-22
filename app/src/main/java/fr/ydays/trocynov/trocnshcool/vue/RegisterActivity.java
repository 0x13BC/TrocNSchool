package fr.ydays.trocynov.trocnshcool.vue;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import fr.ydays.trocynov.trocnshcool.R;
import fr.ydays.trocynov.trocnshcool.modele.Users;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";

    private String FIREBASE_CLOUD_FUNCTION_REG_URL="https://us-central1-trocynovbackend.cloudfunctions.net/register";
    private String Email;
    // Write a message to the database
    EditText editTextNom;
    EditText editTextPrenom;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfPassword;
    DatabaseReference databaseUsers;
    Button buttonAddUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfPassword = (EditText) findViewById(R.id.editTextConfPassword);
        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });

    }

    private void registration() {

        String Nom = editTextNom.getText().toString().trim();
        String Prenom = editTextPrenom.getText().toString().trim();
        Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        String ConfPassword = editTextConfPassword.getText().toString().trim();
        if ((Password.equals(ConfPassword)) && (Password.length() > 6)) {

            HttpUrl.Builder httpBuider = prepareRegRequestBuilder(Nom, Prenom, Email, Password);
            sendMessageToCloudFunction(httpBuider);
        }
    }

    Runnable responseRunnable(final String responseStr) {
        Runnable resRunnable = new Runnable() {
            public void run() {
                Log.d(TAG, responseStr);
                //login success
                if(responseStr.contains("account created")){
                    Toast.makeText(RegisterActivity.this,
                            "Account created, login now.",
                            Toast.LENGTH_SHORT).show();
                    finish();
/////////////////////////ADD SUCCESS
                }else {
                    Toast.makeText(RegisterActivity.this,
                            responseStr,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        return resRunnable;
    }

    private HttpUrl.Builder prepareRegRequestBuilder(String Nom, String Prenom, String Email, String Password) {
        HttpUrl.Builder httpBuider =
                HttpUrl.parse(FIREBASE_CLOUD_FUNCTION_REG_URL).newBuilder();
        httpBuider.addQueryParameter("name", Nom);
        httpBuider.addQueryParameter("firstName", Prenom);
        httpBuider.addQueryParameter("email", Email);
        httpBuider.addQueryParameter("password", Password);
        Log.e("TAG", httpBuider.toString());
        return httpBuider;
    }

}