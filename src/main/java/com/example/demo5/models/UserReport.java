package com.example.demo5.models;

import java.io.Serializable;

public class UserReport extends Report  implements Serializable {
    private static final long serialVersionUID = 1L;
    public UserReport(int reporterId, int reportedId, int Id, String explanation, Situation situation){
        super(reporterId, reportedId, Id, explanation, situation);
    }
}
