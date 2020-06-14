package com.example.navigationdrawerfromscratch.lebensmittel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.account.IntoleranceFragment;
import com.example.navigationdrawerfromscratch.account.recipes.CreateRecipeFragment;
import com.example.navigationdrawerfromscratch.account.recipes.NewIntoleranceFragment;
import com.example.navigationdrawerfromscratch.account.recipes.RecipeGenerate;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gewuerze extends Fragment implements ProductAdapter.OnNoteListener {


    private TextView ueGewuerze;
    List<Food> gewuerzeList;
    DatabaseReference databaseGewuerze;
    ProductAdapter adapter;
    private RecyclerView mResultList;
    public static String vonWoher = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gewuerze, container, false);

        gewuerzeList = new ArrayList<>();
        databaseGewuerze = FirebaseDatabase.getInstance().getReference("Lebensmittel"); //"Gewürze"
        mResultList = (RecyclerView) view.findViewById(R.id.gewuerzeView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(),gewuerzeList, this);
        databaseGewuerze.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gewuerzeList.clear();
                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    Food gewuerze = productSnapshot.getValue(Food.class);
                    if (gewuerze.getCategory().equals("Gewürz")){
                        gewuerzeList.add(gewuerze);
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
        String foodID = gewuerzeList.get(position).getId();
        String foodName = gewuerzeList.get(position).getName();
        //String foodInfo = gewuerzeList.get(position).getInfo();
        String foodImage = gewuerzeList.get(position).getImage();
        String foodCategory = gewuerzeList.get(position).getCategory();
        Food food = new Food(foodName, foodID, foodImage, foodCategory);
        if (vonWoher == "Intolerance") {
            NewIntoleranceFragment.upToDate=false;
            NewIntoleranceFragment.oldAllergies.add(food);

            NewIntoleranceFragment intoleranceFragment = new NewIntoleranceFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).addToBackStack(null).commit();
        }
        if (vonWoher == "CreateRecipe") {
            CreateRecipeFragment.zutatenList.add(food);
            CreateRecipeFragment.foodName = food.getName();

            CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, createRecipeFragment, createRecipeFragment.getTag()).addToBackStack(null).commit();
        }
        if (vonWoher == "Search"){
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
