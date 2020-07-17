package com.example.medictoken;

public class TokenModel {

    String name, mobno;

    public TokenModel(String name, String mobno) {
        this.name = name;
        this.mobno = mobno;
    }

    public TokenModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }
}
