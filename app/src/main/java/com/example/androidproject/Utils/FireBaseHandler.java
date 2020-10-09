package com.example.androidproject.Utils;

import com.google.firebase.auth.FirebaseAuth;

/*
* Making static class for firebase handler so that firebase tasks can be done using this handler
* */
public class FireBaseHandler {

    private static FireBaseHandler fireBaseHandler = new FireBaseHandler();

    private FireBaseHandler() {
    }

    public static FireBaseHandler getInstance() {
        return fireBaseHandler;
    }

    //returns the instance of this class
    public FirebaseAuth getFireBaseAuth(){
        return FirebaseAuth.getInstance();
    }


}
