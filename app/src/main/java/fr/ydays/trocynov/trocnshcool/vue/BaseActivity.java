package fr.ydays.trocynov.trocnshcool.vue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import fr.ydays.trocynov.trocnshcool.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private void Register(){
        Intent i = new Intent();
        i.setClass(this, RegisterActivity.class);
        startActivity(i);
    }
    boolean isLogin(){
        String token = getLoginToken();
        if(token == null || token.isEmpty()){
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
    void sendMessageToCloudFunction(HttpUrl.Builder httpBuider) {

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().
                url(httpBuider.build()).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "error response firebase cloud function");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseActivity.this,
                                "Action failed please try gain.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody responseBody = response.body();
                String resp = "";
                if (!response.isSuccessful()) {
                    Log.e(TAG, "action failed");
                    resp = "Failed perform the action, please try again";
                } else {
                    try {
                        resp = responseBody.string();
                        Log.e(TAG, "Response " + resp);
                    } catch (IOException e) {
                        resp = "Problem in reading response";
                        Log.e(TAG, "Problem in reading response " + e);
                    }
                }
                runOnUiThread(responseRunnable(resp));
            }
        });
    }
    abstract  Runnable responseRunnable(final String responseStr);
}
