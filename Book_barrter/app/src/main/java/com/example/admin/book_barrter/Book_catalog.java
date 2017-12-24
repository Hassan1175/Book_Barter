package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
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
import java.util.zip.Inflater;

/**
 * Created by ADMIN on 18/11/2017.
 */

public class Book_catalog extends Fragment implements SearchView.OnQueryTextListener {
    public Button borrow;

    public static long count;

    FirebaseAuth firebaseAuth ;
    // Creating DatabaseReference.
    DatabaseReference databaseReference;
    // Creating RecyclerView.
    RecyclerView recyclerView;
    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter;


    RecyclerView.Adapter newadapter;
    DatabaseReference myref;
    Toolbar toolbar;

    RecyclerViewAdapter adapter2;



    public static String bookname;

    // Creating List of Model class, which is named as uploading.. . .
    List<uploading> list = new ArrayList<>();

    List<uploading> newlist = new ArrayList<>();

    List<String> allbooks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.book_catalog,container,false);
        //borrow = (Button) view.findViewById(R.id.newbutton);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        //that is how to use setsupportActionbar for fragment...above one is for activity..

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);






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
                    //imageUploadInfo.getmuser().toString();

                  //  allbooks.add(imageUploadInfo.getAther_name().toString());

                 //   if (firebaseAuth.getInstance().getCurrentUser().getEmail().equals(imageUploadInfo.getmuser())) {
                        list.add(imageUploadInfo);

                   // }
                }
                // this is the object of the custom adopter class of the recycle view. . .
                adapter2 = new RecyclerViewAdapter(getActivity(), list);

                recyclerView.setAdapter(adapter2);


                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }
        });



        counnt();


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //MenuInflater menuInflater ;
        //LayoutInflater inflater = LayoutInflater.from(context);
        //        View view = inflater.inflate(R.layout.popup, null);
        inflater.inflate(R.menu.menu_item,menu);
        MenuItem  menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<uploading>  newlist = new ArrayList<>();
        for(uploading upload : list){
            String  book_name = upload.getAther_name().toLowerCase();
            if(book_name.contains(newText)){
                newlist.add(upload);
            }

        }

        adapter2.setfilter(newlist);


        return true;
    }


    // till here. . ..

    public void counnt() {

        myref = FirebaseDatabase.getInstance().getReference("borrow").child(firebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace(".", "_"));
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();

                Log.i("Tag", count + "" + "Inneer");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public long getCount() {
        return count;
    }
}
