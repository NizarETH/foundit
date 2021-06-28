package com.app.foundit;

import android.app.Application;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.app.foundit.activities.MainActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);
    }

    public void setRightFragment(FragmentActivity fragmentActivity, Fragment fm) {

        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();

        Fragment fragment = fragmentActivity.getSupportFragmentManager()
                .findFragmentByTag(fm.getClass().getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            //
        } else {
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
                            R.anim.slide_out_right)
                    .replace(R.id.fragment_container, fm, ((MainActivity) fragmentActivity).bodyFragment)
                    .addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();
        }

    }

    public void setLeftFragment(FragmentActivity fragmentActivity, Fragment fm) {
        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();

        Fragment fragment = fragmentActivity.getSupportFragmentManager()
                .findFragmentByTag(fm.getClass().getSimpleName());
        if (fragment != null && fragment.isAdded()) {
            //
        } else {
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right,
                            R.anim.slide_out_left)
                    .replace(R.id.fragment_container, fm, ((MainActivity) fragmentActivity).bodyFragment)
                    .addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();
        }

    }


    public void setDownFragment(FragmentActivity fragmentActivity, Fragment fm) {
        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();
        // Replace fragmentCotainer with your container id

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_bottom,
                        R.anim.slide_out_top)
                .replace(R.id.fragment_container, fm, ((MainActivity) fragmentActivity).bodyFragment)
                .addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();
    }

    public void setUpFragment(FragmentActivity fragmentActivity, Fragment fm ) {
        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top,
                        R.anim.slide_out_bottom)
                .replace(R.id.fragment_container, fm, ((MainActivity) fragmentActivity).bodyFragment)
                .addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();
    }

    public void refreshFragment(FragmentActivity fragmentActivity, Fragment fm) {
        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();

        fragmentActivity.getSupportFragmentManager().beginTransaction().detach(fm).attach(fm)
                /* .addToBackStack( ((MainActivity) fragmentActivity).bodyFragment ) */.commit();
    }

    public void setUpFragmentWithoutBackStack(FragmentActivity fragmentActivity, Fragment fm ) {
        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();
        // Replace fragmentCotainer with your container id

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top,
                        R.anim.slide_out_bottom)
                .replace(R.id.fragment_container, fm, ((MainActivity) fragmentActivity).bodyFragment)
                /* .addToBackStack(((MainActivity) fragmentActivity).bodyFragment) */.commitAllowingStateLoss();
    }

    public void setRightFragmentWithoutBackStack(FragmentActivity fragmentActivity, Fragment fm ) {
        ((MainActivity) fragmentActivity).bodyFragment = fm.getClass().getSimpleName();
        // Replace fragmentCotainer with your container id

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(R.id.fragment_container, fm, ((MainActivity) fragmentActivity).bodyFragment)
                /* .addToBackStack(((MainActivity) fragmentActivity).bodyFragment) */.commitAllowingStateLoss();
    }




}
