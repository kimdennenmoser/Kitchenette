package com.example.navigationdrawerfromscratch.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChangeUserDataFragment extends Fragment {

    View view;
    EditText editTextChangeFirstName;
    EditText editTextChangeLastName;
    EditText editTextChangeUsername;
    EditText editTextChangeMail;
    public static EditText editTextChangePassword;
    Button btnSaveChanges;
    DatabaseReference databaseUser;
    List<String> allergiesUserList = new ArrayList<>();
    List<String> favoritesUserList = new ArrayList<>();
    List<String> shoppingList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_change_user_data, container, false);
        editTextChangeFirstName = (EditText) view.findViewById(R.id.editTextChangeFirstName);
        editTextChangeLastName = (EditText) view.findViewById(R.id.editTextChangeLastName);
        editTextChangeUsername = (EditText) view.findViewById(R.id.editTextChangeUsername);
        editTextChangeMail = (EditText) view.findViewById(R.id.editTextChangeMail);
        editTextChangePassword = (EditText) view.findViewById(R.id.editTextChangePassword);
        btnSaveChanges = (Button) view.findViewById(R.id.buttonSaveUserDataChange);

        databaseUser = FirebaseDatabase.getInstance().getReference("User");
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.child(AccountFragment.usernameString).getValue(User.class);
                editTextChangeFirstName.setText(user.getFirstname());
                editTextChangeLastName.setText(user.getLastname());
                editTextChangeUsername.setText(user.getUsername());
                editTextChangeMail.setText(user.getMail());
                editTextChangePassword.setText(user.getPassword());


                editTextChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangePasswordPopUpFragment changePasswordPopUpFragment = new ChangePasswordPopUpFragment();
                        changePasswordPopUpFragment.show(getActivity().getSupportFragmentManager(), "ChangePasswordPopUpFragment");
                    }
                });

                btnSaveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editTextChangeUsername.getText().toString().trim().contains(".") == true || editTextChangeUsername.getText().toString().trim().contains("#") == true
                                || editTextChangeUsername.getText().toString().trim().contains("$") == true || editTextChangeUsername.getText().toString().trim().contains("[") == true
                                || editTextChangeUsername.getText().toString().trim().contains("]") == true) {
                            Toast.makeText(getContext(), "Username darf nicht enthalten: '.', '#', '$', '[', oder ']' ", Toast.LENGTH_LONG).show();
                        } else if ((editTextChangeUsername.getText().toString().trim().contains(".") == false || editTextChangeUsername.getText().toString().trim().contains("#") == false
                                || editTextChangeUsername.getText().toString().trim().contains("$") == false || editTextChangeUsername.getText().toString().trim().contains("[") == false
                                || editTextChangeUsername.getText().toString().trim().contains("]") == false)){
                            String fName = editTextChangeFirstName.getText().toString().trim();
                            String lName = editTextChangeLastName.getText().toString().trim();
                            String uName = editTextChangeUsername.getText().toString().trim();
                            String mail = editTextChangeMail.getText().toString().trim();
                            String pWord = editTextChangePassword.getText().toString().trim();
                            allergiesUserList = user.getAllergies();
                            favoritesUserList = user.getFavorites();

                            User changedUser = new User(fName, lName, uName, pWord, mail, allergiesUserList, favoritesUserList, shoppingList);
                            databaseUser.child(uName).setValue(changedUser);
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }
}
