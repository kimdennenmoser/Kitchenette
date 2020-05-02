package com.example.navigationdrawerfromscratch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ListView search_recipes;
    ArrayAdapter<String> adapter;
    User user;
    EditText editTextVorname;
    EditText editTextNachname;
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextMail;
    Button btncreateUser;
    DatabaseReference databaseUser;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //Nachricht, dass Firebase Connection erfolgreich war
        // Toast.makeText(this, "Firebase connection successful", Toast.LENGTH_LONG).show();

        //Diese Seite wird beim Starten der App angezeigt, hier: Rezeptvorschläge
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateAccountFragment()).commit();
           /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BrowseFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_recipes);

            */
        }







    }

    //Methode, dass das passende Fragment zu dem Menüpunkt angezeigt wird
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                break;
            case R.id.nav_start:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StartFragment()).commit();
                break;
            case R.id.nav_recipes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BrowseFragment()).commit();
                break;
            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
