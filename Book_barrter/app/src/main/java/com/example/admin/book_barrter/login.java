package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
/**
 * Created by ADMIN on 14/11/2017.
 */

public class login extends Fragment{
    Button signup1;
    FragmentManager fm;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.login,container,false);

        signup1 =(Button) view.findViewById(R.id.signupu);
        fm = getFragmentManager();
        signup1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signup_window();

            }
        });

              return view;

    }

    public void signup_window(){
        signup signup = new signup();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_screen,signup);
        ft.commit();

      //  Toast.makeText(getActivity(),"hello how are u",Toast.LENGTH_SHORT).show();


    }

}
