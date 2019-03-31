package fr.ydays.trocynov.trocnshcool.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import fr.ydays.trocynov.trocnshcool.R;
import fr.ydays.trocynov.trocnshcool.vue.LoginActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener{


    private View view;
    private Fragment fragmentNext;
    private Button AvisButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile,container,false);

        AvisButton = (Button) view.findViewById(R.id.button7);

        AvisButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button disconnectButton = (Button) getView().findViewById(R.id.disconnect_button);
        Button InfoSup = (Button) getView().findViewById(R.id.info_sup_button);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
                jumpToLogin();
            }
        });



        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InfoSupFragment()).commit();
        // or  (ImageView) view.findViewById(R.id.foo);
    }


    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        FragmentTransaction fragmentManager= getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.button7:

                fragment = new AvisFragment();
                replaceFragment(fragment);
                break;

            case R.id.button6:
                fragment = new HistoricFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
