package com.example.navigationdrawerfromscratch.account.recipes.recipeIngredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.IntoleranceFragment;
import com.example.navigationdrawerfromscratch.account.recipes.IngredientCategory;
import com.example.navigationdrawerfromscratch.account.recipes.RecipeGenerate;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeGemuese extends Fragment implements ProductAdapter.OnNoteListener {

    List<Food> gemueseList;
    DatabaseReference databaseGemuese;
    ProductAdapter adapter;
    private RecyclerView mResultList;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gemuese, container, false);

        gemueseList = new ArrayList<>();
        databaseGemuese = FirebaseDatabase.getInstance().getReference("Lebensmittel");
        mResultList = (RecyclerView) view.findViewById(R.id.gemueseView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(),gemueseList,this);
        databaseGemuese.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                gemueseList.clear();

                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    Food gemüse = productSnapshot.getValue(Food.class);
                    if (gemüse.getCategory().equals("Gemüse")){
                        gemueseList.add(gemüse);
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

        String foodID = gemueseList.get(position).getId();
        String foodName = gemueseList.get(position).getName();
        //String foodInfo = gemueseList.get(position).getInfo();
        String foodImage = gemueseList.get(position).getImage();
        String foodCategory = gemueseList.get(position).getCategory();
        Food food = new Food(foodName, foodID, foodImage, foodCategory);
        RecipeGenerate.productList.add(food);

        IngredientCategory ingredientCategory = new IngredientCategory();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, ingredientCategory, ingredientCategory.getTag()).addToBackStack(null).commit();
    }

}
