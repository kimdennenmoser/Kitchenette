package com.example.navigationdrawerfromscratch;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountFragment extends Fragment {

    @Nullable


    EditText editTextVorname;
    EditText editTextNachname;
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextMail;
    Button btncreateUser;
    DatabaseReference databaseUser;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        //Datenbank Zugriff
        editTextVorname = (EditText) view.findViewById(R.id.editTextVorname);
        editTextNachname = (EditText) view.findViewById(R.id.editTextNachname);
        editTextUsername = (EditText) view.findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextMail = (EditText) view.findViewById(R.id.editTextMail);
        btncreateUser = (Button) view.findViewById(R.id.buttonCreateUser);

        databaseUser = FirebaseDatabase.getInstance().getReference().child("");
        //databaseUser = FirebaseDatabase.getInstance().getReference("User");
        btncreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        return view;
    }

    //Methode, die einen neuen User in die DB hinzufügt
    public void addUser() {

        String firstName = editTextVorname.getText().toString().trim();
        String lastName = editTextNachname.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String mail = editTextMail.getText().toString().trim();
        
        Context context = this.getActivity();

        if (!TextUtils.isEmpty(firstName)) {

            String id = databaseUser.push().getKey();
            User user  = new User(id, firstName, lastName, username, password, mail);
            databaseUser.child(id).setValue(user);

            Toast.makeText(context, "User wurde erfolgreich angelegt", Toast.LENGTH_LONG).show();

        } else{
            Toast.makeText(context, "Bitte Daten ausfüllen", Toast.LENGTH_LONG).show();
            
        }
        /*user = new User(id, firstName, lastName, username, password, mail);
        databaseUser.push().setValue(user);
        //  databaseUser.child(id).setValue(user);
        Context context = getActivity().getApplicationContext();
        Toast.makeText(context, "Benutzer angelegt", Toast.LENGTH_LONG).show();*/


    }

}
