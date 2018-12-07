package fr.ydays.trocynov.trocnshcool.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.ydays.trocynov.trocnshcool.R;
import fr.ydays.trocynov.trocnshcool.modele.Users;


public class RegisterActivity extends AppCompatActivity {


    // Write a message to the database
    EditText editTextEmail;
    EditText editTextPassword;
    DatabaseReference databaseUsers;
    Button buttonAddUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });

    }

    public void AddUser() {
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

            String id = databaseUsers.push().getKey();
            Users user =new Users(id,Email,Password);
            databaseUsers.child(id).setValue(user);

    }
}
