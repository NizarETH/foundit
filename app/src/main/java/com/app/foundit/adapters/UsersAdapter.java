package com.app.foundit.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.activities.MainActivity;
import com.app.foundit.beans.User;
import com.app.foundit.fragments.admin.ProfilAdminFragment;
import com.app.foundit.fragments.admin.UpdateUserAdminFragment;
import com.app.foundit.fragments.user.DetailsObjectFragment;
import com.app.foundit.utils.GlideApp;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class UsersAdapter extends BaseAdapter {

    private Context context;

    private List<User> Users;

    public UsersAdapter(Context context, List<User> Users) {
        this.context = context;
        this.Users = Users;

    }

    @Override
    public int getCount() {
        return Users.size();
    }

    @Override
    public User getItem(int i) {
        return Users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.from(context).inflate(R.layout.grid_item, null); // inflate the layout

         ((TextView)view.findViewById(R.id.category)).setText(getItem(i).getFirst_name());
        ImageView  imageItem = (ImageView) view.findViewById(R.id.image_item);


        GlideApp.with(context).load(getItem(i).getPicture()).placeholder(R.drawable.no_img).error(R.drawable.no_img).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.e("TAG", "Error loading image", e);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }

        }).into(imageItem);

        view.findViewById(R.id.open_object).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open details
                UpdateUserAdminFragment detailsObjectFragment = new UpdateUserAdminFragment();
                Bundle b = new Bundle();
                b.putString("id", getItem(i).getId());
                detailsObjectFragment.setArguments(b);
                ((MyApplication)((MainActivity)context).getApplication()).setRightFragment((FragmentActivity) context, detailsObjectFragment);
            }
        });

        return view;
    }
}
