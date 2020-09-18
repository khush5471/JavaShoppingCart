package com.example.androidproject.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.BrandListData;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.R;
import com.example.androidproject.Users;
import com.example.androidproject.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends BaseFragment {

    private EditText edt_name, edt_email, edt_password, edt_confirm_password;
    private FloatingActionButton fabButton;
    private ArrayList<String> mUserList;
    private boolean mIsUserAlreadyExists = false;
    private String mImgUrl;
    private Bitmap mImgBitmap;
    private String mName,mEmail,mPassword;


    //for database
    FirebaseDatabase database;
    DatabaseReference myUserReferences;
    FirebaseStorage mFirebaseStorage;
    StorageReference mStorageReference;
    StorageReference mStorageChildReference;


    //for authentication
    private FirebaseAuth mAuth;
    private ImageView mImgUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        myUserReferences = database.getReference("USERS");
        mAuth = FirebaseAuth.getInstance();

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReferenceFromUrl(Constats.FIREBASE_BUCKET);


        mUserList = new ArrayList<>();

        edt_name = view.findViewById(R.id.edt_name);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        edt_confirm_password = view.findViewById(R.id.edt_confirm_password);
        mImgUser = view.findViewById(R.id.imgUser);

        fabButton = view.findViewById(R.id.fab);


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        mImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().selectPhoto(SignupFragment.this);
            }
        });

        getUsers();

    }


    private void getUsers() {
        myUserReferences.child("ACCOUNTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("SNAPSHOT", "" + snapshot.getValue());


                //first check if data on firebase empty or not
                Users data = snapshot.getValue(Users.class);
                if (data == null) {
                    Toast.makeText(getContext(), "Data Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mUserList.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Users userData = child.getValue(Users.class);
                        mUserList.add(child.getKey());
                        Log.e("okok", "Title = " + child.getKey() + " " + userData.getEmail() + " length " + mUserList.size());
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void validateData() {
        String name = edt_name.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String confirmPassword = edt_confirm_password.getText().toString().trim();



        if (name.isEmpty() || name.length() < 3) {
            showToast("Enter name and it should be atlest 2 characters");
        } else if (email.isEmpty()) {
            showToast("Enter email");
        } else if (!validEmail(email)) {
            showToast("Enter valid email");
        } else if (password.isEmpty()) {
            showToast("Enter password");
        } else if (password.length() < 5) {
            showToast("Password should be atleast 5 characters");
        } else if (confirmPassword.isEmpty()) {
            showToast("please enter confirm passwrd");
        } else if (!password.equals(confirmPassword)) {
            showToast("confirm password and password donot match");
        } else {

//            myUserReferences.child("ACCOUNTS").setValue("username",email);
//            myUserReferences.child("ACCOUNTS").setValue("password",password);

            Map<String, Users> users = new HashMap<>();


//            getActivity().onBackPressed();
            mIsUserAlreadyExists = false;

            if (!mUserList.isEmpty()) {
                for (String username : mUserList) {
                    if (username.equals(name)) {
                        mIsUserAlreadyExists = true;
                    }
                }
            }

            if (!mIsUserAlreadyExists) {
                showDialoge();
                mName=name;
                mEmail=email;
                mPassword=password;
                uploadImageToFirebase();

            } else {
                showToast("Username already exists");
                hideDialoge();

            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("REUSLT IS ", "ok " + requestCode + " " + resultCode);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == Constats.REQUEST_GALLERY) {
                Log.e("from gallery", "ok");
                handleGalleryData(data);
            }

        }
    }


    private void handleGalleryData(Intent data) {

        Uri selectedImage = data.getData();

        String imagePath = Utils.getInstance().getPathFromURI(getContext(), selectedImage);
        Log.e("image_path", imagePath);
        Utils.getInstance().downloadImageByGlide(getContext(), imagePath, mImgUser);
//        Utils.getInstance().downloadImageByGlide(getContext(), imagePath, image_user, context?.getDrawable(R.drawable.bg_grey))

        mImgBitmap=BitmapFactory.decodeFile(imagePath);


    }

    private void uploadImageToFirebase(){
        mStorageChildReference = mStorageReference.child("user_profile_photo/" + edt_email.getText().toString() + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bMap = BitmapFactory.decodeFile(imagePath);
        mImgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteData = baos.toByteArray();

        UploadTask uploadTask = mStorageChildReference.putBytes(byteData);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("image ", "failure");
                hideDialoge();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mStorageChildReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mImgUrl =uri.toString();
                        Log.e("image ", "success  " + uri.toString());
                        addAccountToFirebase();

                    }
                });

            }
        });
    }

    private void  addAccountToFirebase(){

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myUserReferences.child("ACCOUNTS").child(mName).setValue(new Users(mEmail, mPassword, mImgUrl,mName,new ArrayList<BrandListData>()));
                            getUsers();
                            showToast("Account created");
                        } else {
                            Log.e("ERROR", "" + task.getException().getMessage());
                            showToast(task.getException().getMessage());
                        }

                    }
                });
        hideDialoge();

    }
}
