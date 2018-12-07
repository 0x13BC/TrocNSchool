package fr.ydays.trocynov.trocnshcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Utilisateur");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
