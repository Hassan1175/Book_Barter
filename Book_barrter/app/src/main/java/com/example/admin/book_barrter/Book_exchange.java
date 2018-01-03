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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class Book_exchange extends Fragment {
    Toolbar toolbar;

    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    RecyclerView.Adapter adapter ;
    uploading imageUploadInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_exchange,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.MyrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data from Remote Database. . . ..");
        progressDialog.show();
        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in Book_upload Class.
        databaseReference = FirebaseDatabase.getInstance().getReference(Book_upload.Database_path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<uploading> list = new ArrayList<>();
                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {
                     imageUploadInfo = postSnapshot.getValue(uploading.class);

//  Here I am jsut getting the user in the cardview and the currently logged in user. After comparing their values, i am loading relevant card in the recycler view.. ..
                    if (firebaseAuth.getInstance().getCurrentUser().getEmail().equals(imageUploadInfo.getmuser())) {
                        list.add(imageUploadInfo);
                    }
                }
                //adopter setinng
                adapter = new RecyclerViewAdopterForDeleting(getActivity(), list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }

        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
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
