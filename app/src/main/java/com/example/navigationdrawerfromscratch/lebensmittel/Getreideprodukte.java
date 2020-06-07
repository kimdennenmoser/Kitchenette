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

public class Getreideprodukte extends Fragment implements ProductAdapter.OnNoteListener {

    List<Food> getreideList;
    DatabaseReference databaseGetreide;
    ProductAdapter adapter;
    private RecyclerView mResultList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getreide, container, false);

        getreideList = new ArrayList<>();
        databaseGetreide = FirebaseDatabase.getInstance().getReference("Lebensmittel"); //"Lebensmittel"
        mResultList = (RecyclerView) view.findViewById(R.id.getreideView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), getreideList, this);
        databaseGetreide.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getreideList.clear();

                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    Food obst = productSnapshot.getValue(Food.class);
                    if (obst.getCategory().equals("Getreideprodukt")){
                        getreideList.add(obst);
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

        String foodID = getreideList.get(position).getId();
        String foodName = getreideList.get(position).getName();
        //String foodInfo = getreideList.get(position).getInfo();
        String foodImage = getreideList.get(position).getImage();
        String foodCategory = getreideList.get(position).getCategory();
        Food food = new Food(foodName, foodID, foodImage, foodCategory);

        IntoleranceFragment.productList.add(food);

        IntoleranceFragment intoleranceFragment = new IntoleranceFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).addToBackStack(null).commit();
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
