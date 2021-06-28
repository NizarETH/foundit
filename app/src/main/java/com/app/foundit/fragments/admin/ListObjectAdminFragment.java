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
import com.app.foundit.adapters.ObjectDetailsAdminAdapter;
import com.app.foundit.beans.MyObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;

public class ListObjectAdminFragment extends Fragment {

    private View v;

    @Nullable 
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_object, container, false);

        List<MyObject> myObjects = Realm.getDefaultInstance().where(MyObject.class).findAll();
        ListView listView = v.findViewById(R.id.list_objects);
        listView.setAdapter(new ObjectDetailsAdminAdapter(myObjects, getActivity()));

        return v;
    }
}
