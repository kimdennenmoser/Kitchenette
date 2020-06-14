package com.example.navigationdrawerfromscratch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.account.DeleteIngredientFromShoppingListPopUpFragment;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment implements ProductAdapter.OnNoteListener {

    View view;

    public static RecyclerView recyclerView;
    public static List<String> foodNames = new ArrayList<>();
    Button buttonClearList;
    public static List<Food> foodList = new ArrayList<>();
    public static ProductAdapter adapter;
    public static boolean cleanList = false;
    DatabaseReference databaseFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_list, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewShoppingList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        databaseFood = FirebaseDatabase.getInstance().getReference("Lebensmittel");
        buttonClearList = (Button) view.findViewById(R.id.buttonClearShoppingList);

        buttonClearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodList.clear();
                foodNames.clear();
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), foodList, this);

        databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot intoleranceSnapshot : dataSnapshot.getChildren()) {
                    Food food = intoleranceSnapshot.getValue(Food.class);
                    for (int i = 0; i < foodNames.size(); i++){
                        if ((foodNames.get(i)).equals(food.getName())) {
                            System.out.println("yess");
                            foodList.add(food);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void removeFood(Food food){
        foodList.remove(food);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFoodClick(int position) {
        //Popup: Frage, ob Zutat entfernen
        DeleteIngredientFromShoppingListPopUpFragment deleteIngredientPopUpFragment = new DeleteIngredientFromShoppingListPopUpFragment();
        deleteIngredientPopUpFragment.show(getActivity().getSupportFragmentManager(), "DeleteIngredientPopUpFragment");
        DeleteIngredientFromShoppingListPopUpFragment.foodName = foodList.get(position).getName();
    }
}
