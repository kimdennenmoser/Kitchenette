package com.example.navigationdrawerfromscratch.account.recipes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.adapters.RecipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeResultFragment extends Fragment implements RecipeAdapter.OnRecipeListener  {

    View view;
    RecyclerView recyclerView;
    public static List<Recipe> recipeList = new ArrayList<>();
    RecipeAdapter adapter;
    DatabaseReference databaseRecipe;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_result, container, false);

        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewRecipeResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RecipeGenerate.resultsDisplayed = true;


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new RecipeAdapter(getView().getContext(), recipeList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRecipeClick(int position) {
        String id = recipeList.get(position).getRecipeId();
        RecipeInstruction recipeInstruction = new RecipeInstruction();
        FragmentManager recipeManager = getFragmentManager();
        recipeManager.beginTransaction().replace(R.id.fragment_container, recipeInstruction, recipeInstruction.getTag()).addToBackStack(null).commit();
        RecipeInstruction.recipeString=recipeList.get(position).getRecipeId();

    }
}
