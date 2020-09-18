package com.example.androidproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.Constats;
import com.example.androidproject.R;
import com.example.androidproject.Utils;
import com.example.androidproject.activities.DashBoardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends BaseFragment {

    private FloatingActionButton fab;
    private EditText edtUserName, edtPassword;
    private TextView txtForgotPassword, txtSignup;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        fab = view.findViewById(R.id.fab);
        txtForgotPassword = view.findViewById(R.id.txtForgot);
        edtUserName = view.findViewById(R.id.edt_userName);
        edtPassword = view.findViewById(R.id.edt_password);
        txtSignup = view.findViewById(R.id.txtSignup);


        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFragment(new SignupFragment(), true);

//                Toast.makeText(getContext(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateData();
            }
        });

        String currentUser= Utils.getInstance().readData(getContext(),Constats.USER_EMAIL);
        Log.e("CURRENT_USER",currentUser+"ss");
    }


    private void validateData() {
        String email = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();


        if (email.isEmpty()) {
            showToast("Please enter email");
        } else if (!validEmail(email)) {
            showToast("Enter valid email");
        } else if (password.isEmpty()) {
            showToast("Enter passeord");
        } else {
//            showToast("YOU ARE DONE.");
//            Intent intent=new Intent(getContext(), DashBoardActivity.class);
//            startActivity(intent);
            showDialoge();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        hideDialoge();
                        Intent intent = new Intent(getContext(), DashBoardActivity.class);
                        startActivity(intent);

                        //saving data locally
                        String currentUser = task.getResult().getUser().getEmail();
                        Utils.getInstance().saveData(getContext(), Constats.USER_EMAIL, currentUser);


                        getActivity().finish();


                        FirebaseAuth.getInstance().signOut();

                    } else {
                        hideDialoge();
                        showToast("" + task.getException().getMessage());
                        Log.e("ERROR", "signInWithEmail:failure " + task.getException().getMessage());

                    }
                }
            });
        }


    }


}
