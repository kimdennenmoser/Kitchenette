package com.example.navigationdrawerfromscratch.account.recipes;

public class Zutat {

    public String amount;
    public String name;

    public Zutat(String amount, String name) {
        this.amount = amount;
        this.name = name;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
