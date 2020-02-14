package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Branch {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("code")
    String code;


    public int getId(){return  id;}
    public void setId(int id){this.id = id;}

    public String getName(){return  name;}
    public void setName(String name){this.name =  name;}

    public String getCode(){return  code;}
    public void setCode(String code){this.code = code;}

}
