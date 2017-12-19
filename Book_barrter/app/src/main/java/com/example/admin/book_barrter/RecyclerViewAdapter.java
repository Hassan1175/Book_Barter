package com.example.admin.book_barrter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    TextView tv;


   public final String Database_pathh = "borrow";
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    Profile_model pm;
//that is the reference for the profile picture  . . .  ..
    private DatabaseReference databaseReferenceforprofile;

    private DatabaseReference myprofile;

    public static String clicked_owner_info;
    public static String owner;


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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        TextView  tv = (TextView) view.findViewById(R.id.Muser);

         test = tv.getText().toString().trim();
        profile = firebaseAuth.getInstance().getCurrentUser();
        test2 =  profile.getEmail().toString().trim();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       final  uploading UploadInfo = MainImageUploadInfoList.get(position);

            holder.user_name.setText(UploadInfo.getmuser());
            holder.book_type.setText(UploadInfo.getBook_type());
            holder.arther_name.setText(UploadInfo.getAther_name());
            holder.DisplayDateTime.setText(UploadInfo.getDate());

            Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);



          //that button will jsut upload the data on the server for the requested book
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1  =   firebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                s2  =    UploadInfo.getmuser().toString();
                s3  = UploadInfo.getBook_type().toString();
                s4  =UploadInfo.getAther_name().toString();
                s5 = UploadInfo.getUrl().toString();
                forborrowthebooks();
            }
        });


        // that  is button to show the information about the user in the owner info button
        holder.owner_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReferenceforprofile = FirebaseDatabase.getInstance().getReference("image");
                databaseReferenceforprofile.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {
                           // uploading UploadInfo = postSnapshot.getValue(uploading.class);
                            uploading imageUploadInfo = MainImageUploadInfoList.get(position);

                            clicked_owner_info =   imageUploadInfo.getmuser();

                            owner =  clicked_owner_info.replace(".","_");

                        }
 // in above data change method, i made datachange method to get the email of the owner of book, then below i am makng again
  // database reference to get the data of the got email address... . .

                        myprofile = FirebaseDatabase.getInstance().getReference("Profile_Data").child(owner);
                        myprofile.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //   for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {

                                pm = dataSnapshot.getValue(Profile_model.class);
                                // imageUploadInfo.getmuser().toString();

                                //   if (firebaseAuth.getInstance().getCurrentUser().getEmail().equals(imageUploadInfo.getmuser())) {
                                pm.getContactnum();
                                pm.getColg();
                                pm.getName();

                                Log.i("Tag",pm.getContactnum()+"  "+pm.getName()+"  "+pm.getColg());


                           //   LayoutInflater inflater = LayoutInflater.from(context);
                           //        View view = inflater.inflate(R.layout.popup, null);
                           //     tv = (TextView) view.findViewById(R.id.test);
                           //     tv.setText("I love you Khan Sahb");

                                AlertDialog.Builder altbox =  new AlertDialog.Builder(context);
                                altbox.setMessage("Name  "+pm.getName()+"\n"+"Colg  "+pm.getColg()+"\n"+"Contact  "+pm.getContactnum() ).setCancelable(true);
                                AlertDialog alert = altbox.create();
                                alert.setTitle("Owner Info");
                                alert.show();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        //till here i am fetching info of the owner of the book..


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        }

    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView user_name;
        public TextView book_type;
        public TextView arther_name;
        public ImageView imageView;
        TextView DisplayDateTime;
        public Button button;

        public Button owner_data;

        public ViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.Muser);
            book_type = (TextView) itemView.findViewById(R.id.type);
            arther_name = (TextView) itemView.findViewById(R.id.writer_name);
            imageView = (ImageView) itemView.findViewById(R.id.book_photo);
            owner_data = (Button) itemView.findViewById(R.id.owner_info);

            DisplayDateTime = (TextView)itemView.findViewById(R.id.Date);


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
    }
}
