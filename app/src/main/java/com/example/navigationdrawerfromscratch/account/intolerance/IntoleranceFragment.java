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

import com.example.navigationdrawerfromscratch.MainActivity;
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
    private Button refresh;
    ProductAdapter adapter;
    DatabaseReference databaseFood;
    DatabaseReference databaseUser;
    List<String> allergies = new ArrayList<>();
    User user;
    public static String usernameString = null;
    public static boolean gotAllergies = false;

    static List<Food> productList = new ArrayList<>();
    List<Food> foodList = new ArrayList<>();
    List<String> allergiesList = new ArrayList<>();


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intolerance, container, false);

        //Initialisieren der Elemente
        databaseFood = FirebaseDatabase.getInstance().getReference("Gemüse");
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        mResultList = (RecyclerView) view.findViewById(R.id.intolerance_list);
        mResultList.setHasFixedSize(true);
        addIntolerance = (Button) view.findViewById(R.id.ButtonAddIntolerance);
        saveAllergies = (Button) view.findViewById(R.id.ButtonSaveAllergies);
        refresh = (Button) view.findViewById(R.id.buttonRefresh);
        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));


        //productList = new ArrayList<>();


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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForAllergies();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ProductAdapter(getView().getContext(), productList, this);


            //wenn ein User angemeldet ist, prüfe, ob dieser Allergien/Unverträglichkeiten bereits abgespeichert hat
        if (MainActivity.isAngemeldet == true) {
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.child(usernameString).getValue(User.class);
                    if (user.getAllergies()!= null){
                        allergiesList = user.getAllergies();
                        for (int i = 0; i < allergiesList.size(); i++){
                            System.out.println(allergiesList.get(i));
                            databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mResultList.setAdapter(adapter);
        }
        /*
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(usernameString).exists()) {
                    User login = dataSnapshot.child(usernameString).getValue(User.class);
       databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    System.out.println(login.toString());
                    //user = dataSnapshot.child(usernameString).getValue(User.class);
                    if (user.getAllergies() != null) {
                        gotAllergies = true;
                    }
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
            }
        });

        if (MainActivity.isAngemeldet == true) {
            //checkForAllergies();

            databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child(usernameString).getValue(User.class).getFirstname();
                    System.out.println(name);

                    if (user.getAllergies() != null) {
                        gotAllergies = true;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        System.out.println(gotAllergies);

        if (MainActivity.isAngemeldet == true) {
            databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    // if (user.getAllergies() != null) {
                    List<String> allergien = user.getAllergies();
                    getFood(allergien);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

         */
    }



    public void checkForAllergies() {


    }

    public void saveAllergiesToUser() {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < productList.size(); i++) {
                    String foodName = productList.get(i).getName();
                    allergies.add(foodName);
                }
                user = dataSnapshot.getValue(User.class);
                user.setAllergies(allergies);
                databaseUser.setValue(user);
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