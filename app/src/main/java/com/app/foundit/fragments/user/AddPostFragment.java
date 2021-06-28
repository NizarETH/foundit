package com.app.foundit.fragments.user;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;
import com.app.foundit.activities.MainActivity;
import com.app.foundit.beans.MyObject;
import com.app.foundit.beans.User;
import com.app.foundit.utils.ErrorMessage;
import com.app.foundit.utils.FileUtils;
import com.app.foundit.utils.GlideApp;
import com.app.foundit.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import timber.log.Timber;

public class AddPostFragment extends Fragment {

    private File photoFile;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int SELECT_GALLERY = 1, SELECT_PICTURE = 2;
    private View v;
    private Realm r;
    private MyObject myObject;
    private String path;
    private String category = "";

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        r = Realm.getDefaultInstance();

        myObject = new MyObject();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull   LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.post, container, false);


        Button object_date = v.findViewById(R.id.object_date);
        EditText object_name = v.findViewById(R.id.object_name);
        EditText founder_phone = v.findViewById(R.id.founder_phone);
        EditText object_location = v.findViewById(R.id.object_location);



        Calendar c = Calendar.getInstance();

        Date today = new Date();
        c.setTime(today);

        long minDate = c.getTime().getTime();

        object_date.setOnClickListener(v1 -> {

            Utils.hideSoftKeyboard(getActivity());
            object_location.setFocusable(false);

            Calendar c1 = Calendar.getInstance();
            // Get Current Time
            mHour = c1.get(Calendar.HOUR_OF_DAY);
            mMinute = c1.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            object_date.append(" à " + hourOfDay + ":" + minute);


                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();

            mYear = c1.get(Calendar.YEAR);
            mMonth = c1.get(Calendar.MONTH);
            mDay = c1.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            object_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            myObject.setDate(object_date.getText().toString());
                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.getDatePicker().setMinDate(minDate);

            datePickerDialog.show();

        });

        v.findViewById(R.id.import_picture).setOnClickListener(view -> showPictureDialog());

        v.findViewById(R.id.book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "book";
                Toast.makeText(getActivity()," Catégorie est enregesitrée",Toast.LENGTH_LONG).show();
            }
        }); v.findViewById(R.id.laptop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "laptop";
                Toast.makeText(getActivity()," Catégorie est enregesitrée",Toast.LENGTH_LONG).show();

            }
        });v.findViewById(R.id.file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "file";
                Toast.makeText(getActivity()," Catégorie est enregesitrée",Toast.LENGTH_LONG).show();

            }
        });v.findViewById(R.id.keys).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "keys";
                Toast.makeText(getActivity()," Catégorie est enregesitrée",Toast.LENGTH_LONG).show();

            }
        });


        v.findViewById(R.id.poster_object).setOnClickListener(view -> {
            myObject.setLocation((object_location).getText().toString());
            myObject.setDate(object_date.getText().toString());
            myObject.setName(object_name.getText().toString());
            myObject.setCategory(category);

            if(!TextUtils.isEmpty(path)) {

                myObject.setPicture(path);
             }

            if(photoFile != null) {

                myObject.setPicture(photoFile.getPath());
            }

            myObject.setId(UUID.randomUUID().toString());
            r.beginTransaction();
            User user = r.where(User.class).findFirst();
            user.setPhone(founder_phone.getText().toString());
            myObject.setFounder(user);
            r.copyToRealmOrUpdate(myObject);
            r.commitTransaction();

            getActivity().getSupportFragmentManager().popBackStack();
        });
        return v;
    }

    private void chooseFilesFromGallaryPermission() {

        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        chooseFilesFromGallery();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied())
                            ((MainActivity) getActivity()).showSettingsDialog();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void chooseFilesFromGallery() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, SELECT_GALLERY);

    }

    private void takePhotoFromCameraPermission() {
        Dexter.withContext(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                      takePhotoFromCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }
    private void takePhotoFromCamera() {

        String currentTime = String.valueOf(System.currentTimeMillis());
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        String root = Utils.getFirstWritableDirectory().toString();
        File myDir = new File(root + "/.APP_IMAGES");

        if (!myDir.exists())
            myDir.mkdirs();

        Timber.e( "myDir : " + myDir.exists());

        photoFile = new File(myDir + File.separator + "Image_" + currentTime + ".jpg");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


        } else
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));


        startActivityForResult(intent, SELECT_PICTURE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPictureDialog() {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate and set the layout for the dialog
        View view = inflater.inflate(R.layout.choose_files, null);


        PopupWindow pw = new PopupWindow(view, 470,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        // display the popup in the center
        pw.setOutsideTouchable(true);
       // pw.setBackgroundDrawable(getActivity().getDrawable(R.drawable.shadow_17657));

        pw.setFocusable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) pw.setElevation(25);

        pw.setTouchInterceptor((v1, event) -> {
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                pw.dismiss();
                return true;
            }
            return false;
        });
        view.findViewById(R.id.open_gallery).setOnClickListener(v1 -> {
            chooseFilesFromGallaryPermission();
            pw.dismiss();
        });


        view.findViewById(R.id.open_picture).setOnClickListener(v1 -> {

            takePhotoFromCameraPermission();
            pw.dismiss();

        });
         pw.showAtLocation(v, Gravity.CENTER, 0, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Timber.e("data " + data);

        if (requestCode == SELECT_GALLERY)
            displayFileFromGallery(data);

        else if (requestCode == SELECT_PICTURE)
            displayPicFromCamera();

    }

    private void displayPicFromCamera() {

        v.findViewById(R.id.img_taken).setVisibility(View.VISIBLE);
        GlideApp.with(getActivity()).load(photoFile).into((ImageView) v.findViewById(R.id.img_taken));

    }

    private void displayFileFromGallery(Intent data) {

        if (data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            FileUtils fileUtils = new FileUtils(getActivity());
            path = fileUtils.getPath(fileUri);


            v.findViewById(R.id.img_taken).setVisibility(View.VISIBLE);
            GlideApp.with(getActivity()).load(path).into((ImageView) v.findViewById(R.id.img_taken));

        }
    }
}
