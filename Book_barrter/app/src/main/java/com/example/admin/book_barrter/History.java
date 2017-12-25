package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 20/11/2017.
 */

public class History extends Fragment {

    Toolbar toolbar;

    FirebaseAuth firebaseAuth ;
    // Creating DatabaseReference.
    DatabaseReference databaseReference;
    // Creating RecyclerView.
    RecyclerView recyclerView;
    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating List of Model class, which is named as uploading.. . .
    List<ResponseModel> list = new ArrayList<>();

    ResponseModel responseinfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.history,container,false);


        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        //that is how to use setsupportActionbar for fragment...above one is for activity..

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);






        recyclerView = (RecyclerView)view.findViewById(R.id.R_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog = new ProgressDialog(getActivity());


        // Setting up message in Progress dialog.
        progressDialog.setMessage("Responses of your requests are loading . . .");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("response_data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()){


                    responseinfo = postSnapshot.getValue(ResponseModel.class);


                    list.add(responseinfo);

                }
                adapter =  new RecyclerViewForResponse(getActivity(),list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
        //MenuItem  menuItem = menu.findItem(R.id.action_search);

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        if(id== R.id.home_screen){

            Intent i = new Intent(getActivity(),Home_screen.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.logout){

            firebaseAuth.getInstance().signOut();
            getActivity().finish();
            startActivity(new Intent (getActivity(),MainActivity.class));
            return  true;
        }
        return  true;
    }


}
