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
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.lebensmittel.Gemüse;

public class IntoleranceFragment extends Fragment {


    @Nullable

    private SearchView mSearchField;
    private RecyclerView mResultList;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        mSearchField = (SearchView) view.findViewById(R.id.search_field);
        mResultList = (RecyclerView) view.findViewById(R.id.intolerance_list);



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
