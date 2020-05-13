package com.example.navigationdrawerfromscratch.lebensmittel;

public class Gemüse {

    private int id;
    private String name, status;
    private int image;


    public Gemüse() {

    }

    public Gemüse(int id, String name, String status, int image) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
