package com.example.navigationdrawerfromscratch.account.intolerance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.account.User;
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

public class IntoleranceFragment extends Fragment implements ProductAdapter.OnNoteListener {


    @Nullable

    private RecyclerView mResultList;
    private Button addIntolerance;
    private Button saveAllergies;
    ProductAdapter adapter;
    DatabaseReference databaseFood;
    DatabaseReference databaseUser;
    List<String> allergies = new ArrayList<>();
    public static String usernameString = null;

    static List<Food> productList = new ArrayList<>();


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intolerance, container, false);

        //productList = new ArrayList<>();

        //Initialisieren der Elemente
        databaseUser = FirebaseDatabase.getInstance().getReference("User").child(usernameString);

        mResultList = (RecyclerView) view.findViewById(R.id.intolerance_list);
        mResultList.setHasFixedSize(true);
        addIntolerance = (Button) view.findViewById(R.id.ButtonAddIntolerance);
        saveAllergies = (Button) view.findViewById(R.id.ButtonSaveAllergies);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //Wechsel zur Auswahl der "Zutaten"
        addIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodCategory foodCategory = new FoodCategory();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, foodCategory, foodCategory.getTag()).addToBackStack(null).commit();

            }
        });

        //Abspeichern der Unverträglichkeiten in das Benutzerprofil des Users
        saveAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAllergiesToUser();

            }
        });

        return view;
    }

    private void saveAllergiesToUser() {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < productList.size(); i++) {

                    String foodName = productList.get(i).getName();
                    allergies.add(foodName);

                }
                User user = dataSnapshot.getValue(User.class);
                user.setAllergies(allergies);
                databaseUser.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), productList, this);
        mResultList.setAdapter(adapter);


       /* databaseFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //productList.clear();
                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    //Food gemüse = productSnapshot.getValue(Food.class);
                    //productList.add(gemüse);
                    mResultList.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        */
    }

    @Override
    public void onFoodClick(int position) {
        Toast.makeText(getView().getContext(), "Wurde geklickt!", Toast.LENGTH_LONG).show();

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