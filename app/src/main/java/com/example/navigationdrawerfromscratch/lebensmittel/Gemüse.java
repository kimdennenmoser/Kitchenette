package com.example.navigationdrawerfromscratch.lebensmittel;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.navigationdrawerfromscratch.R;

public class Gemüse {


    private String name, info, id, image;


    public Gemüse() {
    }

    public Gemüse(String name, String info, String id, String image) {
        this.name = name;
        this.info = info;
        this.id = id;
        this.image = image;
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
}
