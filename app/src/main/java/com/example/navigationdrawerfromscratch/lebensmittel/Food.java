package com.example.navigationdrawerfromscratch.lebensmittel;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.navigationdrawerfromscratch.R;

public class Food {


    private String name, info, id, image, category;

    public Food() {
    }

    public Food(String name, String info, String id, String image, String category) {
        this.name = name;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
}
