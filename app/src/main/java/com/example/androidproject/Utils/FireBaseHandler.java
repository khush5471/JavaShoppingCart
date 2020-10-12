package com.example.androidproject.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    //returns the firebase database instance
    public FirebaseDatabase getFireBaseDataBase(){
        return FirebaseDatabase.getInstance();
    }

    //returns the firebase storage reference
    public FirebaseStorage  getFirebaseStorage(){
        return  FirebaseStorage.getInstance();
    }

    //this will return the firebase storage bucket reference to upload media
    public StorageReference getFirebaseStorageReference(){
        return getFirebaseStorage().getReferenceFromUrl(Constats.FIREBASE_BUCKET);
    }

    public DatabaseReference getFirebaseDatabaseReference(String reference){
        return getFireBaseDataBase().getReference(reference);
    }

}
