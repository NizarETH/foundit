package com.app.foundit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main1, container, false);

        Button  btn  =(Button)v.findViewById(R.id.button4);
        Button  btn2 =(Button)v.findViewById(R.id.button3);
        Button  btn3 =(Button)v.findViewById(R.id.button5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginUserFragment
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SinUpUserFragment
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // HomeActivity
            }
        });
        return v;
    }
}
