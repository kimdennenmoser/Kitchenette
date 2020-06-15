package com.example.navigationdrawerfromscratch.lebensmittel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.NewIntoleranceFragment;
import com.example.navigationdrawerfromscratch.account.recipes.CreateRecipeFragment;
import com.example.navigationdrawerfromscratch.account.recipes.RecipeGenerate;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Drinks extends Fragment implements ProductAdapter.OnNoteListener {

    List<Food> drinksList;
    DatabaseReference databaseDrinks;
    ProductAdapter adapter;
    private RecyclerView mResultList;
    public static String vonWoher = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        drinksList = new ArrayList<>();
        databaseDrinks = FirebaseDatabase.getInstance().getReference("Lebensmittel"); //"Lebensmittel"
        mResultList = (RecyclerView) view.findViewById(R.id.drinksView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), drinksList, this);
        databaseDrinks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drinksList.clear();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Food obst = productSnapshot.getValue(Food.class);
                    if (obst.getCategory().equals("Getränk")) {
                        drinksList.add(obst);
                    }
                    mResultList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onFoodClick(int position) {

        String foodID = drinksList.get(position).getId();
        String foodName = drinksList.get(position).getName();
        //String foodInfo = süßList.get(position).getInfo();
        String foodImage = drinksList.get(position).getImage();
        String foodCategory = drinksList.get(position).getCategory();
        Food food = new Food(foodName, foodID, foodImage, foodCategory);

        if (vonWoher == "Intolerance") {
            if(NewIntoleranceFragment.oldAllergies.contains(food)){
                Toast.makeText(getView().getContext(), "Lebensmittel bereits vorhanden", Toast.LENGTH_LONG).show();
            }else {
                NewIntoleranceFragment.upToDate = false;
                NewIntoleranceFragment.oldAllergies.add(food);

                NewIntoleranceFragment intoleranceFragment = new NewIntoleranceFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).addToBackStack(null).commit();
            }
        }
        if (vonWoher == "CreateRecipe") {
            CreateRecipeFragment.zutatenList.add(food);
            CreateRecipeFragment.foodName = food.getName();

            CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, createRecipeFragment, createRecipeFragment.getTag()).addToBackStack(null).commit();

        }
        if (vonWoher == "Search") {
            RecipeGenerate.productList.add(food);

            RecipeGenerate recipeGenerate = new RecipeGenerate();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, recipeGenerate, recipeGenerate.getTag()).addToBackStack(null).commit();


        }
    }


    //View Holder Class

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }


    }


}
