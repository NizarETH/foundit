package com.app.foundit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.fragments.admin.LoginAdminFragment;
import com.app.foundit.fragments.admin.WelcomePageAdminFragment;
import com.app.foundit.fragments.user.LoginUserFragment;
import com.app.foundit.fragments.user.WelcomePageUserFragment;


public class StartFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull   LayoutInflater inflater, @Nullable   ViewGroup container, @Nullable   Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.start, container, false);

        v.findViewById(R.id.connex_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setUpFragment(getActivity(), new WelcomePageAdminFragment());
            }
        });
        v.findViewById(R.id.connex_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setDownFragment(getActivity(), new WelcomePageUserFragment());

            }
        });
        return v;
    }
}
