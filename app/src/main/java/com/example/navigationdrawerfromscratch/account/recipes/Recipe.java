package com.example.navigationdrawerfromscratch.account.recipes;

import java.util.HashMap;

public class Recipe {

    private String recipeId;
    private String recipeName;
    private String preparationTime;
    private String ingredientsList; //private HashMap<String, String> ingredientsList;
   private  String instructions;
    private String category;
    private String recipeImage;
    private String recipeRating;
    //private String bewertung; //nice-to-have


    public Recipe(String recipeId, String recipeName, String preparationTime, String ingredientsList, String instructions, String category, String recipeImage, String recipeRating) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
        this.category = category;
        this.recipeImage = recipeImage;
        this.recipeRating = recipeRating;
    }

    public Recipe() {

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

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeRating() {
        return recipeRating;
    }

    public void setRecipeRating(String recipeRating) {
        this.recipeRating = recipeRating;
    }
}
