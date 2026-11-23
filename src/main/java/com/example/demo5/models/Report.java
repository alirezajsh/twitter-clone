package com.example.demo5.models;

import java.io.Serializable;
import java.util.Date;

public class Report  implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected  int reporterId;
    protected  int reportedId;
    protected  int Id;
    protected  Situation situation;
    protected  String explanation;
    protected  Date madeDate;
    //(reporterId, reportedId, Id, explanation)
    public Report(int reporterId, int reportedId, int Id, String explanation, Situation situation){
        this.reportedId = reportedId;
        this.reporterId = reporterId;
        this.Id = Id;
        this.explanation = explanation;
        this.situation = situation;
    }
    public  int getId(){return this.Id;}
    public int getReporterId(){return this.reporterId;}
    public int getReportedId(){return this.reportedId;}
    public String getExplanation(){return this.explanation;}

    public void rejectReport(){
        this.situation = Situation.REJECTED;
    }

    public void confirmedReport(){
        this.situation = Situation.CONFIRMED;
    }

    public void setReportId(int id){this.Id = id;}

    public Date getMadeDate(){return this.madeDate;}
}
