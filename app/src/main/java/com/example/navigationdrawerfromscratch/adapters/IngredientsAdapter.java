package com.example.navigationdrawerfromscratch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.account.recipes.Zutat;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {


    private Context context;
    private List<Zutat> ingredientsList;

    public IngredientsAdapter(Context context, List<Zutat> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientsAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_list_layout, null);
        return new IngredientsAdapter.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientViewHolder holder, int position) {
        Zutat zutat = ingredientsList.get(position);

        holder.ingredientAmount.setText(zutat.getAmount());
        holder.ingredientUnit.setText(zutat.getUnit());
        holder.ingredientName.setText(zutat.getName());
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class IngredientViewHolder extends  RecyclerView.ViewHolder {

        TextView ingredientAmount;
        TextView ingredientUnit;
        TextView ingredientName;


        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientAmount = itemView.findViewById(R.id.textViewIngredientAmount);
            ingredientUnit = itemView.findViewById(R.id.textViewIngredientUnit);
            ingredientName = itemView.findViewById(R.id.textViewIngredientName);
        }
    }

}