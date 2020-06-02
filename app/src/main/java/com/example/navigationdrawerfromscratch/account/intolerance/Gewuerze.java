package com.example.navigationdrawerfromscratch.account.intolerance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class Gewuerze extends Fragment implements ProductAdapter.OnNoteListener {


    private TextView ueGewuerze;
    List<Food> gewuerzeList;
    DatabaseReference databaseGewuerze;
    ProductAdapter adapter;
    private RecyclerView mResultList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gewuerze, container, false);

        databaseGewuerze = FirebaseDatabase.getInstance().getReference("Gewürze");
        ueGewuerze = (TextView) view.findViewById(R.id.ueGewuerze);
        gewuerzeList = new ArrayList<>();
        mResultList = (RecyclerView) view.findViewById(R.id.gewuerzeView);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseGewuerze.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                gewuerzeList.clear();

                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    Food gewuerze = productSnapshot.getValue(Food.class);

                    gewuerzeList.add(gewuerze);

                    adapter = new ProductAdapter(getView().getContext(),gewuerzeList, null);
                    mResultList.setAdapter(adapter);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onNoteClick(int position) {

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
