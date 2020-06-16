package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.example.navigationdrawerfromscratch.lebensmittel.FoodCategory;
import com.example.navigationdrawerfromscratch.lebensmittel.Gemuese;
import com.example.navigationdrawerfromscratch.lebensmittel.Getreideprodukte;
import com.example.navigationdrawerfromscratch.lebensmittel.Gewuerze;
import com.example.navigationdrawerfromscratch.lebensmittel.Milchprodukte;
import com.example.navigationdrawerfromscratch.lebensmittel.Drinks;
import com.example.navigationdrawerfromscratch.lebensmittel.Obst;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateRecipeFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    View view;
    ArrayList<Zutat> ingredientsList;
    RecyclerView ingredients;
    RecyclerView.Adapter adapter;
    Context context;
    EditText editTextName;
    EditText editTextPreparationTime;
    Spinner spinnerCategory;
    EditText editTextInstruction;
    Button btncreateRecipe;
    ImageView addIngredient;
    DatabaseReference databaseRecipe;
    String categoryString;
    EditText editTextPortions;
    EditText editTextAmount;
    TextView TextViewSwitchToFoodSelection;
    TextView textViewChangeIngredient;
    TextView showAllIngredients;
    Recipe recipe;

    public static List<Food> zutatenList = new ArrayList<>();
    public static HashMap<String, String> ingredientsMap = new HashMap<>();

    public static String foodName = "Zutat";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        context = this.getActivity();

        //Initalisieren aller Elemente
        editTextName = (EditText) view.findViewById(R.id.editTextRecipeName);
        editTextPreparationTime = (EditText) view.findViewById(R.id.editTextPreparationTime);
        editTextInstruction = (EditText) view.findViewById(R.id.editTextInstruction);
        editTextPortions = (EditText) view.findViewById(R.id.editTextPortionen);
        editTextAmount = (EditText) view.findViewById(R.id.editTextAmount);

        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.category_arrays, android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);

        btncreateRecipe = (Button) view.findViewById(R.id.buttonCreateRecipe);

        TextViewSwitchToFoodSelection = (TextView) view.findViewById(R.id.textViewFoodSelection);
        textViewChangeIngredient = (TextView) view.findViewById(R.id.textViewChangeIngredient);
        showAllIngredients = (TextView) view.findViewById(R.id.editTextIngredientsMap);

        addIngredient = (ImageView) view.findViewById(R.id.ImageViewAddIngredient);

        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        if (!(ingredientsMap.isEmpty())){
            for (int i = 0; i < ingredientsMap.size(); i++) {
                showAllIngredients.setText(ingredientsMap.toString());
            }
        }


        btncreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

        TextViewSwitchToFoodSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gemuese.vonWoher = "CreateRecipe";
                Getreideprodukte.vonWoher = "CreateRecipe";
                Gewuerze.vonWoher = "CreateRecipe";
                Milchprodukte.vonWoher = "CreateRecipe";
                Drinks.vonWoher = "CreateRecipe";
                Obst.vonWoher = "CreateRecipe";
                FoodCategory foodCategory = new FoodCategory();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, foodCategory, foodCategory.getTag()).addToBackStack(null).commit();
            }
        });

        textViewChangeIngredient.setText(foodName);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });

        return view;
    }

    public void addIngredient() {

        if (editTextAmount.getText().toString().trim().contains("/") == true) {
            Toast.makeText(getContext(), "Menge darf nicht '/' enthalten", Toast.LENGTH_LONG).show();
        } else if (editTextAmount.getText().toString().trim().contains("/") == false){
            ingredientsMap.put(editTextAmount.getText().toString().trim(), textViewChangeIngredient.getText().toString().trim());
        }

        for (int i = 0; i < ingredientsMap.size(); i++) {
            showAllIngredients.setText(ingredientsMap.toString());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (spinnerCategory.getItemAtPosition(position).toString()) {
            case ("Kategorie auswählen"):
                categoryString = "Kategorie auswählen";
                break;
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
        String instruction = editTextInstruction.getText().toString().trim();
        String rPortions = editTextPortions.getText().toString().trim();
        String creator = AccountFragment.usernameString;
        String category = null;
        if (categoryString.equals("Kategorie auswählen")){
            Toast.makeText(context, "Bitte Kategorie auswählen", Toast.LENGTH_LONG).show();
        } else {
            category = categoryString;
            final Recipe recipe = new Recipe(rId, rName, preparationTime, ingredientsMap, instruction, category, null, 0, rPortions, creator);
            final Context context = this.getActivity();

            databaseRecipe.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    databaseRecipe.child(recipe.getRecipeId()).setValue(recipe);
                    Toast.makeText(context, "Rezept wurde erfolgreich angelegt", Toast.LENGTH_LONG).show();

                    MyRecipesFragment myRecipesFragment = new MyRecipesFragment();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.fragment_container, myRecipesFragment, myRecipesFragment.getTag()).addToBackStack(null).commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }
}