package com.example.navigationdrawerfromscratch;

public class Test {

    private String[] IDs;
    private String name;

    public Test(String[] IDs, String name) {
        this.IDs = IDs;
        this.name = name;
    }

    public String[] getIDs() {
        return IDs;
    }

    public void setIDs(String[] IDs) {
        this.IDs = IDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
