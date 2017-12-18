package com.example.admin.book_barrter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ADMIN on 20/11/2017.
 */

public class Profile extends Fragment {

EditText name;
    EditText colg_name;
    EditText contactnum;


    Button update ;
    Button change_photo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile,container,false);

        name = (EditText) view.findViewById(R.id.display_name);
        colg_name  = (EditText) view.findViewById(R.id.colg_name);
        contactnum = (EditText) view.findViewById(R.id.contactno);

        change_photo = (Button) view.findViewById(R.id.change_pic);
        update = (Button) view.findViewById(R.id.profileinfo);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_photo.setVisibility(v.VISIBLE);

                name.setEnabled(true);
                name.setVisibility(v.VISIBLE);
                colg_name.setEnabled(true);
                contactnum.setEnabled(true);


            }
        });


        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Hy you can chnage pic hee",Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }
}
