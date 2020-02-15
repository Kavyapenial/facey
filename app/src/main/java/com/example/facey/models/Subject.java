package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Subject {


    @SerializedName("id")
    int id;

    @SerializedName("subject_code")
    String subjectCode;

    @SerializedName("teacher")
    int teacher;

    @SerializedName("branch")
    int branch;

    @SerializedName("sem")
    int sem;

    @SerializedName("subject")
    String subjectName;

    @SerializedName("credit")
    int credit;


    public int getId(){return  id;}
    public void setId(int id){this.id = id;}

    public String getSubjectCode(){return  subjectCode;}
    public void setSubjectCode(String subjectCode){this.subjectCode = subjectCode;}

    public int getTeacher(){return  teacher;}
    public void setTeacher(int teacher){this.teacher = teacher;}

    public int getBranch(){return  branch;}
    public void setBranch(int branch){this.branch=branch;}

    public int getSem(){return  sem;}
    public void setSem(int sem){this.sem = sem;}

    public String getSubjectName(){return  subjectName;}
    public void setSubjectName(String subjectName){this.subjectName = subjectName;}

    public int getCredit(){return  credit;}
    public void setCredit(int credit){this.credit = credit;}



}

