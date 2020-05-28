package com.example.navigationdrawerfromscratch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;

public class MyRecipesFragment extends Fragment {

View view;
    DatabaseReference databaseRecipe;
    ImageView addRecipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_my_recipe, container, false);

        //Initi
        addRecipe = view.findViewById(R.id.imageViewAddRecipe);

        //Wechsel zu neuem Rezept anlegen

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, createRecipeFragment, createRecipeFragment.getTag()).commit(); //.addToBackStack(null)

            }
        });


        return view;
    }



}
