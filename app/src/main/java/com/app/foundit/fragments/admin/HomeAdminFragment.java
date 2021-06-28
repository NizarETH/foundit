package com.app.foundit.fragments.admin;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.activities.MainActivity;
import com.app.foundit.adapters.ObjectAdminAdapter;
import com.app.foundit.adapters.ObjectUserAdapter;
import com.app.foundit.beans.MyObject;
import com.app.foundit.fragments.user.ActualiteUserFragment;
import com.app.foundit.fragments.user.ContactUserFragment;
import com.app.foundit.fragments.user.HomeUserFragment;
import com.app.foundit.fragments.user.ProfilUserFragment;
import com.app.foundit.interfaces.SearchCategory;
import com.app.foundit.interfaces.SearchObject;
import com.app.foundit.utils.Utils;
import com.app.foundit.utils.slidingMenu.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class HomeAdminFragment extends Fragment {

    private SearchObject searchObject;
    private SearchCategory searchCategory;
    private List<MyObject> myObjects;
    private GridView grid_view;
    private boolean isBook, isKeys, isFile, isLaptop;
    private    String role;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchObject)
            searchObject = (SearchObject) context;

        if (context instanceof SearchCategory)
            searchCategory = (SearchCategory) context;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_home_admin, container, false);

          grid_view = v.findViewById(R.id.grid_view);

        myObjects = Realm.getDefaultInstance().where(MyObject.class).findAll();
        EditText search_value =   getActivity().findViewById(R.id.search_value_adm);

        ObjectAdminAdapter objectAdapter = new ObjectAdminAdapter(getActivity(), myObjects);
        grid_view.setAdapter(objectAdapter);

        getActivity().findViewById(R.id.top_bar_admin).setVisibility(View.VISIBLE);
      //  ((MainActivity)getActivity()).menu.setMenu(R.layout.selection_favorites_admin);

        getActivity().findViewById(R.id.top_bar).setVisibility(View.GONE);

        getActivity().findViewById(R.id.actualit_s_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication())
                        .setRightFragment(getActivity() , new ActualiteAdminFragment());
            }
        });

        if(search_value != null)
            searchObjectName(search_value);

        getActivity().findViewById(R.id.search_icon_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().findViewById(R.id.menu_bar_adm).setVisibility(View.GONE);
                getActivity().findViewById(R.id.search_tab_adm).setVisibility(View.VISIBLE);
            }
        });
        getActivity().findViewById(R.id.user_icon_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ProfilAdminFragment());

            }
        });

        getActivity().findViewById(R.id.actualit_s_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ActualiteAdminFragment());
                ((TextView)getActivity().findViewById(R.id.actualit_s_adm)).setTextColor(getResources().getColor(R.color.blue_));
                ((TextView)getActivity().findViewById(R.id.accueil_adm)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.users_adm)).setTextColor(getResources().getColor(R.color.black));
            }
        });
        getActivity().findViewById(R.id.accueil_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeAdminFragment homeAdminFragment =  (HomeAdminFragment) getActivity().getSupportFragmentManager()
                        .findFragmentByTag("HomeAdminFragment");

                if(homeAdminFragment == null)
                    homeAdminFragment = new HomeAdminFragment();

                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), homeAdminFragment);

                ((TextView)getActivity().findViewById(R.id.actualit_s_adm)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.accueil_adm)).setTextColor(getResources().getColor(R.color.blue_));
                ((TextView)getActivity().findViewById(R.id.users_adm)).setTextColor(getResources().getColor(R.color.black));
            }
        });
        getActivity().findViewById(R.id.users_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new UsersAdminFragment());
                ((TextView)getActivity().findViewById(R.id.actualit_s_adm)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.accueil_adm)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.users_adm)).setTextColor(getResources().getColor(R.color.blue_));
            }
        });
        getActivity().findViewById(R.id.close_icon_adm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideSoftKeyboard(getActivity());
                getActivity().findViewById(R.id.menu_bar_adm).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.search_tab_adm).setVisibility(View.GONE);

            }
        });

        v.findViewById(R.id.books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBook = true;
                isFile = false;
                isKeys = false;
                isLaptop = false;
                handleData(v);
            }
        });
        v.findViewById(R.id.keys).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBook = false;
                isFile = false;
                isKeys = true;
                isLaptop = false;
                handleData(v);
            }
        });
        v.findViewById(R.id.file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBook = false;
                isFile = true;
                isKeys = false;
                isLaptop = false;
                handleData(v);
            }
        });
        v.findViewById(R.id.laptop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBook = false;
                isFile = false;
                isKeys = false;
                isLaptop = true;
                handleData(v);
            }
        });


        v.findViewById(R.id.close_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
                ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
                ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
                ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));

                v.findViewById(R.id.close_category).setVisibility(View.GONE);

                ObjectUserAdapter objectAdapter = new ObjectUserAdapter(getActivity(), myObjects, role);
                grid_view.setAdapter(objectAdapter);
            }
        });
        return v;
    }



    private void displayCloseCategory(View v)
    {
        v.findViewById(R.id.close_category).setVisibility(View.VISIBLE);
    }
    private void handleData(View v)
    {
        if(isBook) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.blue_));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));
            searchCategory.category("books");
            displayCloseCategory(v);
        }

        if(isFile) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.blue_));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));
            searchCategory.category("file");
            displayCloseCategory(v);
        }

        if(isLaptop) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.blue_));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));
            searchCategory.category("laptop");
            displayCloseCategory(v);
        }

        if(isKeys) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.blue_));
            searchCategory.category("keys");
            displayCloseCategory(v);
        }
    }

    private void searchObjectName(EditText searchValue) {

        searchValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = searchValue.getText().toString();
                searchObject.value(value);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });
    }

    public void getSearchedValue(String name) {

        int textLength = name.length();
        List<MyObject> tempArrayList = new ArrayList<>();

        if (myObjects != null && !myObjects.isEmpty()) {
            for (MyObject c : myObjects) {
                if (textLength <= c.getName().length() && c.getName().toLowerCase().contains(name.toLowerCase())) {
                    tempArrayList.add(c);
                }
            }
        }

        ObjectUserAdapter objectAdapter = new ObjectUserAdapter(getActivity(), tempArrayList, role);
        grid_view.setAdapter(objectAdapter);
    }

    public void getSearchedCategory(String category) {

        int textLength = category.length();
        List<MyObject> tempArrayList = new ArrayList<>();

        if (myObjects != null && !myObjects.isEmpty()) {
            for (MyObject c : myObjects) {
                if (textLength <= c.getCategory().length() && c.getCategory().toLowerCase().contains(category.toLowerCase())) {
                    tempArrayList.add(c);
                }
            }
        }

        ObjectUserAdapter objectAdapter = new ObjectUserAdapter(getActivity(), tempArrayList, role);
        grid_view.setAdapter(objectAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().findViewById(R.id.top_bar_admin).setVisibility(View.GONE);

    }

}
