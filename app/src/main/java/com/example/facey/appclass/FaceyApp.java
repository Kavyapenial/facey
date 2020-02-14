package com.example.facey.appclass;

import android.app.Application;

import com.example.facey.config.DataManager;
import com.example.facey.config.Session;

public class FaceyApp extends Application {

    private  static final String API_KEY ="AIzaSyA3ELJ-2i9B_8M43IvGNkMuQWnlCOQfwaQ";
    @Override
    public void onCreate() {
        super.onCreate();
        Session.getSession(this);

        DataManager.getDataManager().init(this);

    }
}
