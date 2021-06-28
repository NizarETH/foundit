package com.app.foundit.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.app.foundit.R;
import com.app.foundit.activities.MainActivity;
import com.app.foundit.beans.Actualite;

import java.util.List;

import io.realm.Realm;

public class ActualiteAdminAdapter extends BaseAdapter {

    private Context context;
    private List<Actualite> actualites;

    public ActualiteAdminAdapter(Context context, List<Actualite> actualites) {
        this.context = context;
        this.actualites = actualites;
    }

    @Override
    public int getCount() {
        return actualites.size();
    }

    @Override
    public Actualite getItem(int i) {
        return actualites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.from(context).inflate(R.layout.actualite_item_admin, null); // inflate the layout

        ((TextView)view.findViewById(R.id.data)).setText(getItem(i).getData());
        ((TextView)view.findViewById(R.id.date)).setText(getItem(i).getDate());
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context).setTitle("Suppression de l'actualité")
                        .setMessage("Voulez-vous supprimer l'actualité ?")
                        .setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        Realm r = Realm.getDefaultInstance();
                        r.beginTransaction();
                        Actualite actualite = r.where(Actualite.class).equalTo("id",getItem(i).getId()).findFirst();
                        if(actualite != null)
                            actualite.deleteFromRealm();

                        notifyDataSetChanged();
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
