package com.example.navigationdrawerfromscratch.account.recipes;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AcousticEchoCanceler;
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
import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.account.User;
import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.example.navigationdrawerfromscratch.lebensmittel.FoodCategory;
import com.example.navigationdrawerfromscratch.lebensmittel.Gemuese;
import com.example.navigationdrawerfromscratch.lebensmittel.Getreideprodukte;
import com.example.navigationdrawerfromscratch.lebensmittel.Gewuerze;
import com.example.navigationdrawerfromscratch.lebensmittel.Milchprodukte;
import com.example.navigationdrawerfromscratch.lebensmittel.Nuts;
import com.example.navigationdrawerfromscratch.lebensmittel.Obst;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.navigationdrawerfromscratch.adapters.ProductAdapter;

public class NewIntoleranceFragment extends Fragment implements ProductAdapter.OnNoteListener {


    @Nullable
    private RecyclerView mResultList;
    private Button addIntolerance;
    private Button saveAllergies;
    ProductAdapter adapter;
    DatabaseReference databaseFood;
    DatabaseReference databaseUser;
    List<String> allergies = new ArrayList<>();
    User user;

    public static List<Food> productList = new ArrayList<>();
    public static boolean newObst = false;
    public static List<String> allergiesList = new ArrayList<>();
    public int i;
    public static List<Food> newAllergies = new ArrayList<>();
    public static ArrayList<Food> oldAllergies = new ArrayList<>();
    public boolean saved = false;
    public static boolean upToDate = true;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_intolerance, container, false);

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

                Gemuese.vonWoher = "Intolerance";
                Getreideprodukte.vonWoher = "Intolerance";
                Gewuerze.vonWoher = "Intolerance";
                Milchprodukte.vonWoher = "Intolerance";
                Nuts.vonWoher = "Intolerance";
                Obst.vonWoher = "Intolerance";
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

    //-------Richtig bis hier-------

    @Override
    public void onStart() {
        super.onStart();
        Context context = this.getContext();
        adapter = new ProductAdapter(context, oldAllergies, this);

        //wenn ein User angemeldet ist, prüfe, ob dieser Allergien/Unverträglichkeiten bereits abgespeichert hat und speichere sie in das RecyclerView

        if (upToDate == true) {
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                    System.out.println(newObst);
                    if (user.getAllergies() != null) {
                        allergiesList = user.getAllergies();
                        for (i = 0; i < allergiesList.size(); i++) {
                            final String allergieName = allergiesList.get(i);
                            databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot intoleranceSnapshot : dataSnapshot.getChildren()) {
                                        Food food = intoleranceSnapshot.getValue(Food.class);
                                        if (allergieName.equals(food.getName())) {
                                            oldAllergies.add(food);
                                            mResultList.setAdapter(adapter);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    } else {
                        mResultList.setAdapter(adapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else {
            mResultList.setAdapter(adapter);
        }
    }

    public void saveAllergiesToUser() {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < oldAllergies.size(); i++) {
                    String foodName = oldAllergies.get(i).getName();
                    allergies.add(foodName);
                }
                user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                user.setAllergies(allergies);
                databaseUser.child(AccountFragment.usernameString).setValue(user);
                upToDate = true;
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food = snapshot.getValue(Food.class);
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
