package com.example.navigationdrawerfromscratch.lebensmittel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.R;

public class FoodCategory extends Fragment {

    private Button buttonObst;
    private Button buttonGemuese;
    private Button buttonGewuerze;
    private Button buttonMilchprod;
    private Button buttonGetreide;
    private Button buttonNuts;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_selection, container, false);

        buttonGemuese = (Button) view.findViewById(R.id.buttonGemuese);

        buttonGemuese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gemuese gemuese = new Gemuese();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, gemuese, gemuese.getTag()).addToBackStack(null).commit();

            }
        });



        buttonObst = (Button) view.findViewById(R.id.buttonObst);

        buttonObst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obst obst = new Obst();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, obst, obst.getTag()).addToBackStack(null).commit();
            }
        });



        buttonGewuerze = (Button) view.findViewById(R.id.buttonGewuerze);

        buttonGewuerze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gewuerze gewuerze = new Gewuerze();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, gewuerze,gewuerze.getTag()).addToBackStack(null).commit();
            }
        });

        buttonMilchprod = (Button) view.findViewById(R.id.buttonMilchprod);

        buttonMilchprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Milchprodukte dairy = new Milchprodukte();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, dairy, dairy.getTag()).addToBackStack(null).commit();
            }
        });

        buttonGetreide = (Button) view.findViewById(R.id.buttonGetreide);

        buttonGetreide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Getreideprodukte getreideprodukte = new Getreideprodukte();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, getreideprodukte, getreideprodukte.getTag()).addToBackStack(null).commit();
            }
        });


        buttonNuts = (Button) view.findViewById(R.id.buttonNuts);

        buttonNuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nuts nuts = new Nuts();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, nuts, nuts.getTag()).addToBackStack(null).commit();
            }
        });

        return view;
    }



}
