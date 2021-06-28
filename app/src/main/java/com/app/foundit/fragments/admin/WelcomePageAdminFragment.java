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
import com.app.foundit.fragments.user.HomeUserFragment;
import com.app.foundit.fragments.user.LoginUserFragment;


public class WelcomePageAdminFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull   LayoutInflater inflater, @Nullable   ViewGroup container, @Nullable   Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.welcome_page_admin, container, false);

        v.findViewById(R.id.connex_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("role","admin");
                LoginAdminFragment loginAdminFragment = new LoginAdminFragment();
                loginAdminFragment.setArguments(b);

                ((MyApplication)getActivity().getApplication()).setUpFragment(getActivity(), loginAdminFragment);
            }
        });
        v.findViewById(R.id.open_foundit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("role","guest");
                HomeUserFragment homeUserFragment = new HomeUserFragment();
                homeUserFragment.setArguments(b);

                ((MyApplication)getActivity().getApplication()).setUpFragment(getActivity(), homeUserFragment);

            }
        });
        return v;
    }
}
