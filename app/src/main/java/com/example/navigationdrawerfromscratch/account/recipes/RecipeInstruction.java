package com.example.navigationdrawerfromscratch.account.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RecipeInstruction extends Fragment {

    View view;

    TextView recipeName;
    ImageView recipeImage;
    TextView preperationTime;
    RatingBar recipeRating;
    TextView instructions;
    RecyclerView recyclerViewIngredients;
    DatabaseReference databaseRecipe;
    public static String recipeString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recipe_instruction, container, false);

        recipeName = (TextView) view.findViewById(R.id.recipeName);
        recipeImage = (ImageView) view.findViewById(R.id.imgFood);
        preperationTime = (TextView) view.findViewById(R.id.preperationTime);
        recipeRating = (RatingBar) view.findViewById(R.id.recipeRating);
        instructions = (TextView) view.findViewById(R.id.txtInstruction);
        recyclerViewIngredients = (RecyclerView) view.findViewById(R.id.recyclerViewIngredients);
        recyclerViewIngredients.setHasFixedSize(true);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte").child(recipeString);
        System.out.println(databaseRecipe.getKey());


        databaseRecipe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                recipeName.setText(recipe.getRecipeName());
                Picasso.get().load(recipe.getRecipeImage()).into(recipeImage);
                preperationTime.setText(recipe.getPreparationTime());
                //recipeRating.setNumStars(recipe.getRecipeRating());
                instructions.setText(recipe.getInstructions());
                System.out.println(recipe.getInstructions());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }
}
