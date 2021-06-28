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
import com.app.foundit.fragments.user.ResetPasswordUserFragment;


public class
LoginAdminFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container, @Nullable   Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_admin, container, false);

        Button login =(Button) v.findViewById(R.id.login);
        login.setOnClickListener(v1 -> {
            //RecAct
            ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new HomeAdminFragment());
        });
        v.findViewById(R.id.reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ResetPasswordAdminFragment());
            }
        });
        return v;
    }

}
