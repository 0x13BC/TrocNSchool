package fr.ydays.trocynov.trocnshcool.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.ydays.trocynov.trocnshcool.R;
import fr.ydays.trocynov.trocnshcool.vue.LoginActivity;
import fr.ydays.trocynov.trocnshcool.vue.ProfilActivity;

public class ProfileFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button disconnectButton = (Button) getView().findViewById(R.id.disconnect_button);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
                jumpToLogin();
            }
        });
        // or  (ImageView) view.findViewById(R.id.foo);
    }


    private void jumpToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    void disconnect() {
        SharedPreferences sharedPref = PreferenceManager.
                getDefaultSharedPreferences(
                        getActivity().getApplication());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.auth_email), "");
        editor.putString(getString(R.string.auth_token), "");
        editor.commit();

    }
}
