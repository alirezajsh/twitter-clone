package com.example.demo5.models;


import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int userId;
    protected String fullName;
    protected String phoneNumber;
    protected String userName;
    protected String email;
    protected Date birthDate;
    protected File profile;
    protected String password;
    protected Date madeDate;
    private static int accountNumber = 0;
    public Account() {
        this.madeDate = new Date();
        this.profile = null;
    }
    Account(String fullName, String phoneNumber,  String userName, String email, String password){
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

}
