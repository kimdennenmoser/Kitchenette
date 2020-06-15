package com.example.navigationdrawerfromscratch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.account.recipes.Recipe;
import com.example.navigationdrawerfromscratch.account.recipes.RecipeInstruction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mCtx;
    private List<Recipe> recipeList;
    private RecipeAdapter.OnRecipeListener mOnRecipeListener;

    public RecipeAdapter(Context mCtx, List<Recipe> recipeList, RecipeAdapter.OnRecipeListener onRecipeListener) {
        this.mCtx = mCtx;
        this.recipeList = recipeList;
        this.mOnRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recipe_list_layout, null);
        return new RecipeAdapter.RecipeViewHolder(view, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getRecipeName());
        holder.preparationTime.setText(recipe.getPreparationTime());
        holder.recipeRating.setNumStars(recipe.getRecipeRating());
        Picasso.get().load(recipe.getRecipeImage()).into(holder.recipeImageView);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeImageView;
        TextView recipeName, preparationTime;
        OnRecipeListener onRecipeListener;
        RatingBar recipeRating;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);

            recipeImageView = (ImageView) itemView.findViewById(R.id.imgRecipe);
            recipeName = (TextView) itemView.findViewById(R.id.txtRecipeHeader);
            preparationTime = (TextView) itemView.findViewById(R.id.txtTime);
            recipeRating = (RatingBar) itemView.findViewById(R.id.recipeRating);

            this.onRecipeListener = onRecipeListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }


    public interface OnRecipeListener {
        void onRecipeClick(int position);

    }

    public void filterList(ArrayList<Recipe> filteredList){
        recipeList = filteredList;
        notifyDataSetChanged();
    }

    /*
    private Context context;
    private List<Recipe> recipesList;
    private OnRecipeListener onRecipeListener;

    public RecipeAdapter(Context context, List<Recipe> recipesList, OnRecipeListener onRecipeListener) {
        this.context = context;
        this.recipesList = recipesList;
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recipe_list_layout, viewGroup, false);
        return new ViewHolder(view, onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Recipe recipe = recipesList.get(position);
        viewHolder.textViewRecipeNameLayout.setText(recipe.getRecipeName());
        Picasso.get().load(recipe.getRecipeImage()).into(viewHolder.imageViewRecipeLayout);
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder  { //implements View.OnClickListener

        ImageView imageViewRecipeLayout;
        TextView textViewRecipeNameLayout;
        OnRecipeListener onRecipeListener;

        public ViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);
            imageViewRecipeLayout = (ImageView) itemView.findViewById(R.id.imageViewRecipeLayout);
            textViewRecipeNameLayout = (TextView) itemView.findViewById(R.id.textViewRecipeNameLayout);
            this.onRecipeListener = onRecipeListener;
           // itemView.setOnClickListener(this);
        }
      //  @Override
     //   public void onClick(View v) {
           // OnRecipeListener.onRecipeClick(getAdapterPosition());
     //   }
    }

    public interface OnRecipeListener{
        void onRecipeClick(int position);
    }

     */


}
