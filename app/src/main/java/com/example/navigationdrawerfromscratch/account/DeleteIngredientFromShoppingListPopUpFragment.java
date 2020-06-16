package com.example.navigationdrawerfromscratch.account;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.ShoppingListFragment;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteIngredientFromShoppingListPopUpFragment extends DialogFragment {

    View view;
    Button btnDeleteIngredient;
    DatabaseReference databaseFood;
    DatabaseReference databaseUser;
    public static String foodName = null;
    List<String> shoppingListStrings = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_delete_ingredient_popup, container, false);

        btnDeleteIngredient = (Button) view.findViewById(R.id.buttonDeleteIngredient);
        databaseFood = FirebaseDatabase.getInstance().getReference("Lebensmittel").child(foodName);
        if (MainActivity.isAngemeldet == true) {
            databaseUser = FirebaseDatabase.getInstance().getReference("User").child(AccountFragment.usernameString);
        }

        btnDeleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //foodName ist der Name der angeklickten Zutat (zu entfernenden)
                databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Food food = dataSnapshot.getValue(Food.class); //das angeklickte
                        ShoppingListFragment.foodList.remove(food);
                        ShoppingListFragment.vonDeleteIngredientPopUp = true;

                        ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_container, shoppingListFragment, shoppingListFragment.getTag()).addToBackStack(null).commit();

                        for (int i = 0; i < ShoppingListFragment.foodList.size(); i++){
                            shoppingListStrings.add(ShoppingListFragment.foodList.get(i).getName());
                        }
                        //noch dem User die neue (verkürzte Shopping List abspeichern

                        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                user.setShoppingList(shoppingListStrings);
                                ShoppingListFragment.foodList.clear();
                                ShoppingListFragment.upToDate = false;
                                ShoppingListFragment.schonhinzugefügt = true;

                                databaseUser.setValue(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
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
