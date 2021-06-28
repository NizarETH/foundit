package com.app.foundit.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.app.foundit.R;


public class ErrorMessage {

    private ErrorMessage() {
        //
    }

    public static void display(Context context, String title, String message) {

        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setCancelable(false).setNeutralButton(R.string.ok, (dialog1, id1) -> dialog1.dismiss()).show();
    }

    public static void display(Context context) {
        ErrorMessage.display(context, context.getString(R.string.errors_generic_title),
                context.getString(R.string.errors_generic_title)
        );
    }

    public static void display(Context context, String message) {
        ErrorMessage.display(context, context.getString(R.string.title_ops),
                message);
    }
}
