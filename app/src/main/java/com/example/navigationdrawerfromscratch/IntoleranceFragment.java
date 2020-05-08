package com.example.navigationdrawerfromscratch;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class IntoleranceFragment extends AppCompatActivity {


    @Nullable

    private EditText mSearchField;
    private RecyclerView mResultList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mSearchField = (EditText) findViewById(R.id.search_field);

        mResultList = (RecyclerView) findViewById(R.id.intolerance_list);


    }
}
