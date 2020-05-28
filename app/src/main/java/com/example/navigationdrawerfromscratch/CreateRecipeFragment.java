package com.example.navigationdrawerfromscratch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateRecipeFragment extends Fragment {


    View view;
    ArrayList<Zutat> ingredientsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_create_recipe, container, false);
        ;

        //Initalisieren aller Elemente
/*
        editTextFirstName = (EditText) view.findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) view.findViewById(R.id.editTextLastName);
        editTextUsername = (EditText) view.findViewById(R.id.editTextCreateUsername);
        editTextPassword = (EditText) view.findViewById(R.id.editTextCreatePassword);
        editTextMail = (EditText) view.findViewById(R.id.editTextMail);
        btncreateUser = (Button) view.findViewById(R.id.buttonCreateUser);
        databaseUser = FirebaseDatabase.getInstance().getReference("Rezepte");

        btncreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

 */

        return view;
    }

    private ArrayList<Zutat> ingredientsList() {
        ArrayList<Zutat> list = new ArrayList<>();

        list.add(new Zutat("200", "g", "Rote Bete"));
        list.add(new Zutat("100", "g", "Feta"));
        list.add(new Zutat("50", "g", "Walnüsse"));

        return list;
    }

    //Methode, die einen neuen User in die DB hinzufügt
    public void addRecipe() {

        //Initalisieren aller Elemente
        /*String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String mail = editTextMail.getText().toString().trim();

        final User user = new User (firstName, lastName, username, password, mail);



        final Context context = this.getActivity();
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

         */
    }

}