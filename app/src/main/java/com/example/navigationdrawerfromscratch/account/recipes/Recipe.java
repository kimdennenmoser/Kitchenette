package com.example.navigationdrawerfromscratch.account.recipes;

import android.media.Rating;
import android.widget.RatingBar;

import java.util.HashMap;

public class Recipe {

    private String recipeId;
    private String recipeName;
    private String preparationTime;
    private HashMap<String, String> ingredientsMap;
    private String instructions;
    private String category;
    private String recipeImage;
    private int recipeRating;
    private String portions;
    private String creator;
    //private String bewertung; //nice-to-have


    public Recipe(String recipeId, String recipeName, String preparationTime, HashMap<String, String> ingredientsMap, String instructions, String category, String recipeImage, int recipeRating, String portions, String creator) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.ingredientsMap = ingredientsMap;
        this.instructions = instructions;
        this.category = category;
        this.recipeImage = recipeImage;
        this.recipeRating = recipeRating;
        this.portions = portions;
        this.creator = creator;
    }

    public Recipe() {

    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    public HashMap<String, String> getIngredientsMap() {
        return ingredientsMap;
    }

    public void setIngredientsMap(HashMap<String, String> ingredientsMap) {
        this.ingredientsMap = ingredientsMap;
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

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public int getRecipeRating() {
        return recipeRating;
    }

    public void setRecipeRating(int recipeRating) {
        this.recipeRating = recipeRating;
    }

    public String getPortions() {
        return portions;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId='" + recipeId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", preparationTime='" + preparationTime + '\'' +
                ", ingredientsMap=" + ingredientsMap +
                ", instructions='" + instructions + '\'' +
                ", category='" + category + '\'' +
                ", recipeImage='" + recipeImage + '\'' +
                ", recipeRating=" + recipeRating +
                ", portions='" + portions + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }

}
