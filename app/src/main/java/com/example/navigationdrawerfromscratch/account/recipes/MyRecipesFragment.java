package com.example.navigationdrawerfromscratch.account.recipes;

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

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.adapters.RecipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesFragment extends Fragment implements RecipeAdapter.OnRecipeListener {

    View view;
    DatabaseReference databaseRecipe;
    ImageView addRecipe;
    RecyclerView recyclerView;
    List<Recipe> recipeList;
    RecipeAdapter adapter;
    String usernameString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_my_recipes, container, false);

        addRecipe = view.findViewById(R.id.imageViewAddRecipe);

        recipeList = new ArrayList<>();
        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewMyRecipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        usernameString = AccountFragment.usernameString;

        //Wechsel zu neuem Rezept anlegen
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRecipeFragment.foodName = "Zutat";
                CreateRecipeFragment.ingredientsMap.clear();
                CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, createRecipeFragment, createRecipeFragment.getTag()).commit(); //.addToBackStack(null)
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new RecipeAdapter(getView().getContext(), recipeList, this);
        databaseRecipe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    if (recipe.getCreator().equals(usernameString)){
                       recipeList.add(recipe);
                    }
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
        RecipeInstruction.shoppingList = "all";
        RecipeInstruction recipeInstruction = new RecipeInstruction();
        FragmentManager recipeManager = getFragmentManager();
        recipeManager.beginTransaction().replace(R.id.fragment_container, recipeInstruction, recipeInstruction.getTag()).addToBackStack(null).commit();
        RecipeInstruction.recipeString=recipeList.get(position).getRecipeId();
    }

    //View Holder Class
    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}
