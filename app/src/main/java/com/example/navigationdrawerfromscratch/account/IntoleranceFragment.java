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
    public static boolean newObst = false;
    List<String> allergiesList = new ArrayList<>();
    public int i;
    public static List<Food> newAllergies = new ArrayList<>();
    List<Food> oldAllergies = new ArrayList<>();
    public boolean saved = false;


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

    @Override
    public void onStart() {
        super.onStart();
        if (newObst == false) {
            adapter = new ProductAdapter(getView().getContext(), oldAllergies, this);
        }
        if (newObst == true) {
            saved = false;
            adapter = new ProductAdapter(getView().getContext(), newAllergies, this);
        }

        //wenn ein User angemeldet ist, prüfe, ob dieser Allergien/Unverträglichkeiten bereits abgespeichert hat und speichere sie in das RecyclerView
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(usernameString).getValue(User.class);

                System.out.println(newObst);

                if (user.getAllergies() != null) {
                    allergiesList = user.getAllergies();

                    //newAllergies.clear();
                    for (i = 0; i < allergiesList.size(); i++) {
                        final String allergieName = allergiesList.get(i);
                        databaseFood.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot intoleranceSnapshot : dataSnapshot.getChildren()) {
                                    Food food = intoleranceSnapshot.getValue(Food.class);
                                    System.out.println("1: " + oldAllergies.toString()); //1: leer //2: Apfel
                                    System.out.println("newObst: " + newObst);
                                    if (allergieName.equals(food.getName())) {
                                        //productList.clear();
                                        //if (newObst == false){ //wenn auskommentiert Ergebnis: Apfel Apfel Ananas Banane, wenn drin Ergebnis nur Banane
                                        System.out.println("2: " + oldAllergies.toString()); //1: leer // 2: Apfel
                                        oldAllergies.add(food);
                                        //}
                                        System.out.println("3: " + oldAllergies.toString()); //Runde 1: hier ist nur Apfel drin // 2: Apfel + Ananas

                                        if (newObst == true) { //allergieName.equals(food.getName()) &&
                                            //productList.clear();
                                            System.out.println("newAllergiesList1: " + newAllergies.toString()); //1: hier ist nur Banane drin //2: Banane + Apfel
                                            System.out.println("Wir sind zwar hier");
                                            System.out.println(oldAllergies.toString()); //1: nur Apfel //2: Apfel + Ananas
                                            for (i = 0; i < (oldAllergies.size()); i++) {
                                                System.out.println("aber nicht hier");
                                                System.out.println("i: " + i);
                                                newAllergies.add(oldAllergies.get(i));
                                                System.out.println("newAllergiesList2: " + newAllergies.toString()); //1: Banane und Apfel //2: Banane + Apfel + Apfel //3: Banane + Apfel +  Apfel + Ananas
                                            }
                                            System.out.println("new");
                                        }
                                    }
                                }
                                mResultList.setAdapter(adapter);
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

    public void saveAllergiesToUser() {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < newAllergies.size(); i++) {
                    String foodName = newAllergies.get(i).getName();
                    allergies.add(foodName);
                }
                user = dataSnapshot.child(usernameString).getValue(User.class);
                user.setAllergies(allergies);
                databaseUser.child(usernameString).setValue(user);
                saved = true;
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