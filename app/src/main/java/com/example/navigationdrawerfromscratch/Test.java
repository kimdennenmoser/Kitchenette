package com.example.navigationdrawerfromscratch;

import java.util.List;

public class Test {

    private List<String> IDs;
    private String name;

    public Test(List<String> IDs, String name) {
        this.IDs = IDs;
        this.name = name;
    }

    public List<String> getIDs() {
        return IDs;
    }

    public void setIDs(List<String> IDs) {
        this.IDs = IDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
