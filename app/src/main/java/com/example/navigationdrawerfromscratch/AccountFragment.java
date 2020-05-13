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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    View view;
    TextView textView;
    Button anmeldenBtn;
    EditText editTextInsertUsername;
    String editTextInsertUsernameString;
    DatabaseReference databaseUser;
    Context context;

    List<User> userList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);
        textView = (TextView) view.findViewById(R.id.textViewCreateAccount);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountFragment createAccountFragment =  new CreateAccountFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, createAccountFragment, createAccountFragment.getTag()).commit();
            }
        });

        context = this.getActivity();

        databaseUser = FirebaseDatabase.getInstance().getReference();

        userList = new ArrayList<>();

        anmeldenBtn = (Button) view.findViewById(R.id.buttonLogIn);
        anmeldenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AccountOverviewFragment accountOverviewFragment = new AccountOverviewFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, accountOverviewFragment, accountOverviewFragment.getTag()).commit();

                //databaseUser.addChildEventListener();


                databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String uname = dataSnapshot.child("username").getValue(String.class);
                                //User user = userSnapshot.getValue(User.class);
                                //userList.add(user);

                                editTextInsertUsername = (EditText) view.findViewById(R.id.editTextInsertUsername);
                                editTextInsertUsernameString = editTextInsertUsername.getText().toString().trim();

                                if (uname == editTextInsertUsernameString) {
                                    Toast.makeText(context, "Anmeldung erfolgreich", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Username existiert nicht", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
                /*databaseUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //aufgerufen, jedes Mal, wenn sich etwas in der DB ändert
  if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String uname = dataSnapshot.child("username").getValue(String.class);
                                //User user = userSnapshot.getValue(User.class);
                                //userList.add(user);

                                editTextInsertUsername = (EditText) view.findViewById(R.id.editTextInsertUsername);
                                editTextInsertUsernameString = editTextInsertUsername.getText().toString().trim();

                                if (uname == editTextInsertUsernameString) {
                                    Toast.makeText(context, "Anmeldung erfolgreich", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Username existiert nicht", Toast.LENGTH_LONG).show();
                                }
                            }



                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //String tag;
                        //Log.w(tag, "Failed to read value.", error.toException());
                    }
                });

                 */
           // }
        });

        return view;
    }

}
