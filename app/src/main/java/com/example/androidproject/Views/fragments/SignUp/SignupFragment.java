package com.example.androidproject.Views.fragments.SignUp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.R;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Utils.DialogeHelper;
import com.example.androidproject.Utils.FireBaseHandler;
import com.example.androidproject.Utils.Utils;
import com.example.androidproject.Views.fragments.BaseFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SignupFragment extends BaseFragment implements View.OnClickListener {

    private EditText edt_name, edt_email, edt_password, edt_confirm_password;
    private FloatingActionButton fabButton;
    private ArrayList<String> mUserList;
    private String mImgUrl;
    private Bitmap mImgBitmap;
    private ImageView mImgUser;
    private SignupViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        init(view);
        setListners();
        attachObservers();


        mViewModel.getUsersListFromDataBase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgUser: {
                if (Utils.getInstance().checkPermission(getContext())) {
                    Utils.getInstance().selectPhoto(SignupFragment.this);
                }else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, Constats.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                }
            }
            break;
            case R.id.fab: {
                String name = edt_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String confirmPassword = edt_confirm_password.getText().toString().trim();
                mViewModel.signupUsers(name, email, password, confirmPassword, mImgUrl, mUserList, getActivity());
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Constats.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE){

            if (grantResults!=null && (grantResults[0]== PackageManager.PERMISSION_GRANTED)){
                Utils.getInstance().selectPhoto(SignupFragment.this);
            }else if (grantResults!=null && (grantResults[0]== PackageManager.PERMISSION_DENIED)){
                DialogeHelper.getInstance().createPermissionDeniedDialog(getActivity());
            }
        }
    }

    /*Initialze the variables*/
    private void init(View view) {
        mUserList = new ArrayList<>();
        edt_name = view.findViewById(R.id.edt_name);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        edt_confirm_password = view.findViewById(R.id.edt_confirm_password);
        mImgUser = view.findViewById(R.id.imgUser);
        fabButton = view.findViewById(R.id.fab);
    }

    /*Setting the click listners on views
     * */
    private void setListners() {
        fabButton.setOnClickListener(this);
        mImgUser.setOnClickListener(this);
    }


    /*Attaching the observer , so as to get the response data from the request*/
    private void attachObservers() {

        mViewModel.mRegisteredUserList.observe(getActivity(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> list) {
                Log.e("USER_LIST", "SIZE " + list.size());
                mUserList = list;
            }
        });


        mViewModel.mSuccessResponse.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
        mViewModel.mErrorResponse.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });


        mViewModel.mIsLoading.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showDialoge();
                } else {
                    hideDialoge();
                }
            }
        });
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


    /*After selecting image from storage gets its path and upload it to the firebase*/
    private void handleGalleryData(Intent data) {

        Uri selectedImage = data.getData();

        String imagePath = Utils.getInstance().getPathFromURI(getContext(), selectedImage);
        Log.e("image_path", imagePath);

        Utils.getInstance().downloadImageByGlide(getContext(), imagePath, mImgUser);
        mImgBitmap = BitmapFactory.decodeFile(imagePath);
        uploadImageToFirebase();
    }

    private void uploadImageToFirebase() {
        showDialoge();
//        mStorageChildReference = mStorageReference.child("user_profile_photo/" + edt_email.getText().toString() + ".jpg");
        final StorageReference mStorageChildReference = FireBaseHandler.getInstance().getFirebaseStorageReference().child("user_profile_photo/" + System.currentTimeMillis() + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bMap = BitmapFactory.decodeFile(imagePath);
        mImgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteData = baos.toByteArray();
        UploadTask uploadTask = mStorageChildReference.putBytes(byteData);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("image ", "failure" + e.getMessage());
                hideDialoge();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mStorageChildReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        hideDialoge();
                        mImgUrl = uri.toString();
                        Log.e("image ", "success  " + uri.toString());
                    }
                });

            }
        });
    }


}
