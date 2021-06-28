package com.app.foundit.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.app.foundit.R;
import com.app.foundit.beans.MyObject;
import com.app.foundit.utils.ErrorMessage;
import com.app.foundit.utils.GlideApp;

import java.util.List;

import io.realm.Realm;

public class ObjectDetailsAdminAdapter extends BaseAdapter {

    private List<MyObject> myObjects;
    private Context context;

    public ObjectDetailsAdminAdapter(List<MyObject> myObjects, Context context) {
        this.myObjects = myObjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return myObjects.size();
    }

    @Override
    public MyObject getItem(int i) {
        return myObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.from(context).inflate(R.layout.list_item, viewGroup, false); // inflate the layout

        GlideApp.with(context).load(getItem(i).getPicture()).into((ImageView) view.findViewById(R.id.img));
        ((TextView) view.findViewById(R.id.title)).setText(getItem(i).getName());
        ((TextView) view.findViewById(R.id.details)).setText(getItem(i).getDetails());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Suppression d'objet perdu")
                        .setMessage("Voulez-vous supprimer cet objet ?")
                        .setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        Realm r = Realm.getDefaultInstance();
                        r.beginTransaction();
                        MyObject myObject = r.where(MyObject.class).equalTo("id", getItem(i).getId()).findFirst();

                        if(myObject != null) {
                            myObject.deleteFromRealm();
                            notifyDataSetChanged();
                        }
                        r.commitTransaction();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        return view;
    }
}
