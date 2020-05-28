package com.example.navigationdrawerfromscratch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FoodCategory extends Fragment {

    private Button buttonObst;
    private Button buttonGemuese;
    private Button buttonGewuerze;
    private Button buttonMilchprod;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_selection, container, false);

        buttonGemuese = (Button) view.findViewById(R.id.buttonGemuese);
        buttonObst = (Button) view.findViewById(R.id.buttonObst);
        buttonGewuerze = (Button) view.findViewById(R.id.buttonGewuerze);
        buttonMilchprod = (Button) view.findViewById(R.id.buttonMilchprod);


        return view;
    }
}
