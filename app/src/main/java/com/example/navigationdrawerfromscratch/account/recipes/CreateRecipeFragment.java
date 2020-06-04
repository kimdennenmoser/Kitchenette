package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreateRecipeFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    View view;
    ArrayList<Zutat> ingredientsList;
    RecyclerView ingredients;
    RecyclerView.Adapter adapter;
    Context context;
    EditText editTextName;
    EditText editTextPreparationTime;
    EditText editTextIngredients;
    Spinner category;
    EditText editTextInstruction;
    Button btncreateRecipe;
    DatabaseReference databaseRecipe;
    String categoryString;
    EditText rating;
    Recipe recipe;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        context = this.getActivity();

        //ArrayList<Zutat> ingredientsList = ingredientsList();

        /*ingredients = (RecyclerView) view.findViewById(R.id.ingredientsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        this.ingredients.setLayoutManager(layoutManager);

        adapter = new IngredientsAdapter(context, ingredientsList);
        this.ingredients.setAdapter(adapter);

         */

        //Initalisieren aller Elemente
        editTextName = (EditText) view.findViewById(R.id.editTextRecipeName);
        editTextPreparationTime = (EditText) view.findViewById(R.id.editTextPreparationTime);
        editTextIngredients = (EditText) view.findViewById(R.id.editTextIngredients);
        rating = (EditText) view.findViewById(R.id.editRating);
        category = (Spinner) view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.category_arrays, android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);
        editTextInstruction = (EditText) view.findViewById(R.id.editTextInstruction);
        btncreateRecipe = (Button) view.findViewById(R.id.buttonCreateRecipe);
        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        btncreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

        return view;
    }

   /* private ArrayList<Zutat> ingredientsList() {
        ArrayList<Zutat> list = new ArrayList<>();

        list.add(new Zutat("200", "g", "Rote Bete"));
        list.add(new Zutat("100", "g", "Feta"));
        list.add(new Zutat("50", "g", "Walnüsse"));

        return list;
    }

    */

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (category.getItemAtPosition(position).toString()) {
            case ("Vorspeise"):
                categoryString = "Vorspeise";
                break;
            case ("Getränk"):
                categoryString = "Getränk";
                break;
            case ("Hauptgericht"):
                categoryString = "Hauptgericht";
                break;
            case ("Dessert"):
                categoryString = "Dessert";
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Methode, die ein neues Rezept in die DB hinzufügt
    public void addRecipe() {

        //Initalisieren aller Elemente
        String rId = databaseRecipe.push().getKey();
        String rName = editTextName.getText().toString().trim();
        String preparationTime = editTextPreparationTime.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String instruction = editTextInstruction.getText().toString().trim();
        String recipeRating = rating.getText().toString().trim();
        String rImage = null;



            final Recipe recipe = new Recipe(rId, rName, ingredients, preparationTime,  categoryString, instruction,  rImage ,recipeRating);
        final Context context = this.getActivity();

        databaseRecipe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseRecipe.child(recipe.getRecipeId()).setValue(recipe); //wird als Kind des Knoten "User" angelegt
                Toast.makeText(context, "Rezept wurde erfolgreich angelegt", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}