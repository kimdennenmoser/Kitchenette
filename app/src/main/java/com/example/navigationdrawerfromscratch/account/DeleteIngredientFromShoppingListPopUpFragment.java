package com.example.navigationdrawerfromscratch.account;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
    Button btnDeleteIngredientAbbruch;
    DatabaseReference databaseFood;
    public static String foodName = null;
    public static List<String> ingredientsToDelete = new ArrayList<>();
    public static List<String> foodListFromShoppingList = new ArrayList<>();
    public static List<Food> foodList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_delete_ingredient_popup, container, false);

        btnDeleteIngredient = (Button) view.findViewById(R.id.buttonDeleteIngredient);
        btnDeleteIngredientAbbruch = (Button) view.findViewById(R.id.buttonDeleteIngredientAbbruch);
        databaseFood = FirebaseDatabase.getInstance().getReference("Lebensmittel");
        foodList = ShoppingListFragment.foodList;


        btnDeleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < ShoppingListFragment.foodList.size(); i++) {
                    ingredientsToDelete.add(ShoppingListFragment.foodList.get(i).getName());
                }
                System.out.println("to delete " + ingredientsToDelete.toString());
                for (int k = 0; k < ShoppingListFragment.foodList.size(); k++) {
                    foodListFromShoppingList.add(ShoppingListFragment.foodList.get(k).getName());
                }
                System.out.println("foodList 1" + foodListFromShoppingList.toString());

                foodListFromShoppingList.remove(foodName);
                System.out.println("foodList 2" + foodListFromShoppingList.toString());

                databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            for (int o = 0; o < foodListFromShoppingList.size(); o++){
                                Food food = dataSnapshot.child(foodListFromShoppingList.get(o)).getValue(Food.class);
                                foodList.add(food);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                 ShoppingListFragment.foodList = foodList;
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
