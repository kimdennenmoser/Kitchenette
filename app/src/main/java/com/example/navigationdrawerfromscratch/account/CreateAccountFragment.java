package com.example.navigationdrawerfromscratch.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountFragment extends Fragment {

    @Nullable


    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextMail;
    Button btncreateUser;
    DatabaseReference databaseUser;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        //Initalisieren aller Elemente
        editTextFirstName = (EditText) view.findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) view.findViewById(R.id.editTextLastName);
        editTextUsername = (EditText) view.findViewById(R.id.editTextCreateUsername);
        editTextPassword = (EditText) view.findViewById(R.id.editTextCreatePassword);
        editTextMail = (EditText) view.findViewById(R.id.editTextMail);
        btncreateUser = (Button) view.findViewById(R.id.buttonCreateUser);
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

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

        //Initalisieren aller Elemente
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String mail = editTextMail.getText().toString().trim();
        final Context context = this.getActivity();
        final User user = new User (firstName, lastName, username, password, mail);

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUsername()).exists()){
                    Toast.makeText(context, "Username existiert bereits", Toast.LENGTH_LONG).show();
                }
                else{
                    databaseUser.child(user.getUsername()).setValue(user); //wird als Kind des Knoten "User" angelegt
                    Toast.makeText(context, "User wurde erfolgreich angelegt", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
