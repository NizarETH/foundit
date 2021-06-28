package com.app.foundit.fragments.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.beans.User;
import com.app.foundit.utils.ErrorMessage;
import com.app.foundit.utils.Utils;

import java.util.List;

import io.realm.Realm;

public class LoginUserFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable   ViewGroup container, @Nullable   Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_user, container, false);

        TextView resetPassword =(TextView) v.findViewById(R.id.reset_password);
        resetPassword.setOnClickListener(v1 -> {
         });

        v.findViewById(R.id.login).setOnClickListener(v1 -> {
            String email = ((EditText)v.findViewById(R.id.username)).getText().toString();
            String password = ((EditText)v.findViewById(R.id.password)).getText().toString();
            Realm r = Realm.getDefaultInstance();
            List<User> users = r.where(User.class).findAll();
            for (int i = 0; i < users.size(); i++) {
                if(users.get(i) != null)
                {
                    if(users.get(i).getEmail().equalsIgnoreCase(email) && users.get(i).getPassword().equalsIgnoreCase(password))
                    {
                        Bundle b = new Bundle();
                        b.putString("role","user");
                        b.putString("id",users.get(i).getId());
                        HomeUserFragment homeUserFragment =  new HomeUserFragment();
                        homeUserFragment.setArguments(b);
                        Utils.hideSoftKeyboard(getActivity());
                        ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), homeUserFragment);
                        break;
                    }
                    else
                        ErrorMessage.display(getActivity()," Mot de passe incorrect.");
                }
            }

        });

        v.findViewById(R.id.reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), new ResetPasswordUserFragment());
            }
        });


        return v;
    }

}
