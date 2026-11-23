package com.example.demo5.models;


import java.util.ArrayList;
import java.util.Date;

public abstract class PremiumUser extends User{
    private static final long serialVersionUID = 1L;
    public PremiumUser() {
        super();
    }
    public PremiumUser(String fullName, String phone, String username, String email, String password){
        super(fullName,phone, username, email, password);
    }
    PremiumUser(String fullName, String phoneNumber,  String userName, String email, String password, Date birhtDate, int userId, Date madeDate, int initialBalnce, int tokenNumber, String biographi, ArrayList<Integer> posts, ArrayList<Integer> followers, ArrayList<Integer> following, ArrayList<Integer> likedList, File userBadge, boolean isBlocked){
        super(fullName, phoneNumber, userName, email, password, userId);
        this.profile = profile;
        this.birthDate = birthDate;
        this.madeDate = madeDate;
        this.initialBalance = initialBalnce;
        this.tokenNumber = tokenNumber;
        this.biographi = biographi;
        this.posts = posts;
        this.followers = followers;
        this.following = following;
        this.likedList = likedList;
        this.userBadge = userBadge;
        this.isBlocked = isBlocked;
    }
}
