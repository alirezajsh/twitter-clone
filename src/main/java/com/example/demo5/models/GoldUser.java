package com.example.demo5.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GoldUser extends PremiumUser implements Serializable {
    private static final long serialVersionUID = 1L;
    public GoldUser(String fullName, String phoneNumbere, String userName, String email, String password, Date birhtDate, int userId, Date madeDate, int initialBalnce, int tokenNumber, String biographi, ArrayList<Integer> posts, ArrayList<Integer> followers, ArrayList<Integer> following, ArrayList<Integer> likedList, File userBadge, boolean isBlocked){
        super(fullName, phoneNumbere, userName, email,password, birhtDate, userId, madeDate, initialBalnce, tokenNumber, biographi, posts, followers, following, likedList, userBadge, isBlocked);

    }

    public GoldUser(String fullName, String phone, String username, String email, String password){
        super(fullName, phone, username, email, password);
    }
    public GoldUser() {
        super();
    }
}
