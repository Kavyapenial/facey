package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Batch {

    @SerializedName("id")
    int id;

    @SerializedName("year")
    int year;

    @SerializedName("branch")
    String branch;

    @SerializedName("sem")
    int sem;


    public int getId(){return  id;}
    public void setId(int id){this.id = id;}


    public int getYear(){return  year;}
    public void setYear(int year){this.year = year;}

    public String getBranch(){return  branch;}
    public void setBranch(String branch){this.branch = branch;}

    public int getSem(){return  sem;}
    public void setSem(int sem){this.sem = sem;}

}
