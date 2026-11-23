package com.example.demo5.exceptions;

public class UserAlreadyFollowedException extends Exception {
    public UserAlreadyFollowedException(String message) {
        super(message);
    }
}