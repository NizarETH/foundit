package com.app.foundit.fragments.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;

import org.jetbrains.annotations.NotNull;

public class NewPasswordUserFragment extends Fragment {

 
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.password_reset, container, false);

        v.findViewById(R.id.terminer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Nouveau mot de passe est enregistré avec succès,",Toast.LENGTH_LONG).show();
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putString("role","user");

                        if( getArguments() != null)
                            b.putString("id", getArguments().getString("id"));

                        HomeUserFragment homeUserFragment =  new HomeUserFragment();
                        homeUserFragment.setArguments(b);
                        ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), homeUserFragment);
                    }
                }, 500);
            }
        });
        return v;
    }

}