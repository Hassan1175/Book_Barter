package com.example.admin.book_barrter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ADMIN on 12/11/2017.
 */

public class signup extends Fragment {

    Button signp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.signup,container,false);

        signp = (Button) view.findViewById(R.id.registration);

        signp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"sdasdasda",Toast.LENGTH_LONG).show();

            Intent i = new Intent(getActivity(),Home_screen.class);
                startActivity(i);
            }
        });


        return view;

    }


}
