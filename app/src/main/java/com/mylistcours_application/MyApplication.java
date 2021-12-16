package com.mylistcours_application;

import android.app.Application;

import com.mylistcours_application.database.DatabaseHelper;

public class MyApplication extends Application {

    // Declare 1 objet type Application qui sera utilisé en tanque Singleton
    private static MyApplication singleton_application;

    DatabaseHelper databaseHelper;

    // Renvoyer l'intance de l'app
    public static MyApplication getInstance(){
        return singleton_application;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        singleton_application = this;
        initialDBHelper();
    }

    // methode GETTER pour l'objet DatabaseHelper
    public DatabaseHelper getDatabaseHelper(){
        return databaseHelper;
    }

    // Methode pour initialisation le Database Helper
    public void initialDBHelper(){
        // on creer 1 objet type DatabaseHelper pour appeler la BD afin de memoriser ce musicien
         databaseHelper = new DatabaseHelper(this);

    }

}
