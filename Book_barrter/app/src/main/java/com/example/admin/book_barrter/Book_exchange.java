package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class Book_exchange extends Fragment {
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
    List<uploading> list = new ArrayList<>();

    Button frodeleting;
    uploading imageUploadInfo;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.book_exchange,container,false);






        recyclerView = (RecyclerView)view.findViewById(R.id.MyrecyclerView);

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

                     imageUploadInfo = postSnapshot.getValue(uploading.class);
                    // imageUploadInfo.getmuser().toString();



//  Here I am jsut getting the user in the cardview and the current,y logged in user. After comparing their values, i am loading relevant card in the recycler view.. ..

                    Log.i("Tag",  imageUploadInfo.getId());


                    if (firebaseAuth.getInstance().getCurrentUser().getEmail().equals(imageUploadInfo.getmuser())) {
                        list.add(imageUploadInfo);
                        
                    }



                }
                adapter = new RecyclerViewAdapter(getActivity(), list);

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }



        });




       // final uploading up = new uploading();
       // Log.i("Tag",up.getId());

//        frodeleting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(imageUploadInfo!=null)
//                deleting(imageUploadInfo.getId());
//            }
//        });





        return view;
    }




    public void deleting(String id){




    DatabaseReference dr =    FirebaseDatabase.getInstance().getReference(Book_upload.Database_path).child(id);
        dr.removeValue();

        Toast.makeText(getActivity(),"Here i have deletd your user. . . ",Toast.LENGTH_LONG).show();




    }
}
