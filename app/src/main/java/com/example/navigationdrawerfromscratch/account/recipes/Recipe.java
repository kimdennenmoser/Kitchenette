package com.example.navigationdrawerfromscratch.account.recipes;

import java.util.HashMap;

public class Recipe {

    private String recipeId;
    private String recipeName;
    private String ingredientsList; //private HashMap<String, String> ingredientsList;
    private String preparationTime;
    private String category;
    //private String bewertung; //nice-to-have

    public Recipe(String recipeId, String recipeName, String preparationTime, String ingredientsList, String category, String bewertung) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.ingredientsList = ingredientsList;
        this.category = category;
        //this.bewertung = bewertung;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /*
    public String getBewertung() {
        return bewertung;
    }

    public void setBewertung(String bewertung) {
        this.bewertung = bewertung;
    }
     */
}
