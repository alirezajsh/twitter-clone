package com.example.demo5.models;

import java.io.Serializable;

public class Admin extends Account  implements Serializable {
    private static final long serialVersionUID = 1L;
    Admin(String fullName, String phoneNumbere,  String userName, String emial, String password){
        super(fullName, phoneNumbere, userName, emial, password);
    }

}
