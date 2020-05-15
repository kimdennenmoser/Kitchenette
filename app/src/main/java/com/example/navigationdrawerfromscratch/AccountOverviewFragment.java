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
    TextView textViewMyRecipes, textViewSettings, textViewIntolerance, textViewFavorites, textViewCommunity;
    ImageView imageViewMyRecipes, imageViewSettings, imageViewIntolerance, imageViewFavorites, imageViewCommunity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_account_overview, container, false);

        //Initalisierung aller Elemente
        //Meine Rezepte
        textViewMyRecipes = (TextView) view.findViewById(R.id.textViewMyRecipes);
        imageViewMyRecipes = (ImageView) view.findViewById(R.id.imageViewMyRecipes);

        //Kontoeinstellungen
        textViewSettings = (TextView) view.findViewById(R.id.textViewSettings);
        imageViewSettings = (ImageView) view.findViewById(R.id.imageViewSettings);

        //Unverträglichkeiten
        textViewIntolerance = (TextView) view.findViewById(R.id.textViewIntolerance);
        imageViewIntolerance = (ImageView) view.findViewById(R.id.imageViewIntolerance);

        //Favoriten
        textViewFavorites = (TextView) view.findViewById(R.id.textViewFavorites);
        imageViewFavorites = (ImageView) view.findViewById(R.id.imageViewFavorites);

        //Communtiy
        textViewCommunity = (TextView) view.findViewById(R.id.textViewCommunity);
        imageViewCommunity = (ImageView) view.findViewById(R.id.imageViewCommunity);

        //Wechsel zu Meine Rezepte
        textViewMyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMyRecipes();
            }
        });
        imageViewMyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMyRecipes();
            }
        });

        //Wechsel zu Kontoeinstellungen
        textViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSettings();
            }
        });
        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSettings();
            }
        });

        //Wechsel zu Unverträglichkeiten
        textViewIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToIntolerance();
            }
        });
        imageViewIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToIntolerance();
            }
        });

        //Wechsel zu Favoriten
        textViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFavorites();
            }
        });
        imageViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFavorites();
            }
        });

        //Wechsel zu Community
        textViewIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFavorites();
            }
        });
        imageViewIntolerance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFavorites();
            }
        });

        return view;
    }

    //Wechsel zu Meine Rezepte
    public void switchToMyRecipes() {
        MyRecipesFragment myRecipesFragment = new MyRecipesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, myRecipesFragment, myRecipesFragment.getTag()).commit();
    }

    //Wechsel zu Kontoeinstellungen
    public void switchToSettings() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, settingsFragment, settingsFragment.getTag()).commit();
    }

    //Wechsel zu Unverträglichkeiten
    public void switchToIntolerance() {
        IntoleranceFragment intoleranceFragment = new IntoleranceFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, intoleranceFragment, intoleranceFragment.getTag()).commit();
    }

    //Wechsel zu Favoriten
    public void switchToFavorites() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, favoritesFragment, favoritesFragment.getTag()).commit();
    }

    //Wechsel zu Community
    public void switchToCommunity() {
        CommunityFragment communityFragment = new CommunityFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, communityFragment, communityFragment.getTag()).commit();
    }
}
