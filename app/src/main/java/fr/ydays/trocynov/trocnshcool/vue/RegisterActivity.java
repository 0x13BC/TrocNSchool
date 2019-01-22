package fr.ydays.trocynov.trocnshcool.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.ydays.trocynov.trocnshcool.R;
import fr.ydays.trocynov.trocnshcool.modele.Users;


public class RegisterActivity extends AppCompatActivity {


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
                AddUser();
            }
        });

    }

    public void AddUser() {
        String Nom = editTextNom.getText().toString().trim();
        String Prenom = editTextPrenom.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        String ConfPassword = editTextConfPassword.getText().toString().trim();
        if ((Password.equals(ConfPassword)) && (Password.length()>6) ){
            String id = databaseUsers.push().getKey();
            Users user = new Users(id, Nom, Prenom, Email, Password);
            databaseUsers.child(id).setValue(user);
            Toast.makeText(RegisterActivity.this,"Enregistrement du compte",Toast.LENGTH_LONG).show();
            //Intent intent= new Intent(this,DonEtDemandeActivity);



        }
        else if (Password.equals(ConfPassword))
            Toast.makeText(RegisterActivity.this,"Mot de passe trop court! Minimum 7 caracteres",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(RegisterActivity.this,"Mot de passe différent",Toast.LENGTH_LONG).show();
}
}