package com.app.foundit.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;
import com.app.foundit.adapters.ActualiteAdminAdapter;
import com.app.foundit.beans.Actualite;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;

public class ActualiteAdminFragment extends Fragment {

    private View v;


    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_actualities_admin, container, false);

        List<Actualite> actualites = Realm.getDefaultInstance().where(Actualite.class).findAll();

        ListView list_actualites = v.findViewById(R.id.list_actualites);
        list_actualites.setAdapter(new ActualiteAdminAdapter(getActivity(), actualites));
        return v;
    }
}
