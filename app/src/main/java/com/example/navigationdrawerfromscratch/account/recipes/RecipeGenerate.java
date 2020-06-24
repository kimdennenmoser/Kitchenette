package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.account.User;
import com.example.navigationdrawerfromscratch.lebensmittel.FoodCategory;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
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

public class RecipeGenerate extends Fragment implements ProductAdapter.OnNoteListener {

    Button btnAddIngredient;
    Button btnStartSearch;
    RecyclerView recyclerViewIngredient;
    ProductAdapter adapter;
    DatabaseReference databaseRecipes;
    DatabaseReference databaseUser;

    TextView showIngredients;
    public static ArrayList<Food> productList = new ArrayList<>();
    public static String foodName = null;
    public static boolean resultsDisplayed = false;
    public static List<Recipe> recipeList = new ArrayList<>();
    public static List<String> enthalteneZutaten = new ArrayList<>();
    public static List<String> productListNameString = new ArrayList<>();
    List<String> userAllergies = new ArrayList<>();
    public static Boolean allergicToRecipe = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_search, container, false);


        View view = inflater.inflate(R.layout.fragment_search_alternative, container, false);

        /*recyclerViewIngredient = (RecyclerView) view.findViewById(R.id.recyclerViewIngredient);
        recyclerViewIngredient.setHasFixedSize(true);
        recyclerViewIngredient.setLayoutManager(new LinearLayoutManager(view.getContext()));

        try {
            Log.i("DEBUG", adapter.toString());
        }catch(Exception e){
            System.out.println("Adapter ist noch leer");
        }



        btnAddIngredient = (Button) view.findViewById(R.id.btnAddIngredient);
        btnStartSearch = (Button) view.findViewById(R.id.btnStartSearch);*/


        btnAddIngredient = (Button) view.findViewById(R.id.btnAddIngredientAlternative);
        btnStartSearch = (Button) view.findViewById(R.id.btnStartSearchAlternative);
        showIngredients = (TextView) view.findViewById(R.id.textViewShowSelectedIngredients);

        databaseRecipes = FirebaseDatabase.getInstance().getReference("Rezepte");

        databaseUser = FirebaseDatabase.getInstance().getReference("User");


        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gemuese.vonWoher = "Search";
                Getreideprodukte.vonWoher = "Search";
                Gewuerze.vonWoher = "Search";
                Milchprodukte.vonWoher = "Search";
                Drinks.vonWoher = "Search";
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
                startSearch();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), productList, this);
        if (resultsDisplayed = true) {
            recipeList.clear();
        }

        productListNameString.clear();
        for (int z = 0; z < productList.size(); z++) {
            productListNameString.add(productList.get(z).getName());
        }
        showIngredients.setText(productListNameString.toString());
    }

    private void startSearch() {
        RecipeResultFragment.recipeList.clear();

        databaseRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    recipeList.clear();
                    enthalteneZutaten.clear();
                    final Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                    final List<String> zutaten = new ArrayList<>();
                    for (int i = 0; i < recipeList.size(); i++) {
                        zutaten.clear();
                        HashMap<String, String> ingredientsHashMap = recipeList.get(i).getIngredientsMap();
                        for (String key : ingredientsHashMap.keySet()) {
                            zutaten.add(ingredientsHashMap.get(key)); //Ananas, Erdbeere, Orange, Pistazie
                        }
                        //User Allergien abfragen
                        if (MainActivity.isAngemeldet == true) {
                            databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                                    for (int i = 0; i < user.getAllergies().size(); i++) {
                                        userAllergies.add(user.getAllergies().get(i));
                                    }
                                    for (int j = 0; j < userAllergies.size(); j++) {
                                        if (zutaten.contains(userAllergies.get(j))) {
                                           allergicToRecipe = true;
                                        } else{
                                            allergicToRecipe = false;
                                        }
                                    }
                                    for (int k = 0; k < productList.size(); k++) {
                                        for (int j = 0; j < zutaten.size(); j++) {
                                            if (zutaten.get(j).equals(productList.get(k).getName())) {
                                                enthalteneZutaten.add(productList.get(k).getName()); //eingegebene(productList) in Zutaten enthalten
                                            }
                                        }
                                    }
                                    productListNameString.clear();
                                    for (int z = 0; z < productList.size(); z++) {
                                        productListNameString.add(productList.get(z).getName());
                                    }

                                    if (enthalteneZutaten.equals(productListNameString)) {
                                        if (allergicToRecipe == false) {
                                            RecipeResultFragment.recipeList.add(recipe);
                                        }
                                        RecipeResultFragment recipeResultFragment = new RecipeResultFragment();
                                        FragmentManager manager = getFragmentManager();
                                        manager.beginTransaction().replace(R.id.fragment_container, recipeResultFragment, recipeResultFragment.getTag()).addToBackStack(null).commit();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onFoodClick(int position) {

    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
    }

}
