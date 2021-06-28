package com.app.foundit.fragments.user;

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
import com.app.foundit.adapters.ObjectUserAdapter;
import com.app.foundit.beans.MyObject;
import com.app.foundit.interfaces.SearchCategory;
import com.app.foundit.interfaces.SearchObject;
import com.app.foundit.interfaces.SendIDtoMain;
import com.app.foundit.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;


public class HomeUserFragment extends Fragment {

    private SearchObject searchObject;
    private SearchCategory searchCategory;
    private List<MyObject> myObjects;
    private GridView grid_view;
    private boolean isBook, isKeys, isFile, isLaptop;
    private    String role;
    private SendIDtoMain sendIDtoMain;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchObject)
            searchObject = (SearchObject) context;

        if (context instanceof SearchCategory)
            searchCategory = (SearchCategory) context;

        if (context instanceof SendIDtoMain)
            sendIDtoMain = (SendIDtoMain) context;

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_home, container, false);

        Bundle b = getArguments();
          role = b.getString("role");
          String id = b.getString("id");

          sendIDtoMain.data(id);
        //((MainActivity)getActivity()).menu.setMenu(R.layout.selection_favorites_view );

        if(role.equalsIgnoreCase("user"))
        {
            getActivity().findViewById(R.id.eva_plus_sq).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.user_icon).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.gg_menu_rig).setVisibility(View.VISIBLE);
        }
        else if (role.equalsIgnoreCase("guest"))
        {
            getActivity().findViewById(R.id.eva_plus_sq).setVisibility(View.INVISIBLE);
            getActivity().findViewById(R.id.user_icon).setVisibility(View.INVISIBLE);
            getActivity().findViewById(R.id.gg_menu_rig).setVisibility(View.INVISIBLE);
        }

        getActivity().findViewById(R.id.top_bar_admin).setVisibility(View. GONE );
        getActivity().findViewById(R.id.top_bar).setVisibility(View.VISIBLE);

        grid_view = v.findViewById(R.id.grid_view);
        EditText search_value =   getActivity().findViewById(R.id.search_value);

         myObjects = Realm.getDefaultInstance().where(MyObject.class).findAll();

        ObjectUserAdapter objectAdapter = new ObjectUserAdapter(getActivity(), myObjects, role);
        grid_view.setAdapter(objectAdapter);

        if(search_value != null)
            searchObjectName(search_value);

        getActivity().findViewById(R.id.search_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().findViewById(R.id.menu_bar).setVisibility(View.GONE);
                getActivity().findViewById(R.id.search_tab).setVisibility(View.VISIBLE);
            }
        });
        getActivity().findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfilUserFragment profilUserFragment = new ProfilUserFragment();
                profilUserFragment.setArguments(getArguments());
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(),profilUserFragment);

            }
        });
        getActivity().findViewById(R.id.actualit_s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ActualiteUserFragment());
                ((TextView)getActivity().findViewById(R.id.actualit_s)).setTextColor(getResources().getColor(R.color.green_));
                ((TextView)getActivity().findViewById(R.id.accueil)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.contact)).setTextColor(getResources().getColor(R.color.black));
            }
        });
         getActivity().findViewById(R.id.accueil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeUserFragment homeUserFragment =  (HomeUserFragment) getActivity().getSupportFragmentManager()
                        .findFragmentByTag("HomeUserFragment");
                if(homeUserFragment == null)
                    homeUserFragment = new HomeUserFragment();
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), homeUserFragment);

                ((TextView)getActivity().findViewById(R.id.actualit_s)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.accueil)).setTextColor(getResources().getColor(R.color.green_));
                ((TextView)getActivity().findViewById(R.id.contact)).setTextColor(getResources().getColor(R.color.black));
            }
        });
         getActivity().findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ContactUserFragment());
                ((TextView)getActivity().findViewById(R.id.actualit_s)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.accueil)).setTextColor(getResources().getColor(R.color.black));
                ((TextView)getActivity().findViewById(R.id.contact)).setTextColor(getResources().getColor(R.color.green_));
            }
        });
        getActivity().findViewById(R.id.close_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideSoftKeyboard(getActivity());
                getActivity().findViewById(R.id.menu_bar).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.search_tab).setVisibility(View.GONE);

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
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.green_));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));
            searchCategory.category("books");
            displayCloseCategory(v);
        }

        if(isFile) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.green_));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));
            searchCategory.category("file");
            displayCloseCategory(v);
        }

        if(isLaptop) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.green_));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.gray));
            searchCategory.category("laptop");
            displayCloseCategory(v);
        }

        if(isKeys) {
            ((TextView) v.findViewById(R.id.outils_scol)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.lectronique)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.documents)).setTextColor(getResources().getColor(R.color.gray));
            ((TextView) v.findViewById(R.id.fins_person)).setTextColor(getResources().getColor(R.color.green_));
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

        getActivity().findViewById(R.id.top_bar).setVisibility(View.GONE);

    }
}
