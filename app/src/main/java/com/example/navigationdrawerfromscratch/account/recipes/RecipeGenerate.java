package com.example.navigationdrawerfromscratch.account.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.intolerance.FoodCategory;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RecipeGenerate extends Fragment implements ProductAdapter.OnNoteListener {


    Button btnAddIngredient;
    TextView textViewSuche;
    RecyclerView recyclerViewIngredient;
    ProductAdapter adapter;
    DatabaseReference databaseFood;
    static List<Food> productList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerViewIngredient = (RecyclerView) view.findViewById(R.id.recyclerViewIngredient);
        recyclerViewIngredient.setHasFixedSize(true);
        recyclerViewIngredient.setLayoutManager(new LinearLayoutManager(view.getContext()));
        btnAddIngredient = (Button) view.findViewById(R.id.btnAddIngredient);

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(),"Wurde geklickt!", Toast.LENGTH_LONG).show();
                FoodCategory foodCategory = new FoodCategory();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, foodCategory, foodCategory.getTag()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onFoodClick(int position) {


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), productList, this);
        recyclerViewIngredient.setAdapter(adapter);
    }


    public class FoodViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }
    }
}
