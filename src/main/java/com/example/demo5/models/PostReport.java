package com.example.demo5.models;

import java.io.Serializable;

public class PostReport extends Report  implements Serializable {
    private static final long serialVersionUID = 1L;
    public PostReport(int reporterId, int reportedId, int Id, String explanation, Situation situation){
        super(reporterId, reportedId, Id, explanation, situation);
    }


}
