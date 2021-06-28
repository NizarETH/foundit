package com.app.foundit.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;
import com.app.foundit.beans.User;
import com.app.foundit.utils.GlideApp;

import org.jetbrains.annotations.NotNull;

import io.realm.Realm;

public class ProfilAdminFragment extends Fragment {
    private View v;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_profile_admin, container, false);

        Realm r = Realm.getDefaultInstance();
        User user = r.where(User.class).findFirst();
        ((TextView)v.findViewById(R.id.username)).setText(user.getFirst_name() + " "+ user.getLast_name());
        ((EditText)v.findViewById(R.id.modifier_ad)).setText(user.getEmail());
        ((EditText)v.findViewById(R.id.modifier_nu)).setText(user.getPhone());
        GlideApp.with(getActivity()).load(user.getPicture()).error(R.drawable.photo).into((ImageView) v.findViewById(R.id.avatar));

        v.findViewById(R.id.update_profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.beginTransaction();

                user.setPhone(((EditText)v.findViewById(R.id.modifier_nu)).getText().toString());
                user.setPassword(((EditText)v.findViewById(R.id.modifier_le)).getText().toString());
                user.setEmail(((EditText)v.findViewById(R.id.modifier_ad)).getText().toString());

                r.commitTransaction();

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }
}
