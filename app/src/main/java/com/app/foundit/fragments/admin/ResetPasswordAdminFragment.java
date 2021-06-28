package com.app.foundit.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;

import org.jetbrains.annotations.NotNull;

public class ResetPasswordAdminFragment extends Fragment {


    private View v;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_rec_admin, container, false);

        Button  btn =(Button) v.findViewById(R.id.button2);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new SMSConfirmAdminFragment());
            }
        });
        return v;
    }

}