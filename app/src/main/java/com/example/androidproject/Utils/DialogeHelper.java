package com.example.androidproject.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;

public class DialogeHelper {

    private static DialogeHelper mDialogeHelper = new DialogeHelper();

    private DialogeHelper() {
    }

    public static DialogeHelper getInstance() {
        return mDialogeHelper;
    }

    public void createPermissionDeniedDialog(final Activity activity) {
        final Dialog alertDialog = new Dialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);

        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setContentView(R.layout.dialoge_permission_denied);
        alertDialog.setCancelable(false);
        ImageView imageCancel = alertDialog.findViewById(R.id.imageDismissDialog);
        TextView txtYesDialog = alertDialog.findViewById(R.id.txtYesDialog);
        TextView txtNoDialog = alertDialog.findViewById(R.id.txtNoDialog);
        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });


        txtNoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        txtYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Open the specific App Info page:
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivityForResult(intent, 112);

                } catch (ActivityNotFoundException e){
                    //e.printStackTrace();
                    //Open the generic Apps page:
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    activity.startActivityForResult(intent, 112);

                }
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }
}
