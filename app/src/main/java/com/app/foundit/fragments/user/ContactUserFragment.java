package com.app.foundit.fragments.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;

import org.jetbrains.annotations.NotNull;

public class ContactUserFragment extends Fragment {

    private View v;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_contact, container, false);

        v.findViewById(R.id.msg_env).setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Votre message est envoyé avec succès.", Toast.LENGTH_LONG).show();
            Handler h = new Handler();
            h.postDelayed(() -> getActivity().getSupportFragmentManager().popBackStack(), 500);
        });
        return v;
    }
}
