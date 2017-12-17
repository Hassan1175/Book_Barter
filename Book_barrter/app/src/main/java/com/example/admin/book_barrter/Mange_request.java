package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class Mange_request extends Fragment {
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
    List<BorrowModel> list = new ArrayList<>();


        BorrowModel borrowModel;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.manage_request,container,false);


        recyclerView = (RecyclerView)view.findViewById(R.id.b_recyclerView);

        recyclerView.setHasFixedSize(true);
        // Setting RecyclerView layout as LinearLayout. get activity is the conext of the mentioend class.. (Which is using as fragments)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());


        // Setting up message in Progress dialog.
        progressDialog.setMessage("Requests from users are loading. .  ..");
        progressDialog.show();


        Toast.makeText(getActivity(), "Hello I ahere.    .", Toast.LENGTH_SHORT).show();


        databaseReference = FirebaseDatabase.getInstance().getReference("borrow");
        Toast.makeText(getActivity(), "Hello I ahere. again   .", Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    borrowModel =  postSnapshot.getValue(BorrowModel.class);

// here i have to put the check that, the requests of currently logged in user should be displayed from the common
// databse of the  boorw node of borrowing requests database. .     . ..

                    if (firebaseAuth.getInstance().getCurrentUser().getEmail().equals(borrowModel.getBook_owner())) {
                        list.add(borrowModel);
                    }
                }

                adapter =  new B_recylerviewAdopter(getActivity(),list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return view;
    }
}
