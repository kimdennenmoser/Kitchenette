package com.example.navigationdrawerfromscratch.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.recipes.Recipe;
import com.example.navigationdrawerfromscratch.account.recipes.RecipeInstruction;
import com.example.navigationdrawerfromscratch.adapters.RecipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements RecipeAdapter.OnRecipeListener {

    View view;
    DatabaseReference databaseUser;
    DatabaseReference databaseRecipe;

    RecyclerView recyclerView;
    List<Recipe> recipeList;
    RecipeAdapter adapter;
    List<String> favoritesIDsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        databaseUser = FirebaseDatabase.getInstance().getReference("User");
        databaseRecipe = FirebaseDatabase.getInstance().getReference("Rezepte");

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewMyFavorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recipeList = new ArrayList<>();
        favoritesIDsList = new ArrayList<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new RecipeAdapter(getView().getContext(), recipeList, this);

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                favoritesIDsList = user.getFavorites();
                databaseRecipe.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recipeList.clear();
                        for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                            Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                            for (int i = 0; i < favoritesIDsList.size(); i++) {
                                if (recipe.getRecipeId().equals(favoritesIDsList.get(i))) {
                                    recipeList.add(recipe);
                                }
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onRecipeClick(int position) {

    }
}
