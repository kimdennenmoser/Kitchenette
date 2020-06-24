package com.example.navigationdrawerfromscratch.lebensmittel;


import android.os.Build;


import androidx.annotation.RequiresApi;

import com.example.navigationdrawerfromscratch.R;

import java.util.Objects;

public class Food {


    private String name, id, image, category;

    public Food() {
    }

    public Food(String name, String id, String image, String category) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) &&
                Objects.equals(id, food.id) &&
                Objects.equals(image, food.image) &&
                Objects.equals(category, food.category);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name, id, image, category);
    }
}
