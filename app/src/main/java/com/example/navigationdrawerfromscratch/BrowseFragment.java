package com.example.navigationdrawerfromscratch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.account.recipes.Recipe;
import com.example.navigationdrawerfromscratch.account.recipes.RecipeGenerate;
import com.example.navigationdrawerfromscratch.adapters.RecipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BrowseFragment extends Fragment implements RecipeAdapter.OnRecipeListener  { //implements RecipeAdapter.OnRecipeListener

    RecyclerView recyclerView;
    List<Recipe> recipeList;
    RecipeAdapter adapter;
    DatabaseReference databaseRecipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        recipeList = new ArrayList<>();
        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewBrowseRecipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewZurSuche);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeGenerate recipeGenerate = new RecipeGenerate();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, recipeGenerate, recipeGenerate.getTag()).commit();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        Context context = this.getContext();
        super.onStart();
        adapter = new RecipeAdapter(context, recipeList, this);

        databaseRecipe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                recipeList.clear();

                for(DataSnapshot recipeSnapshot: dataSnapshot.getChildren()){
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onRecipeClick(int position) {
        Toast.makeText(getView().getContext(), position+" Wurde geklickt!", Toast.LENGTH_LONG).show();
        String id = recipeList.get(position).getRecipeId();
        System.out.println(id);

    }

//View Holder Class

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

    }

  /*  private void insertFakeRecipeS() {
        for (int i = 0; i < 1000; i++) {
            Recipe recipe = new Recipe();
            recipe.setRecipeId("nbvghjmnbvgc24rtgvc #" + i);
            recipe.setRecipeName("Brot");
            recipe.setCategory("Vorspeise");
            recipe.setPreparationTime("140");
            recipe.setIngredientsList(" ");
            recipe.setRecipeImage("");
            recipeArrayList.add(recipe);

        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recipeAdapter = new RecipeAdapter(this.getContext(), recipeArrayList, this);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onRecipeClick(int position) {
        //Switch to new Fragment/Activity
        Toast.makeText(getView().getContext(), "Wurde geklickt!", Toast.LENGTH_LONG).show();
    }

   */

}
