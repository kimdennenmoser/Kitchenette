package com.example.navigationdrawerfromscratch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccountOverviewFragment extends Fragment {

    View view;

    TextView textViewIntolerance;
    ImageView imageViewIntolerance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_overview, container, false);

        textViewIntolerance = (TextView) view.findViewById(R.id.textViewIntolerance);
        imageViewIntolerance = (ImageView) view.findViewById(R.id.imageViewIntolerance);

        textViewIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchToIntolerance();

            }
        });

        imageViewIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // switchToIntolerance();
                IntoleranceFragment intoleranceFragment = new IntoleranceFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).commit();

            }
        });

        return view;
    }

    public void switchToIntolerance() {
        IntoleranceFragment intoleranceFragment = new IntoleranceFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).commit();

    }

}
