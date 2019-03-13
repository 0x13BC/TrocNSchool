package fr.ydays.trocynov.trocnshcool;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import fr.ydays.trocynov.trocnshcool.fragment.DonEtDemandeFragment;
import fr.ydays.trocynov.trocnshcool.fragment.MessagesFragment;
import fr.ydays.trocynov.trocnshcool.fragment.NotificationsFragment;
import fr.ydays.trocynov.trocnshcool.fragment.PostFragment;
import fr.ydays.trocynov.trocnshcool.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById( R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DonEtDemandeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectFragment = null;

            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    selectFragment = new DonEtDemandeFragment();
                    break;
                case R.id.navigation_profile:
                    selectFragment = new ProfileFragment();
                    break;
                case R.id.navigation_post:
                    selectFragment = new PostFragment();
                    break;
                case R.id.navigation_message:
                    selectFragment = new MessagesFragment();
                    break;
                case R.id.navigation_notification:
                    selectFragment = new NotificationsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
            return true;
        }
    };
}
