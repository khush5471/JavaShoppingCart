package com.example.androidproject.Views.fragments.SignIn;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.R;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Utils.Utils;
import com.example.androidproject.Views.activities.DashBoardActivity;
import com.example.androidproject.Views.fragments.BaseFragment;
import com.example.androidproject.Views.fragments.SignUp.SignupFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends BaseFragment implements View.OnClickListener {

    private FloatingActionButton mLoginButton;
    private EditText mEdtUserName, mEdtPassword;
    private TextView txtForgotPassword, mTxtSignup;
    private SignInViewModel mSignInViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSignInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        init(view);
        attachObserver();
        setListners();

        String currentUser = Utils.getInstance().readData(getContext(), Constats.USER_EMAIL);
        Log.e("CURRENT_USER", currentUser + "ss");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab: {
                String email = mEdtUserName.getText().toString();
                String password = mEdtPassword.getText().toString();
                mSignInViewModel.loginUserUsingEmailPassword(email, password, getActivity());
            }
            break;
            case R.id.txtSignup: {
                addFragment(new SignupFragment(), true);
            }
            break;
        }
    }

    /*Initialize all the views
     * */
    private void init(View view) {
        mLoginButton = view.findViewById(R.id.fab);
        txtForgotPassword = view.findViewById(R.id.txtForgot);
        mEdtUserName = view.findViewById(R.id.edt_userName);
        mEdtPassword = view.findViewById(R.id.edt_password);
        mTxtSignup = view.findViewById(R.id.txtSignup);
    }

    /*Settomg listners on views
     * */
    private void setListners() {
        mLoginButton.setOnClickListener(this);
        mTxtSignup.setOnClickListener(this);
    }

    /*Attaching obsrvers for the requests and respose
     * */
    private void attachObserver() {

        mSignInViewModel.mLoginResponse.observe(getActivity(), new Observer<Task<AuthResult>>() {
            @Override
            public void onChanged(Task<AuthResult> authResultTask) {


                if (authResultTask.isSuccessful()) {

                    Intent intent = new Intent(getContext(), DashBoardActivity.class);
                    startActivity(intent);

                    //saving data locally
                    String currentUser = authResultTask.getResult().getUser().getEmail();
                    Utils.getInstance().saveData(getContext(), Constats.USER_EMAIL, currentUser);

                    getActivity().finish();
                    FirebaseAuth.getInstance().signOut();

                } else {
                    showToast("" + authResultTask.getException().getMessage());
                    Log.e("ERROR", "signInWithEmail:failure " + authResultTask.getException().getMessage());

                }
            }
        });

        mSignInViewModel.mIsLoading.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showDialoge();
                } else {
                    hideDialoge();
                }
            }
        });

        mSignInViewModel.mErrorResponse.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
    }



}
