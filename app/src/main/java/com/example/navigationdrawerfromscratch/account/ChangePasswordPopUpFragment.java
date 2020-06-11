package com.example.navigationdrawerfromscratch.account;


import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordPopUpFragment extends DialogFragment {

    View view;
    EditText editTextOldPassword;
    EditText editTextNewPassword;
    Button btnChangePassword;
    DatabaseReference databaseUser;

    List<String> allergiesUserList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_change_password_popup, container, false);

        editTextOldPassword = (EditText) view.findViewById(R.id.editTextOldPassword);
        editTextNewPassword = (EditText) view.findViewById(R.id.editTextNewPassword);
        databaseUser = FirebaseDatabase.getInstance().getReference("User");
        btnChangePassword = (Button) view.findViewById(R.id.buttonChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                        if (user.getPassword().equals(editTextOldPassword.getText().toString().trim())) {

                            //Passwort direkt dem User wegschreiben

                           String fName = user.getFirstname();
                            String lName = user.getLastname();
                            String uName = user.getUsername();
                            String mail = user.getMail();
                            String pWord = editTextNewPassword.getText().toString().trim();
                            allergiesUserList = user.getAllergies();
                            ChangeUserDataFragment.editTextChangePassword.setText(pWord);
                            User changedUser = new User (fName, lName, uName, pWord, mail, allergiesUserList);
                            databaseUser.child(uName).setValue(changedUser);



                            //nur dem ChangeUserDataFragment mitgeben, erst wenn Speichern (auf ChangeUserDataFragment) weggeschrieben
                            //ChangeUserDataFragment.editTextChangePassword.setText(editTextNewPassword.getText().toString().trim());
                        } else {
                            Toast.makeText(getContext(), "altes Passwort inkorrekt", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
