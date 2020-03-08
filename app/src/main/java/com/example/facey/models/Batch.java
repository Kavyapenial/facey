package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Batch {

    @SerializedName("id")
    int id;

    @SerializedName("year")
    int year;

    @SerializedName("branch")
    Branch branch;

    @SerializedName("sem")
    Semester sem;


    public int getId(){return  id;}
    public void setId(int id){this.id = id;}


    public int getYear(){return  year;}
    public void setYear(int year){this.year = year;}

    public Branch getBranch(){return  branch;}
    public void setBranch(Branch branch){this.branch = branch;}

    public Semester getSem(){return  sem;}
    public void setSem(Semester sem){this.sem = sem;}

}
