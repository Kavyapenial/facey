package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;


    @SerializedName("credit")
    int regId;


    @SerializedName("profile")
    String profilePic;

    public int getId(){return  id;}
    public void setId(int id){this.id = id;}

    public String getName(){return  name;}
    public void setName(String name){this.name =  name;}

    public int getRegId(){return  regId;}
    public void setRegId(int regId){this.regId = regId;}

    public String getProfilePic(){return  profilePic;}
    public void setProfilePic(String profilePic){this.profilePic =  profilePic;}
}
