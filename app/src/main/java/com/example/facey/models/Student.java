package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;


    @SerializedName("reg_id")
    int regId;


    @SerializedName("profile")
    String profilePic;


    @SerializedName("is_present")
    Boolean isPrescent;

    public int getId(){return  id;}
    public void setId(int id){this.id = id;}

    public String getName(){return  name;}
    public void setName(String name){this.name =  name;}

    public int getRegId(){return  regId;}
    public void setRegId(int regId){this.regId = regId;}

    public String getProfilePic(){return  profilePic;}
    public void setProfilePic(String profilePic){this.profilePic =  profilePic;}

    public Boolean getPrescent() {
        return isPrescent;
    }

    public void setPrescent(Boolean prescent) {
        isPrescent = prescent;
    }
}
