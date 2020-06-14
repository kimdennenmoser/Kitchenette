package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.adapters.RecipeAdapter;
import com.example.navigationdrawerfromscratch.lebensmittel.FoodCategory;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.example.navigationdrawerfromscratch.lebensmittel.Gemuese;
import com.example.navigationdrawerfromscratch.lebensmittel.Getreideprodukte;
import com.example.navigationdrawerfromscratch.lebensmittel.Gewuerze;
import com.example.navigationdrawerfromscratch.lebensmittel.Milchprodukte;
import com.example.navigationdrawerfromscratch.lebensmittel.Nuts;
import com.example.navigationdrawerfromscratch.lebensmittel.Obst;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeGenerate extends Fragment implements ProductAdapter.OnNoteListener {

    Button btnAddIngredient;
    Button btnStartSearch;
    RecyclerView recyclerViewIngredient;
    private ProductAdapter productAdapter;
    DatabaseReference databaseFood;
    DatabaseReference databaseRecipes;
    List<Food> liste = new ArrayList<>();

    public static List<Food> productList = new ArrayList<>();
    public static String foodName = null;
    public static boolean resultsDisplayed = false;
    List<Recipe> recipeList = new ArrayList<>();
    List<String> enthalteneZutaten = new ArrayList<>();
    List<String> productListNameString = new ArrayList<>();
    List<Food> selectedIngredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerViewIngredient = (RecyclerView) view.findViewById(R.id.recyclerViewIngredient);
        recyclerViewIngredient.setHasFixedSize(true);
        recyclerViewIngredient.setLayoutManager(new LinearLayoutManager(view.getContext()));


        btnAddIngredient = (Button) view.findViewById(R.id.btnAddIngredient);
        btnStartSearch = (Button) view.findViewById(R.id.btnStartSearch);
        databaseFood = FirebaseDatabase.getInstance().getReference("Lebensmittel");
        databaseRecipes = FirebaseDatabase.getInstance().getReference("Rezepte");


        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gemuese.vonWoher = "Search";
                Getreideprodukte.vonWoher = "Search";
                Gewuerze.vonWoher = "Search";
                Milchprodukte.vonWoher = "Search";
                Nuts.vonWoher = "Search";
                Obst.vonWoher = "Search";

                resultsDisplayed = false;

                FoodCategory foodCategory = new FoodCategory();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, foodCategory, foodCategory.getTag()).addToBackStack(null).commit();
            }
        });

        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeResultFragment.recipeList.clear();

                databaseRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        recipeList.clear();

                        for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                            recipeList.clear();
                            enthalteneZutaten.clear();
                            Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                            recipeList.add(recipe);
                            List<String> zutaten = new ArrayList<>();
                            System.out.println("RezepteListe 2: " + recipeList);
                            for (int i = 0; i < recipeList.size(); i++) {
                                zutaten.clear();
                                HashMap<String, String> ingredientsHashMap = recipeList.get(i).getIngredientsMap();
                                System.out.println("IngredientsMap 1: " + ingredientsHashMap.toString());
                                for (String key : ingredientsHashMap.keySet()) {
                                    zutaten.add(ingredientsHashMap.get(key));
                                }
                                System.out.println("Zutaten:" + zutaten.toString());


                                for (int k = 0; k < productList.size(); k++) {
                                    for (int j = 0; j < zutaten.size(); j++) {
                                        if (zutaten.get(j).equals(productList.get(k).getName())) {
                                            System.out.println("geklappt: " + productList.get(k).getName());
                                            enthalteneZutaten.add(productList.get(k).getName());
                                        }
                                    }
                                }
                                productListNameString.clear();
                                for (int z = 0; z < productList.size(); z++) {
                                    productListNameString.add(productList.get(z).getName());
                                }
                                System.out.println("enthalten: " + enthalteneZutaten.toString());
                                System.out.println("productlist " + productListNameString.toString());
                                if (enthalteneZutaten.equals(productListNameString)) {
                                    RecipeResultFragment.recipeList.add(recipe);

                                    RecipeResultFragment recipeResultFragment = new RecipeResultFragment();
                                    FragmentManager manager = getFragmentManager();
                                    manager.beginTransaction().replace(R.id.fragment_container, recipeResultFragment, recipeResultFragment.getTag()).addToBackStack(null).commit();
                                    System.out.println("Final geklappt");
                                    System.out.println("passendes Rezept:" + recipe.toString());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        productAdapter = new ProductAdapter(getContext(), productList, this);
        try {
            System.out.println(productAdapter.getItemCount());
        }
        catch(Exception e){
            Log.i("ExAdapter", "Adapter ist leer");
        }

        recyclerViewIngredient.setAdapter(productAdapter);
        if (resultsDisplayed = true) {
            recipeList.clear();
        }
    }

        @Override
        public void onFoodClick ( int position){
            //Toast.makeText(getView().getContext(), "Wurde geklickt!", Toast.LENGTH_LONG).show();
        }


        public class FoodViewHolder extends RecyclerView.ViewHolder {
            View mView;

            public FoodViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
            }
        }
    }
