package com.example.navigationdrawerfromscratch.account.recipes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.adapters.RecipeAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeResultFragment extends Fragment implements RecipeAdapter.OnRecipeListener, AdapterView.OnItemSelectedListener {

    View view;
    RecyclerView recyclerView;
    public static List<Recipe> recipeList = new ArrayList<>();
    RecipeAdapter adapter;
    DatabaseReference databaseRecipe;
    EditText editSearch;
    Spinner spinner;
    public String text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_result, container, false);

        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        spinner = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.category_arrays, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewRecipeResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        editSearch = (EditText) view.findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        RecipeGenerate.resultsDisplayed = true;


        return view;
    }

    private void filter(String text){
        ArrayList<Recipe> filteredList = new ArrayList<>();

        for(Recipe recipe : recipeList){
            if(recipe.getRecipeName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }else if (recipe.getCategory().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new RecipeAdapter(getView().getContext(), recipeList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (spinner.getItemAtPosition(position).toString()) {
            case ("Kategorie"):
                text="";
                filter(text);
                break;
            case ("Vorspeise"):
                text = "Vorspeise";
                filter(text);
                break;
            case ("Getränk"):
                text = "Getränk";
                filter(text);
                break;
            case ("Hauptgericht"):
                text = "Hauptgericht";
                filter(text);
                break;
            case ("Dessert"):
                text = "Dessert";
                filter(text);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onRecipeClick(int position) {
        RecipeInstruction.shoppingList = "selective";
        RecipeInstruction recipeInstruction = new RecipeInstruction();
        FragmentManager recipeManager = getFragmentManager();
        recipeManager.beginTransaction().replace(R.id.fragment_container, recipeInstruction, recipeInstruction.getTag()).addToBackStack(null).commit();
        RecipeInstruction.recipeString = recipeList.get(position).getRecipeId();

    }
}
