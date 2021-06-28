package com.app.foundit.fragments.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
import com.app.foundit.beans.User;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import io.realm.Realm;

public class SignUpUserFragment extends Fragment {

    private View v;
   
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_sin_up, container, false);
        Button btnIns= v.findViewById(R.id.s_inscrire);
        EditText t_nom,t_email,t_num,t_psw;
        t_nom= v.findViewById(R.id.nom_et_pr_n);
        t_email= v.findViewById(R.id.adresse_e_m);
        t_num= v.findViewById(R.id.num_ro_de_t);
        t_psw= v.findViewById(R.id.mot_de_pass);

        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm r = Realm.getDefaultInstance();

                r.beginTransaction();
                User user = r.createObject(User.class, UUID.randomUUID().toString());
                String name = t_nom.getText().toString();
                if(name.contains(" "))
                {
                    String[] names = name.split(" ");
                    user.setFirst_name(names[0]);
                    user.setLast_name(names[1]);
                }
                else
                    user.setFirst_name(t_nom.getText().toString());

                user.setEmail(t_email.getText().toString());
                user.setPhone(t_num.getText().toString());
                user.setPassword(t_psw.getText().toString());

                    Toast.makeText(getActivity(), "Compte crée avec succès,",Toast.LENGTH_LONG).show();
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Bundle b = new Bundle();
                            b.putString("role","user");
                            b.putString("id",user.getId());
                            HomeUserFragment homeUserFragment =  new HomeUserFragment();
                            homeUserFragment.setArguments(b);
                            ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), homeUserFragment);
                        }
                    }, 500);
                    r.commitTransaction();



            }
        });

        return v;
    }
}
