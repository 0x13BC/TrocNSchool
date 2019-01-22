package fr.ydays.trocynov.trocnshcool.vue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.ydays.trocynov.trocnshcool.R;

public class ProfilActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mTextMessage = (TextView) findViewById(R.id.message);
        Button disconnectButton = (Button) findViewById(R.id.disconnect_button);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
                jumpToLogin();
            }
        });

    }

    private void jumpToLogin() {
        Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);////////
        startActivity(intent);
        finish();
    }

    void disconnect() {
        SharedPreferences sharedPref = PreferenceManager.
                getDefaultSharedPreferences(
                        ProfilActivity.this.getApplication());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.auth_email), "");
        editor.putString(getString(R.string.auth_token), "");
        editor.commit();

    }
}