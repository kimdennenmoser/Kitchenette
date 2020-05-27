package com.example.navigationdrawerfromscratch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.lebensmittel.Gemüse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IntoleranceFragment extends Fragment {


    @Nullable

    private EditText mSearchField;
    private RecyclerView mResultList;
    private Button addIntolerance;
    ProductAdapter adapter;
    DatabaseReference databaseFood;


    List<Gemüse> productList;



    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
               View view = inflater.inflate(R.layout.fragment_intolerance, container, false);

        productList = new ArrayList<>();

        databaseFood = FirebaseDatabase.getInstance().getReference("Gemüse");

        mSearchField = (EditText) view.findViewById(R.id.search_field);
        mResultList = (RecyclerView) view.findViewById(R.id.intolerance_list);
        mResultList.setHasFixedSize(true);
        addIntolerance = (Button) view.findViewById(R.id.addIntolerance);

        addIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodCategory foodCategory = new FoodCategory();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, foodCategory, foodCategory.getTag()).addToBackStack(null).commit();

            }
        });

        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));






        /*productList.add(
                new Gemüse(
                        1,
                        "Gurke",
                        "Eine leckere Gurke",
                        R.drawable.gurke

                )

        );

        productList.add(
                new Gemüse(
                        2,
                        "Tomate",
                        "Eine saftige Tomate",
                        R.drawable.tomate
                )
        ); */

        //adapter = new ProductAdapter(view.getContext(),productList);
        //mResultList.setAdapter(adapter);



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        databaseFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                productList.clear();

                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    Gemüse gemüse = productSnapshot.getValue(Gemüse.class);

                    productList.add(gemüse);
                }

                adapter = new ProductAdapter(getView().getContext(), productList);
                mResultList.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
