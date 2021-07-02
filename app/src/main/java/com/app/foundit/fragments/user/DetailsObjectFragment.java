package com.app.foundit.fragments.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.R;
import com.app.foundit.beans.MyObject;
import com.app.foundit.utils.GlideApp;
import com.app.foundit.utils.PopUpClass;
import com.bumptech.glide.Glide;

import io.realm.Realm;

public class DetailsObjectFragment extends Fragment {


    private View v;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.details_object, container, false);
        Bundle b = getArguments();

        String id_object = b.getString("id_object");
        String role = b.getString("role");
        Realm r = Realm.getDefaultInstance();
        MyObject myObject = r.where(MyObject.class).equalTo("id", id_object).findFirst();


        ((TextView)v.findViewById(R.id.title_object)).setText(myObject.getName());
        ((TextView)v.findViewById(R.id.name)).setText(myObject.getFounder().getFirst_name() +" "+myObject.getFounder().getLast_name()    );
        ((TextView)v.findViewById(R.id.location)).setText(myObject. getLocation()  );
        ((TextView)v.findViewById(R.id.date)).setText(myObject. getDate()  );
        ((TextView)v.findViewById(R.id.details)).setText(myObject. getDetails()  );

        GlideApp.with(getActivity()).load(myObject.getPicture()).into((ImageView) v.findViewById(R.id.image_object));
        if(role.equalsIgnoreCase("guest"))
            {
                v.findViewById(R.id.found_object).setVisibility(View.INVISIBLE);
            }
        else {
            v.findViewById(R.id.found_object).setVisibility(View.VISIBLE);
            v.findViewById(R.id.found_object).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"Demande est envoyée avec succèes", Toast.LENGTH_LONG).show();
                }
            });
        }

        v.findViewById(R.id.open_profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        v.findViewById(R.id.open_profil).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                if(myObject.getFounder() != null)
                   popUpClass.showPopupWindow(v,myObject.getFounder() .getId());
            }
        });
        return v;
    }
}
