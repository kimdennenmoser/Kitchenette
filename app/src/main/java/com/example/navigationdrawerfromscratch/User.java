package com.example.navigationdrawerfromscratch;

public class User {
    private String userid;
    private String vname;
    private String nname;
    private String username;
    private String password;
    private String mail;

    public User(String userid, String vname, String nname, String username, String password, String mail) {
        this.userid = userid;
        this.vname = vname;
        this.nname = nname;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public User() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
