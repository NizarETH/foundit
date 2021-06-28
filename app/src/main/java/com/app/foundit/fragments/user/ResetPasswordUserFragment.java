package com.app.foundit.fragments.user;

 import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
 import android.widget.EditText;

 import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.foundit.MyApplication;
import com.app.foundit.R;
 import com.app.foundit.beans.User;

 import org.jetbrains.annotations.NotNull;

 import java.util.List;

 import io.realm.Realm;

public class ResetPasswordUserFragment extends Fragment {


    private View v;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_rec, container, false);

        Button  btn =(Button) v.findViewById(R.id.button2);
        EditText txtV =(EditText) v.findViewById(R.id.txtV);
        Bundle b = new Bundle();

        String data = txtV.getText().toString();
        Realm r = Realm.getDefaultInstance();
        List<User> users = r.where(User.class).findAll();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i) != null)
            {
                if(users.get(i).getEmail().equalsIgnoreCase(data) || users.get(i).getPhone().equalsIgnoreCase(data) )
                {
                    b.putString("id", users.get(i).getId());
                }
            }
            }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSConfirmUserFragment smsConfirmUserFragment =  new SMSConfirmUserFragment();
                smsConfirmUserFragment.setArguments(b);
                ((MyApplication)getActivity().getApplication()).setRightFragment(getActivity(), smsConfirmUserFragment);
            }
        });
        return v;
    }

}