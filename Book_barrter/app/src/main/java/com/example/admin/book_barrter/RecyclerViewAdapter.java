package com.example.admin.book_barrter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ADMIN on 10/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {


   public final String Database_pathh = "borrow";


    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public String s1;
    public String s2;
    public String s3;
    public String s4;

    public String s5;

public  String s6;

   // private static final String LOG = "TESTLOG";
    FirebaseAuth firebaseAuth ;
    FirebaseUser profile;



    public   String test;
    public   String test2;
   // Book_upload bu;

    Context context;
    List<uploading> MainImageUploadInfoList;


    public RecyclerViewAdapter(Context context, List<uploading> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


       // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);


        TextView  tv = (TextView) view.findViewById(R.id.Muser);

         test = tv.getText().toString().trim();
        profile = firebaseAuth.getInstance().getCurrentUser();
        test2 =  profile.getEmail().toString().trim();

     //   Log.i("Tag",test + test2);




        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       final  uploading UploadInfo = MainImageUploadInfoList.get(position);

            holder.user_name.setText(UploadInfo.getmuser());
            holder.book_type.setText(UploadInfo.getBook_type());
            holder.arther_name.setText(UploadInfo.getAther_name());


            Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);




        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                s1  =   firebaseAuth.getInstance().getCurrentUser().getEmail().toString();


                s2  =    UploadInfo.getmuser().toString();



                s3  = UploadInfo.getBook_type().toString();

                s4  =UploadInfo.getAther_name().toString();




                s5 = UploadInfo.getUrl().toString();





                Log.i("Tag", s1 + "       "+"       "+s2 + "       "+s3+ "      "+s4+"       "+s5  );


                forborrowthebooks();





            }
        });


        }


    @Override
    public int getItemCount() {

    //   Collections.frequency()



        return MainImageUploadInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView user_name;
        public TextView book_type;
        public TextView arther_name;
        public ImageView imageView;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.Muser);
            book_type = (TextView) itemView.findViewById(R.id.type);
            arther_name = (TextView) itemView.findViewById(R.id.writer_name);
            imageView = (ImageView) itemView.findViewById(R.id.book_photo);
            button = (Button) itemView.findViewById(R.id.newbutton);

            button.setText("Borrow Me");

        }
    }


    public void forborrowthebooks(){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Sending borrowing request. . .. . .");
        dialog.show();

        databaseReference =  FirebaseDatabase.getInstance().getReference(Database_pathh);


        String key = databaseReference.push().getKey();
        final BorrowModel requesting = new BorrowModel(s1,s2,s3,s4,s5,key);







        try{


           // databaseReference.child(requesting.getrequesting_user().replace(".","_")).setValue(requesting);

            databaseReference.child(key).setValue(requesting);



  //                 databaseReference.setValue(uploadiiinngg);
       }
      catch (Exception e){
           Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
     }
        dialog.dismiss();
        Toast.makeText(context,"Request has been sent to the owner of the book.  . .. ",Toast.LENGTH_LONG).show();





        //testing. .

/*
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<BorrowModel> details = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child("borrow");


                Iterable<DataSnapshot> Children = ds.getChildren();

                for (DataSnapshot tt : Children){
                    BorrowModel d = tt.getValue(BorrowModel.class);
                    d.getrequesting_user();
                    details.add(d);

                    int s =details.size();

                    Toast.makeText(context,"RThe legnth kf  my array list is "+ s,Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



//till here. . .


*/






        //here is just for the sake of the testing of the code..  .




        /*

// that code is just example of the codeeee
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = databaseReference.push().getKey();
                final BorrowModel requesting = new BorrowModel(s1,s2,s3,s4,s5,key);
                databaseReference.child(key).setValue(requesting);
             //   String number = dataSnapshot.getKey();
          //      requesting.getrequesting_user();

            //    String number2 = dataSnapshot.getChildrenCount()+"";
                Toast.makeText(context,"i am going to display numbers of child node. . . .", Toast.LENGTH_LONG).show();

            //    Toast.makeText(context,"Haan bhi..."+ requesting.getrequesting_user()+" "+number2,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //till here. . .

*/

    }








}
