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
    private Button AvisButton, HistoricButton,ParameterButton,InfoButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile,container,false);

        AvisButton = (Button) view.findViewById(R.id.avis_button);
        AvisButton.setOnClickListener(this);

        HistoricButton = (Button) view.findViewById(R.id.historic_button);
        HistoricButton.setOnClickListener(this);

        ParameterButton = (Button) view.findViewById(R.id.parameter_button);
        ParameterButton.setOnClickListener(this);

        InfoButton = (Button) view.findViewById(R.id.info_sup_button);
        InfoButton.setOnClickListener(this);

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
            case R.id.avis_button:

                fragment = new AvisFragment();
                replaceFragment(fragment);
                break;

            case R.id.historic_button:
                fragment = new HistoricFragment();
                replaceFragment(fragment);
                break;

            case R.id.parameter_button:
                fragment = new ParameterFragment();
                replaceFragment(fragment);
                break;

            case R.id.info_sup_button:
                fragment = new InfoSupFragment();
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
