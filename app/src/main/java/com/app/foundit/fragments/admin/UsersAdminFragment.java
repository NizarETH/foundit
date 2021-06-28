package com.app.foundit.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;
import com.app.foundit.adapters.UsersAdapter;
import com.app.foundit.beans.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;

public class UsersAdminFragment extends Fragment {

    private View v;

    
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.users, container, false);

        Realm r = Realm.getDefaultInstance();
        List<User> users =  r.where(User.class).findAll();
        ((TextView)v.findViewById(R.id.number_users)).setText(""+users.size()+" Users");
        GridView grid_view = v.findViewById(R.id.grid_view);
        grid_view.setAdapter(new UsersAdapter(getActivity(), users));

        return v;
    }
}
