package com.example.navigationdrawerfromscratch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.lebensmittel.Gemüse;

import java.util.ArrayList;
import java.util.List;

public class IntoleranceFragment extends Fragment {


    @Nullable

    private SearchView mSearchField;
    private RecyclerView mResultList;
    ProductAdapter adapter;

    List<Gemüse> productList;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        productList = new ArrayList<>();

        mSearchField = (SearchView) view.findViewById(R.id.search_field);
        mResultList = (RecyclerView) view.findViewById(R.id.intolerance_list);
        mResultList.setHasFixedSize(true);

        mResultList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        productList.add(
                new Gemüse(
                        1,
                        "Gurke",
                        "Eine leckere Gurke",
                        1

                )
        );

        adapter = new ProductAdapter(view.getContext(),productList);
        mResultList.setAdapter(adapter);



        return view;
    }


//View Holder Class

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }


    }


}
