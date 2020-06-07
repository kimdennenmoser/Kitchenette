package com.example.navigationdrawerfromscratch.account;

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

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.account.User;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.example.navigationdrawerfromscratch.lebensmittel.FoodCategory;
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
    User user;
    public static String usernameString = null;

    public static List<Food> productList = new ArrayList<>();
    List<String> allergiesList = new ArrayList<>();
    public int i;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intolerance, container, false);

        //Initialisieren der Elemente
        databaseFood = FirebaseDatabase.getInstance().getReference("Lebensmittel");
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

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

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), productList, this);

        //wenn ein User angemeldet ist, prüfe, ob dieser Allergien/Unverträglichkeiten bereits abgespeichert hat und speichere sie in das RecyclerView
        if (MainActivity.isAngemeldet == true) {
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.child(usernameString).getValue(User.class);

                    if (user.getAllergies() != null) {
                        allergiesList = user.getAllergies();
                        //productList.clear();
                        for (i = 0; i < allergiesList.size(); i++) {
                            final String allergieName = allergiesList.get(i);
                            databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot intoleranceSnapshot : dataSnapshot.getChildren()) {
                                        Food food = intoleranceSnapshot.getValue(Food.class);
                                        // for (i = 0; i < productList.size(); i++) {
                                        if (allergieName.equals(food.getName())) {
                                            //for (i = 0; i < productList.size(); i++){
                                            //if (productList.get(i).equals(food)) {
                                            productList.add(food);
                                            // }
                                            //else {

                                            // }
                                        }
                                    }
                                    //}
                                    //}
                                    mResultList.setAdapter(adapter);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                        }
                    }
                    mResultList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    public void saveAllergiesToUser() {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < productList.size(); i++) {
                    String foodName = productList.get(i).getName();
                    allergies.add(foodName);
                }
                user = dataSnapshot.child(usernameString).getValue(User.class);
                user.setAllergies(allergies);
                databaseUser.child(usernameString).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getFood(List<String> allergien) {
        databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Food food = productSnapshot.getValue(Food.class);
                    for (int i = 0; i < productList.size(); i++)
                        if ((food.getName()).equals(productList.get(i).getName())) {
                            productList.add(food);
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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