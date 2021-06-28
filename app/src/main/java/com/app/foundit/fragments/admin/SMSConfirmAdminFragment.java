package com.app.foundit.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;

import org.jetbrains.annotations.NotNull;

public class SMSConfirmAdminFragment extends Fragment {


    @Nullable
    
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_recup_act2_adm, container, false);

        v.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication) getActivity().getApplication()).setRightFragment(getActivity(), new NewPasswordAdminFragment());
            }
        });
        return v;
    }
}