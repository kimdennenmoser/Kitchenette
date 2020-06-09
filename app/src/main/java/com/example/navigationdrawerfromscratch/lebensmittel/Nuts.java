package com.example.navigationdrawerfromscratch.lebensmittel;

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
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Nuts extends Fragment implements ProductAdapter.OnNoteListener {

    List<Food> nutsList;
    DatabaseReference databaseNuts;
    ProductAdapter adapter;
    private RecyclerView mResultList;
    public static String vonWoher = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuts, container, false);

        nutsList = new ArrayList<>();
        databaseNuts = FirebaseDatabase.getInstance().getReference("Lebensmittel"); //"Lebensmittel"
        mResultList = (RecyclerView) view.findViewById(R.id.nutsView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), nutsList, this);
        databaseNuts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nutsList.clear();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Food obst = productSnapshot.getValue(Food.class);
                    if (obst.getCategory().equals("Nüsse")) {
                        nutsList.add(obst);
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

        String foodID = nutsList.get(position).getId();
        String foodName = nutsList.get(position).getName();
        //String foodInfo = süßList.get(position).getInfo();
        String foodImage = nutsList.get(position).getImage();
        String foodCategory = nutsList.get(position).getCategory();
        Food food = new Food(foodName, foodID, foodImage, foodCategory);

        if (vonWoher == "Intolerance") {
            IntoleranceFragment.productList.add(food);
            IntoleranceFragment intoleranceFragment = new IntoleranceFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).addToBackStack(null).commit();

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
