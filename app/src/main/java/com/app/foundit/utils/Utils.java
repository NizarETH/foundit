package com.app.foundit.utils;

import android.app.Activity;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String currentDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        return date;
    }
    public static File getFirstWritableDirectory() {
        File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (file1.exists())
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        else if (file2.exists())
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        else if (file3.exists()) return Environment.getExternalStorageDirectory();
        else
            return Environment.getExternalStorageDirectory();
    }

    public static void hideSoftKeyboard(FragmentActivity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

    }
}
