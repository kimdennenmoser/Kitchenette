package com.example.navigationdrawerfromscratch.account;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LogOutPopUpFragment extends DialogFragment {

    View view;
    Button btnLogOut, btnLogoutCancel;
    DatabaseReference databaseUser;

    List<String> allergiesUserList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_log_out_popup, container, false);

        databaseUser = FirebaseDatabase.getInstance().getReference("User").child(AccountFragment.usernameString);
        btnLogOut = (Button) view.findViewById(R.id.buttonLogOutAccount);
        btnLogoutCancel = (Button) view.findViewById(R.id.buttonLogOutAccountAbbruch);

        btnLogoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "User erfolgreich abgemeldet", Toast.LENGTH_LONG).show();
                MainActivity.isAngemeldet = false;
                AccountFragment accountFragment = new AccountFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, accountFragment, accountFragment.getTag()).addToBackStack(null).commit();
            }
        });

        onResume();
        onStop();

        return view;

    }

    //Ausblenden der Toolbar
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
