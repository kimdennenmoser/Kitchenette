package com.example.navigationdrawerfromscratch.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.MainActivity;
import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.recipes.CreateRecipeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {


    View view;
    TextView textView;
    Button anmeldenBtn;
    EditText editTextInsertUsername;
    EditText editTextPassword;
    DatabaseReference databaseUser;
    Context context;
    private DrawerLayout drawer;
    public static String usernameString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_account, container, false);
        context = this.getActivity();

        //Initialisieren der Elemente
        databaseUser = FirebaseDatabase.getInstance().getReference("User");
        editTextInsertUsername = (EditText) view.findViewById(R.id.editTextInsertUsername);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        anmeldenBtn = (Button) view.findViewById(R.id.buttonLogIn);

        //Wechsel zur Seite "Account erstellen"
        textView = (TextView) view.findViewById(R.id.textViewCreateAccount);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountFragment createAccountFragment = new CreateAccountFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, createAccountFragment, createAccountFragment.getTag()).addToBackStack(null).commit();
            }
        });

        anmeldenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(editTextInsertUsername.getText().toString(), editTextPassword.getText().toString());
            }
        });

        return view;
    }

    //Methode zum LogIn
    public void signIn(final String username, final String password) {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(username).exists()) {
                    if (!username.isEmpty()) {
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)) {
                            Toast.makeText(context, "Anmeldung erfolgreich", Toast.LENGTH_LONG).show();
                            MainActivity.isAngemeldet=true;
                            usernameString = username;
                            //CreateRecipeFragment.usernameString = username;

                            //Wenn Anmeldung erfolgreich, Switch zur Kontoübersicht
                            AccountOverviewFragment accountOverviewFragment = new AccountOverviewFragment();
                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction().replace(R.id.fragment_container, accountOverviewFragment, accountOverviewFragment.getTag()).addToBackStack(null).commit();

                        } else {
                            Toast.makeText(context, "inkorrektes Passwort", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "User existiert nicht", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
