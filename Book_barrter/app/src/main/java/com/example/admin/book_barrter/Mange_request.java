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

    Toolbar toolbar;
    FirebaseAuth firebaseAuth ;
    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    DatabaseReference dref;
    // Creating RecyclerView.
    RecyclerView recyclerView;
    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating List of Model class, which is named as uploading.. . .
    List<BorrowModel> list = new ArrayList<>();

    public static  String owner;

    BorrowModel borrowModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.manage_request,container,false);


        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        //that is how to use setsupportActionbar for fragment...above one is for activity..

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);


        recyclerView = (RecyclerView)view.findViewById(R.id.b_recyclerView);

        recyclerView.setHasFixedSize(true);
        // Setting RecyclerView layout as LinearLayout. get activity is the conext of the mentioend class.. (Which is using as fragments)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());


        // Setting up message in Progress dialog.
        progressDialog.setMessage("Requests from users are loading. .  ..");
        progressDialog.show();


        Toast.makeText(getActivity(), "Hello I ahere.    .", Toast.LENGTH_SHORT).show();



        databaseReference = FirebaseDatabase.getInstance().getReference("borrow2");
        Toast.makeText(getActivity(), "Hello I ahere. again   .", Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    borrowModel =  postSnapshot.getValue(BorrowModel.class);

// here i have to put the check that, the requests of currently logged in user should be displayed from the common
// databse of the  boorw node of borrowing requests database. .     . ..
//thtat check is working perfecly.. for testing sake, it hs been commented
                //   if (firebaseAuth.getInstance().getCurrentUser().getEmail().equals(borrowModel.getBook_owner())) {
                        list.add(borrowModel);
                  //  }
                }

                adapter =  new B_recylerviewAdopter(getActivity(),list);
                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();

            }

          /*
public void getPositin(int position){
    list.remove(position);
    //firebase database remove value
    adapter.notifyDataSetChanged();;
}
*/
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
