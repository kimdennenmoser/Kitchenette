package com.example.navigationdrawerfromscratch;

import java.util.HashMap;

public class Rezept {

    private String recipeId;
    private HashMap<String, String> ingredientsList;
    private int preparationTime;
    private String category;
    //private String bewertung; //nice-to-have

    public Rezept(String recipeId, HashMap<String, String> ingredientsList, int preparationTime, String category, String bewertung) {
        this.recipeId = recipeId;
        this.ingredientsList = ingredientsList;
        this.preparationTime = preparationTime;
        this.category = category;
        //this.bewertung = bewertung;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public HashMap<String, String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(HashMap<String, String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
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
