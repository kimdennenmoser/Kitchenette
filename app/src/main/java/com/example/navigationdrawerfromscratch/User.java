package com.example.navigationdrawerfromscratch;

public class User {
    private String vname;
    private String nname;
    private String username;
    private String password;
    private String mail;

    public User() {
    }

    public String getVname(String vname) {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getNname(String nname) {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getUsername(String username) {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
