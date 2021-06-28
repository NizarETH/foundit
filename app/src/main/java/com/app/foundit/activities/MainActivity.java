package com.app.foundit.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.beans.MyObject;
import com.app.foundit.fragments.StartFragment;
import com.app.foundit.fragments.admin.HomeAdminFragment;
import com.app.foundit.fragments.admin.ProfilAdminFragment;
import com.app.foundit.fragments.user.AddPostFragment;
import com.app.foundit.fragments.user.ContactUserFragment;
import com.app.foundit.fragments.user.HomeUserFragment;
import com.app.foundit.fragments.user.ProfilUserFragment;
import com.app.foundit.interfaces.SearchCategory;
import com.app.foundit.interfaces.SearchObject;
import com.app.foundit.interfaces.SendIDtoMain;
import com.app.foundit.utils.slidingMenu.SlidingMenu;


public class MainActivity extends AppCompatActivity implements SearchObject, SearchCategory, SendIDtoMain {


    public String bodyFragment;
    public SlidingMenu menu;
    private View shadow;
    private Boolean isUser;
    private Boolean isAdmin;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        menu = new SlidingMenu(this);

        menu.setMode(SlidingMenu.LEFT);

        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);
        menu.setSlidingEnabled(false);
        menu.setFadeDegree(0.25f);
        menu.setMenu(R.layout.selection_favorites_view);

      /*  if(findViewById(R.id.top_bar_admin).getVisibility() == View.VISIBLE)
        {
            menu.setMenu(R.layout.selection_favorites_admin);
            isUser = false;
            isAdmin = true;

        }
            else
        {
            menu.setMenu(R.layout.selection_favorites_view);
            isUser = true;
            isAdmin = false;
        }*/

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.widthPixels > metrics.heightPixels)
            menu.setBehindOffset(metrics.widthPixels - (metrics.widthPixels / 2));
        else
            menu.setBehindOffset(metrics.widthPixels - (metrics.heightPixels / 2));


        menu.setBehindWidthRes(R.dimen.menu_width_res_phone);

        ImageView Homebutton = (ImageView)  findViewById(R.id.gg_menu_rig);

        shadow =  findViewById(R.id.shadowview);

        Homebutton.setOnClickListener(view -> OpenMenu());

        shadow.setOnClickListener(view -> {
            if(menu.isMenuShowing())
                CloseMenu();
        });
        findViewById(R.id.eva_plus_sq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getApplication()).setRightFragment(MainActivity.this, new AddPostFragment());
            }
        });
        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getApplication()).setRightFragment(MainActivity.this, new ContactUserFragment());
            }
        });
        findViewById(R.id.open_profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                Bundle b = new Bundle();
                b.putString("id",idUser);
                ProfilUserFragment profilUserFragment = new ProfilUserFragment();
                profilUserFragment.setArguments(b);
                ((MyApplication)getApplication()).setRightFragment(MainActivity.this, profilUserFragment);
            }
        });
        findViewById(R.id.disconnect ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setTitle("La déconnexion")
                        .setMessage("Voulez-vous se déconnecter?")
                        .setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        finish();
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

        ((MyApplication) getApplication()).setRightFragment(this, new StartFragment());

    }



    private void CloseMenu() {

        shadow.setVisibility(View.GONE);
        menu.toggle(true);
    }

    private void OpenMenu() {

        menu.showMenu(true);
        shadow.setVisibility(View.VISIBLE);
    }

    public void showSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",  getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void value(String name) {
        HomeUserFragment homeUserFragment =  (HomeUserFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeUserFragment");
        if(homeUserFragment != null)
             homeUserFragment.getSearchedValue(name);

        HomeAdminFragment homeAdminFragment =  (HomeAdminFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeAdminFragment");
        if(homeAdminFragment != null)
            homeAdminFragment.getSearchedValue(name);
    }

    @Override
    public void category(String name) {
        HomeUserFragment homeUserFragment =  (HomeUserFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeUserFragment");
        if(homeUserFragment != null)
            homeUserFragment.getSearchedCategory(name);
        HomeAdminFragment homeAdminFragment =  (HomeAdminFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeAdminFragment");
        if(homeAdminFragment != null)
            homeAdminFragment.getSearchedCategory(name);
    }

    @Override
    public void data(String id) {
        idUser = id;
    }
}