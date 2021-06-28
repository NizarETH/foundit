package com.app.foundit.fragments.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;

import org.jetbrains.annotations.NotNull;

public class SMSConfirmUserFragment extends Fragment {


    @Nullable
    
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_recup_act2, container, false);

        v.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPasswordUserFragment newPasswordUserFragment = new NewPasswordUserFragment();
                newPasswordUserFragment.setArguments(getArguments());
                ((MyApplication) getActivity().getApplication()).setRightFragment(getActivity(), newPasswordUserFragment);
            }
        });
        return v;
    }
}