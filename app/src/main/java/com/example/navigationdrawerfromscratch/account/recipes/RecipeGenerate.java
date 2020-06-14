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

    public RecyclerView recyclerViewIngredient;
    public ProductAdapter productAdapter;
    public static List<Food> productList = new ArrayList<>();

    Button btnAddIngredient;
    Button btnStartSearch;
    DatabaseReference databaseFood;
    DatabaseReference databaseRecipes;
    List<Food> liste = new ArrayList<>();
    boolean etwasFehlt = false;
    public static boolean addIngredient = false;
    public static String foodName = null;
    List<Recipe> recipeList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

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

                FoodCategory foodCategory = new FoodCategory();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, foodCategory, foodCategory.getTag()).addToBackStack(null).commit();
            }
        });

        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeSearch();

            }
        });




        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Context context = this.getContext();
        productAdapter = new ProductAdapter(context, productList, this);


        try{
            Log.i("Produkte", productList.get(0).getName());
        }catch(Exception e){
            Log.i("Ex", "Liste ist leer");
        }

        try {
            System.out.println(productAdapter.getItemCount());
        }catch (Exception p){
            Log.i("ExAdap", "Adapter übernimmt nicht");
        }


        recyclerViewIngredient.setAdapter(productAdapter);

    }

    public void recipeSearch(){

        System.out.println("RezepteListe 1: " + recipeList);

        databaseRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                recipeList.clear();

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                    System.out.println("RezepteListe 2: " + recipeList);
                }
                System.out.println("ProductList " + productList.toString());

                for (int i = 0; i < recipeList.size(); i++) {
                    HashMap<String, String> ingredientsHashMap = recipeList.get(i).getIngredientsMap();
                    System.out.println("IngredientsMap 1: " + ingredientsHashMap.toString());
                    for (String key : ingredientsHashMap.keySet()) {

                        for (int j = 0; j < productList.size(); j++) {
                            if (ingredientsHashMap.get(key).equals(productList.get(j).getName())) {
                                System.out.println("ist drin!" + productList.get(j).getName());
                            } else {
                                etwasFehlt = true;
                                System.out.println("ist nicht drin" + productList.get(j).getName());
                            }
                        }
                    }
                }
                if (etwasFehlt = true) {
                    System.out.println("passt nicht");
                } else if (etwasFehlt = false) {
                    System.out.println("passt");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("RezepteListe 4: " + recipeList); //leer

    }




    @Override
    public void onFoodClick(int position) {
        Toast.makeText(getView().getContext(), "Wurde geklickt!", Toast.LENGTH_LONG).show();
    }


    public class FoodViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}
