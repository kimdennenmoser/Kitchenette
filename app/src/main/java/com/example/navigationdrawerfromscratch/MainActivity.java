package com.example.navigationdrawerfromscratch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.navigationdrawerfromscratch.account.AccountFragment;
import com.example.navigationdrawerfromscratch.account.AccountOverviewFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ListView search_recipes;
    ArrayAdapter<String> adapter;
    public static boolean isAngemeldet=false;

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

        //Diese Seite wird beim Starten der App angezeigt, hier: Rezeptvorschläge
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BrowseFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_recipes);
        }

    }


    //Methode, dass das passende Fragment zu dem Menüpunkt anzeigt
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_account:
                if(isAngemeldet==false){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountOverviewFragment()).commit();
                }

                break;
            case R.id.nav_start:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                break;
            case R.id.nav_recipes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BrowseFragment()).commit();
                break;
            case R.id.nav_shoppinglist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingListFragment()).commit();
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
