package com.example.demo5.models;


import java.io.Serializable;
import java.util.Date;

public class BasicUser extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    public BasicUser(String fullName, String phoneNumber, String userName, String emial, String password){
        super(fullName, phoneNumber, userName, emial, password);
        this.madeDate = new Date();
        this.profile = null;
        this.biographi = "";
        this.symbol = "";

    }
    public BasicUser() {
        super();
    }


}
