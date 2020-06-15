package com.example.navigationdrawerfromscratch.account;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteAllIngredientsFromShoppingListPopUpFragment extends DialogFragment {

    View view;
    Button btnDeleteIngredient;
    Button btnDeleteIngredientAbbruch;
    DatabaseReference databaseUser;
    public static String foodName = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_delete_all_ingredients_popup, container, false);

        btnDeleteIngredient = (Button) view.findViewById(R.id.buttonDeleteAllIngredients);
        btnDeleteIngredientAbbruch = (Button) view.findViewById(R.id.buttonDeleteAllIngredientsAbbruch);
        databaseUser = FirebaseDatabase.getInstance().getReference("User").child(AccountFragment.usernameString);

        btnDeleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user.getShoppingList() != null){
                            user.setShoppingList(null);
                            databaseUser.setValue(user);
                            Toast.makeText(getContext(), "Erfolgreich geleert", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Bereits leer", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnDeleteIngredientAbbruch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        onResume();
        onStop();

        return view;

    }

    //Ausblenden der Toolbar
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
