package com.app.foundit.fragments.user;

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


public class WelcomePageUserFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull   LayoutInflater inflater, @Nullable   ViewGroup container, @Nullable   Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.welcome_page_user, container, false);

        Bundle b = new Bundle();
        v.findViewById(R.id.connex_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.putString("role","guest");
                HomeUserFragment homeUserFragment = new HomeUserFragment();
                homeUserFragment.setArguments(b);

                ((MyApplication)getActivity().getApplication()).setUpFragment(getActivity(), homeUserFragment);
            }
        });
        v.findViewById(R.id.connex_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.putString("role","user");
                ((MyApplication)getActivity().getApplication()).setDownFragment(getActivity(), new LoginUserFragment());

            }
        });
        v.findViewById(R.id.inscrire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setDownFragment(getActivity(), new SignUpUserFragment());

            }
        });
        return v;
    }
}
