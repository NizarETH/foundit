package com.app.foundit.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.beans.User;
import com.app.foundit.fragments.user.ContactUserFragment;
import com.app.foundit.utils.GlideApp;

import org.jetbrains.annotations.NotNull;

import io.realm.Realm;

public class UpdateUserAdminFragment extends Fragment {
    private View v;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_update_admin, container, false);

        Realm r = Realm.getDefaultInstance();
        String id = getArguments().getString("id");

        User user = r.where(User.class).equalTo("id",id).findFirst();
        String name = "";
        if (user.getFirst_name() != null)
            name = user.getFirst_name();
        if (user.getLast_name() != null)
            name .concat(" "+user.getLast_name())  ;

        ((TextView)v.findViewById(R.id.username)).setText(name);

        GlideApp.with(getActivity()).load(user.getPicture()).error(R.drawable.photo).into((ImageView) v.findViewById(R.id.avatar));

        v.findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ContactUserFragment());
            }
        });
        v.findViewById(R.id.update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ProfilAdminFragment());
            }
        });

        v.findViewById(R.id.block_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "L'utilisateur est banni !", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().popBackStack();
                }
        });

        return v;
    }
}
