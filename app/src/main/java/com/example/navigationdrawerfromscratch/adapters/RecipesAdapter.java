package com.example.navigationdrawerfromscratch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;

import java.util.HashMap;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    LayoutInflater inflater;
    HashMap<String, String> modelList;
    Context context;

    public RecipesAdapter(Context context, HashMap<String, String> modelList) {
        this.inflater = LayoutInflater.from(context);
        this.modelList = modelList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredient_list_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        //holder.bindData();
        holder.ingredientName.setText(modelList.get(position)); // value for the given key
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientAmount;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ingredientName = (TextView) itemView.findViewById(R.id.textViewIngredientName);
            ingredientAmount = (TextView) itemView.findViewById(R.id.textViewIngredientAmount);
        }

    }

}
