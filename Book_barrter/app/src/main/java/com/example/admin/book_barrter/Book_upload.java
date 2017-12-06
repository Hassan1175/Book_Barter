package com.example.admin.book_barrter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by ADMIN on 18/11/2017.
 */

public class Book_upload extends Fragment {
     Spinner spinner;
    ArrayAdapter<CharSequence> adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.book_upload,container,false);

//  cos here i am using fragments for my application, so i will inherit findviewby id methid from view claass
        spinner = (Spinner) view.findViewById(R.id.book_category);

        //here i am getting data fromm the  string resourse, where i have made an array named as Book_Category,(arts, science, general)
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Book_Category,android.R.layout.simple_spinner_item);
        // the first one is  for the each item of the drop down, while second is the for dropdown view resourse
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //here action listener for the selected item of the spinner

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),parent.getItemAtPosition(position) + "Selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }



}
