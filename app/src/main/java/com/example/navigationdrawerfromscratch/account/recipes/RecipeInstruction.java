package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.ShoppingListFragment;
import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.account.User;
import com.example.navigationdrawerfromscratch.adapters.IngredientsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeInstruction extends Fragment {

    View view;

    List<Zutat> ingredientsList = new ArrayList<>();
    TextView recipeName;
    ImageView recipeImage;
    TextView preperationTime;
    RatingBar recipeRating;
    TextView instructions;
    TextView amoutPortions;
    RecyclerView recyclerViewIngredients;
    DatabaseReference databaseRecipe;
    DatabaseReference databaseIngredients;
    DatabaseReference databaseUser;
    Button btnAddToShoppingList;
    ImageView imageViewAddToFavorites;


    public static List<String> userFavorties = new ArrayList<>();
    public static List<String> ingredients = new ArrayList<>();
    public static List<String> enthalteneZutaten = new ArrayList<>();
    public static String shoppingList = null;
    public static String recipeString;
    IngredientsAdapter adapter;
    ImageButton buttonAddToFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recipe_instruction, container, false);

        recipeName = (TextView) view.findViewById(R.id.recipeName);
        recipeImage = (ImageView) view.findViewById(R.id.imgFood);
        preperationTime = (TextView) view.findViewById(R.id.preperationTime);
        recipeRating = (RatingBar) view.findViewById(R.id.ratingBar);
        instructions = (TextView) view.findViewById(R.id.txtInstruction);
        recyclerViewIngredients = (RecyclerView) view.findViewById(R.id.recyclerViewIngredients);
        recyclerViewIngredients.setHasFixedSize(true);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte").child(recipeString);
        databaseIngredients = FirebaseDatabase.getInstance().getReference("Rezepte").child(recipeString).child("ingredientsMap");
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        amoutPortions = (TextView) view.findViewById(R.id.textViewAmountPortions);
        btnAddToShoppingList = (Button) view.findViewById(R.id.buttonAddToShoppingList);
        enthalteneZutaten = RecipeGenerate.enthalteneZutaten;


        imageViewAddToFavorites = view.findViewById(R.id.imageViewAddToFav);

        imageViewAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
            }
        });


        databaseRecipe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                recipeName.setText(recipe.getRecipeName());
                Picasso.get().load(recipe.getRecipeImage()).into(recipeImage);
                preperationTime.setText(recipe.getPreparationTime());
                recipeRating.setNumStars(recipe.getRecipeRating());
                instructions.setText(recipe.getInstructions());
                amoutPortions.setText(recipe.getPortions());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (MainActivity.isAngemeldet == true) {
                    User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                    for (int i = 0; i < user.getFavorites().size(); i++) {
                        userFavorties.add(user.getFavorites().get(i));
                    }
                    if (userFavorties.contains(recipeString)) {
                        imageViewAddToFavorites.setImageResource(R.drawable.ic_added_favorites);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAddToShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToShoppingList();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Context context = this.getContext();
        adapter = new IngredientsAdapter(context, ingredientsList);
        databaseIngredients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ingredientsList.clear();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Zutat zutat = new Zutat(recipeSnapshot.getKey(), recipeSnapshot.getValue().toString());
                    ingredientsList.add(zutat);

                    recyclerViewIngredients.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addToFavorites() {
        userFavorties.clear();
        if (MainActivity.isAngemeldet == true) {
            databaseRecipe.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                            if (user.getFavorites() != null) {
                                userFavorties = user.getFavorites();
                            }
                            userFavorties.add(recipe.getRecipeId());
                            user.setFavorites(userFavorties);
                            databaseUser.child(user.getUsername()).setValue(user);
                            Toast.makeText(getContext(), "Favorit hinzugefügt", Toast.LENGTH_LONG).show();
                            imageViewAddToFavorites.setImageResource(R.drawable.ic_added_favorites);

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
        } else {
            Toast.makeText(getContext(), "Bitte anmelden", Toast.LENGTH_LONG).show();
        }
    }

    private void addToShoppingList() {
        if (MainActivity.isAngemeldet == true) {
            if (shoppingList == "selective") {
                ingredients.clear();
                for (int z = 0; z < ingredientsList.size(); z++) {
                    ingredients.add(ingredientsList.get(z).getName());
                }

                for (int i = 0; i < enthalteneZutaten.size(); i++) {
                    String string = enthalteneZutaten.get(i);
                    if ((ingredients.contains(string))) {
                        ingredients.remove(string);
                    }
                }
                for (int i = 0; i < ShoppingListFragment.foodList.size(); i++) {
                    String string = ShoppingListFragment.foodList.get(i).getName();
                    if ((ingredients.contains(string))) {
                        ingredients.remove(string);
                    }
                }
                ShoppingListFragment.foodNames.clear();
                for (int i = 0; i < ShoppingListFragment.foodList.size(); i++) {
                    ShoppingListFragment.foodNames.add(ShoppingListFragment.foodList.get(i).getName());
                }
                ShoppingListFragment.foodList.clear();
                for (int z = 0; z < ingredients.size(); z++) {
                    ShoppingListFragment.foodNames.add(ingredients.get(z));
                    ShoppingListFragment.upToDate = false;
                }
                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, shoppingListFragment, shoppingListFragment.getTag()).addToBackStack(null).commit();
            }

            if (shoppingList == "all") {
                ingredients.clear();
                for (int z = 0; z < ingredientsList.size(); z++) {
                    ingredients.add(ingredientsList.get(z).getName());
                }
                for (int i = 0; i < ShoppingListFragment.foodList.size(); i++) {
                    String string = ShoppingListFragment.foodList.get(i).getName();
                    if ((ingredients.contains(string))) {
                        ingredients.remove(string);
                    }
                }
                ShoppingListFragment.foodNames.clear();
                for (int i = 0; i < ShoppingListFragment.foodList.size(); i++) {
                    ShoppingListFragment.foodNames.add(ShoppingListFragment.foodList.get(i).getName());
                }
                ShoppingListFragment.foodList.clear();
                for (int i = 0; i < ingredients.size(); i++) {
                    ShoppingListFragment.foodNames.add(ingredients.get(i));
                    ShoppingListFragment.upToDate = false;
                    ShoppingListFragment.schonhinzugefügt = false;
                }

                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, shoppingListFragment, shoppingListFragment.getTag()).addToBackStack(null).commit();
            }
        } else {
            Toast.makeText(getContext(), "Bitte anmelden", Toast.LENGTH_LONG).show();
        }
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

    }


}
