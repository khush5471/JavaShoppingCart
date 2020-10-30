package com.example.androidproject.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidproject.R;

import java.util.regex.Pattern;

public class Utils {

    private static Utils utils = new Utils();

    private Utils() {
    }

    public static Utils getInstance() {
        return utils;
    }


    //checks if internet available or not
    public boolean isInternetAvailable(Context context) {

        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conManager.getActiveNetworkInfo() != null && conManager.getActiveNetworkInfo().isConnected();
    }

    public void selectPhoto(Fragment activity) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, Constats.REQUEST_GALLERY);
    }

    public String getPathFromURI(Context context, Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        String path;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);


        try {
            if (cursor != null) {
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();
                path = cursor.getString(columnIndex);
                cursor.close();
                return path;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return uri.getPath();
    }

    public void downloadImageByGlide(Context context, String url, ImageView imageView) {
        if (!url.isEmpty()) {
            Glide.with(context)
                    .load(url)
//                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .thumbnail(0.01f)
                    .into(imageView);
        } else {
            Log.e("URL", "EMPTY");
        }

    }


    public void saveData(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String readData(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(
                R.string.preference_file_key), Context.MODE_PRIVATE);
        String getReturnValue = sharedPref.getString(key, "");
        return getReturnValue;
    }


    //checks if the email is valid or not
    public boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    /*CHecks the permissions*/
    public boolean checkPermission(Context context) {

        if (context != null) {
            int currentAPi = Build.VERSION.SDK_INT;
            if (currentAPi >= android.os.Build.VERSION_CODES.M) {
                return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            }else {
                return true;
            }
        }else {
            return false;
        }


    }
}
