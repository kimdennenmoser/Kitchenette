package com.example.navigationdrawerfromscratch.account.intolerance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Obst extends Fragment implements ProductAdapter.OnNoteListener{

    private TextView ueObst;
    List<Food> obstList;
    DatabaseReference databaseObst;
    ProductAdapter adapter;
    private RecyclerView mResultList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obst, container, false);

        ueObst = (TextView) view.findViewById(R.id.ueObst);
        obstList = new ArrayList<>();
        databaseObst = FirebaseDatabase.getInstance().getReference("Obst");
        mResultList = (RecyclerView) view.findViewById(R.id.obstView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), obstList,this);
        databaseObst.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                obstList.clear();

                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    Food obst = productSnapshot.getValue(Food.class);
                    obstList.add(obst);
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

        String foodID = obstList.get(position).getId();
        String foodName = obstList.get(position).getName();
        String foodInfo = obstList.get(position).getInfo();
        String foodImage = obstList.get(position).getImage();
        Food food = new Food(foodName, foodID, foodInfo, foodImage);
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
