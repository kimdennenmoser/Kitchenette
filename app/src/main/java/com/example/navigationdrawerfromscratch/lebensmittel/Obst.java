package com.example.navigationdrawerfromscratch.lebensmittel;

public class Obst {

    private int ID;
    private String Name, status;
    private int image;

    public Obst() {
    }

    public Obst(int ID, String name, String status, int image) {
        this.ID = ID;
        Name = name;
        this.status = status;
        this.image = image;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
