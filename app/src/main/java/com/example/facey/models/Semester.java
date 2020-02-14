package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Semester {


    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;


    public int getId(){return  id;}
    public void setId(int id){this.id = id;}

    public String getName(){return  name;}
    public void setName(String name){this.name =  name;}
}
