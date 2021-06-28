package com.app.foundit.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    private static Uri contentUri = null;

    private Context context;

    public FileUtils(Context context) {
        this.context=context;
    }

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            }
        }
    }

    @SuppressLint("NewApi")
    public  String getPath( Uri uri) {
        // check here to KITKAT or new version
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String selection = null;
        String[] selectionArgs = null;
        // DocumentProvider
        if (isKitKat ) {
            // ExternalStorageProvider

            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                String fullPath = getPathFromExtSD(split);
                if (fullPath != "") return fullPath;
                else return null;
            }


            // DownloadsProvider

            if (isDownloadsDocument(uri)) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String id;
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String fileName = cursor.getString(0);
                        String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                        if (!TextUtils.isEmpty(path)) return path;
                    }
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                id = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) return id.replaceFirst("raw:", "");
                    String[] contentUriPrefixesToTry = new String[]{
                            "content://downloads/public_downloads",
                            "content://downloads/my_downloads"
                    };
                    for (String contentUriPrefix : contentUriPrefixesToTry)
                        try {
                            Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));


                            return getDataColumn(context, contentUri, null, null);
                        } catch (NumberFormatException e) {
                            //In Android 8 and Android P the id is not a number
                            return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                        }


                }
            } else {
                String id = DocumentsContract.getDocumentId(uri);

                if (id.startsWith("raw:")) return id.replaceFirst("raw:", "");
                try {
                    contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (contentUri != null) return getDataColumn(context, contentUri, null, null);
            }


            // MediaProvider
            if (isMediaDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                else if ("video".equals(type))
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                else if ("audio".equals(type))
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }

            if (isGoogleDriveUri(uri)) return getDriveFilePath(uri);

            if(isWhatsAppFile(uri)) return getFilePathForWhatsApp(uri);


            if ("content".equalsIgnoreCase(uri.getScheme())) {

                if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();
                if (isGoogleDriveUri(uri)) return getDriveFilePath(uri);
                // return getFilePathFromURI(context,uri);
                // return getRealPathFromURI(context,uri);
                if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    return copyFileToInternalStorage(uri, "userfiles");
                else
                    return getDataColumn(context, uri, null, null);

            }
            if ("file".equalsIgnoreCase(uri.getScheme())) return uri.getPath();
        }
        else {

            if(isWhatsAppFile(uri)) return getFilePathForWhatsApp(uri);

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) return cursor.getString(column_index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }




        return null;
    }

    private  boolean fileExists(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }

    private String getPathFromExtSD(String[] pathData) {
        String type = pathData[0];
        String relativePath = "/" + pathData[1];
        String fullPath = "";

        // on my Sony devices (4.4.4 & 5.1.1), `type` is a dynamic string
        // something like "71F8-2C0A", some kind of unique id per storage
        // don't know any API that can get the root path of that storage based on its id.
        //
        // so no "primary" type, but let the check here for other devices
        if ("primary".equalsIgnoreCase(type)) {
            fullPath = Environment.getExternalStorageDirectory() + relativePath;
            if (fileExists(fullPath)) return fullPath;
        }

        // Environment.isExternalStorageRemovable() is `true` for external and internal storage
        // so we cannot relay on it.
        //
        // instead, for each possible path, check if file exists
        // we'll start with secondary storage as this could be our (physically) removable sd card
        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
        if (fileExists(fullPath)) return fullPath;

        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
        if (fileExists(fullPath)) return fullPath;

        return fullPath;
    }

    private String getDriveFilePath(Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) outputStream.write(buffers, 0, read);
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    /***
     * Used for Android Q+
     * @param uri
     * @param newDirName if you want to create a directory, you can set this variable
     * @return
     */
    private String copyFileToInternalStorage(Uri uri,String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = context.getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE
        }, null, null, null);


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if(!newDirName.equals("")) {
            File dir = new File(context.getFilesDir() + "/" + newDirName);
            if (!dir.exists()) dir.mkdir();
            output = new File(context.getFilesDir() + "/" + newDirName + "/" + name);
        }
        else output = new File(context.getFilesDir() + "/" + name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) outputStream.write(buffers, 0, read);

            inputStream.close();
            outputStream.close();

        }
        catch (Exception e) {

            Log.e("Exception", e.getMessage());
        }

        return output.getPath();
    }

    private String getFilePathForWhatsApp(Uri uri){
        return  copyFileToInternalStorage(uri,"whatsapp");
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    private  boolean isExternalStorageDocument(Uri uri) {
        if(uri.getAuthority() != null)
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        else
            return false;
    }

    private  boolean isDownloadsDocument(Uri uri) {
        if(uri.getAuthority() != null)
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        else
            return false;
    }

    private  boolean isMediaDocument(Uri uri) {
        if(uri.getAuthority() != null)
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        else
            return false;
    }

    private  boolean isGooglePhotosUri(Uri uri) {
        if(uri.getAuthority() != null)
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        else
            return false;
    }

    private boolean isWhatsAppFile(Uri uri){
        if(uri.getAuthority() != null)
            return "com.whatsapp.provider.media".equals(uri.getAuthority());
        return false;
    }

    private boolean isGoogleDriveUri(Uri uri) {
        if (uri.getAuthority() != null)
            return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
        else
            return false;
    }


}
