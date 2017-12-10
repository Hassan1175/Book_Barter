package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 18/11/2017.
 */

public class Book_catalog extends Fragment {

    // Creating DatabaseReference.
    DatabaseReference databaseReference;
    // Creating RecyclerView.
    RecyclerView recyclerView;
    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating List of Model class, which is named as uploading.. . .
    List<uploading> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.book_catalog,container,false);


        // Assign id to RecyclerView.
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);
        // Setting RecyclerView layout as LinearLayout. get activity is the conext of the mentioend class.. (Which is using as fragments)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


// Assign class to progress dialog.
        progressDialog = new ProgressDialog(getActivity());


        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Data from Remote Database. . . ..");
        progressDialog.show();


        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in Book_upload Class.
        databaseReference = FirebaseDatabase.getInstance().getReference(Book_upload.Database_path);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {

                    uploading imageUploadInfo = postSnapshot.getValue(uploading.class);

                    list.add(imageUploadInfo);
                }

                // this is the object of the custom adopter class of the recycle view. . .
                adapter = new RecyclerViewAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }
        });




        return view;
    }





}
