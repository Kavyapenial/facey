package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("token")
    String accessToken;

    @SerializedName("name")
    String name;

    @SerializedName("designation")
    String designation;


    @SerializedName("email")
    String email;

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public  String getName(){ return  name;}
    public  void setName(String name) {this.name = name;}

    public String getDesignation(){return  designation;}
    public  void setDesignation(String designation) {this.designation =  designation;}

    public  String getEmail(){return  email;}
    public void setEmail(String email){this.email = email;}

}
