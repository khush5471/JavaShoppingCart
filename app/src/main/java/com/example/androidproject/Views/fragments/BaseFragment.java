package com.example.androidproject.Views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androidproject.R;

import java.util.regex.Pattern;

public class BaseFragment extends Fragment {

    private Dialog mDialoge;

    //adds the fragment
    public void addFragment(Fragment fragment, Boolean isAdd) {

        if (isAdd) {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack("")
                    .commitAllowingStateLoss();
        } else {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(),
                message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 250);
        createAlertDialoge(message);
//        toast.show();
    }

    //checks if the email is valid or not
    public boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    //cretes the simple dialoge
    void createAlertDialoge(String Message) {
        Context context = getContext();
        final Dialog alertDialog = new Dialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setContentView(R.layout.dialoge_normal);
        alertDialog.setCancelable(false);
        TextView txtInfoAlertDialog = alertDialog.findViewById(R.id.txtInfoAlertDialog);
        Button buttonOk = alertDialog.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        txtInfoAlertDialog.setText(Message);
        alertDialog.show();
    }

    //shows the dialoge
    public void showDialoge() {
        mDialoge = new Dialog(getContext());
        mDialoge.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialoge.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                mProgressDialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mDialoge.setContentView(R.layout.dialog_progress);
        mDialoge.setCancelable(false);
        mDialoge.show();
    }


    //hides the dialoge
    public void hideDialoge() {
        if (mDialoge != null) {
            mDialoge.dismiss();
        }
    }
}
