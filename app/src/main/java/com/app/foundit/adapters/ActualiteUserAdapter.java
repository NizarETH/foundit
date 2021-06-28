package com.app.foundit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.foundit.R;
import com.app.foundit.beans.Actualite;

import java.util.List;

public class ActualiteUserAdapter extends BaseAdapter {

    private Context context;
    private List<Actualite> actualites;

    public ActualiteUserAdapter(Context context, List<Actualite> actualites) {
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

        view = inflater.from(context).inflate(R.layout.actualite_item, viewGroup, false); // inflate the layout

        ((TextView)view.findViewById(R.id.data)).setText(getItem(i).getData());
        ((TextView)view.findViewById(R.id.date)).setText(getItem(i).getDate());
        return view;
    }
}
