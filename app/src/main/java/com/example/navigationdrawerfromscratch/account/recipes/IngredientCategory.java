package com.example.navigationdrawerfromscratch.account.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients.RecipeGemuese;
import com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients.RecipeGetreideprodukte;
import com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients.RecipeGewuerze;
import com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients.RecipeMilchprodukte;
import com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients.RecipeObst;
import com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients.RecipeSueßes;

public class IngredientCategory extends Fragment {

    private Button buttonObst;
    private Button buttonGemuese;
    private Button buttonGewuerze;
    private Button buttonMilchprod;
    private Button buttonGetreide;
    private Button buttonSüßes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_selection, container, false);

        buttonGemuese = (Button) view.findViewById(R.id.buttonGemuese);

        buttonGemuese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeGemuese recipeGemuese = new RecipeGemuese();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, recipeGemuese, recipeGemuese.getTag()).addToBackStack(null).commit();

            }
        });



        buttonObst = (Button) view.findViewById(R.id.buttonObst);

        buttonObst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeObst recipeObst = new RecipeObst();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, recipeObst, recipeObst.getTag()).addToBackStack(null).commit();
            }
        });



        buttonGewuerze = (Button) view.findViewById(R.id.buttonGewuerze);

        buttonGewuerze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeGewuerze gewuerze = new RecipeGewuerze();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, gewuerze,gewuerze.getTag()).addToBackStack(null).commit();
            }
        });

        buttonMilchprod = (Button) view.findViewById(R.id.buttonMilchprod);

        buttonMilchprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeMilchprodukte dairy = new RecipeMilchprodukte();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, dairy, dairy.getTag()).addToBackStack(null).commit();
            }
        });

        buttonGetreide = (Button) view.findViewById(R.id.buttonGetreide);

        buttonGetreide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeGetreideprodukte getreideprodukte = new RecipeGetreideprodukte();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, getreideprodukte, getreideprodukte.getTag()).addToBackStack(null).commit();
            }
        });


        buttonSüßes = (Button) view.findViewById(R.id.buttonNuts);

        buttonSüßes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeSueßes süßes = new RecipeSueßes();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, süßes, süßes.getTag()).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
