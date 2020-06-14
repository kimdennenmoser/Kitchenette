package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.R;
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
import java.util.HashMap;
import java.util.List;

public class RecipeInstruction extends Fragment {

    View view;

    List<Zutat> ingredientsList;
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

    public static List<String> userFavorties = new ArrayList<>();
    public static String recipeString;
    IngredientsAdapter adapter;
    ImageButton buttonAddToFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recipe_instruction, container, false);

        ingredientsList = new ArrayList<>();
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
        System.out.println(databaseRecipe.getKey());
        buttonAddToFavorites = (ImageButton) view.findViewById(R.id.btnAddToFav);
        amoutPortions = (TextView) view.findViewById(R.id.textViewAmountPortions);


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

        buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userFavorties.clear();
                System.out.println("Angemeldet: " + MainActivity.isAngemeldet);
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
                                    System.out.println("Rezepte ID" + recipe.getRecipeId().toString().trim());
                                    user.setFavorites(userFavorties);
                                    databaseUser.child(user.getUsername()).setValue(user);
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
                //ingredientsList.clear();


                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    //Zutat zutat = recipeSnapshot.getValue(Zutat.class);
                    //Zutat zut = new Zutat(recipeSnapshot.getKey(),recipeSnapshot.getValue().toString());
                    Zutat zutat = new Zutat(recipeSnapshot.getKey(), recipeSnapshot.getValue().toString());
                    //Log.i("key", recipeSnapshot.getKey());
                    //Log.i("zutat", recipeSnapshot.getValue().toString());
                    //Log.i("Zutaten", zutat.getAmount()+ "  "+ zutat.getName());

                    //---------------------- bis hier hin passts -------------------------

                    ingredientsList.add(zutat);


                    recyclerViewIngredients.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

    }


}
