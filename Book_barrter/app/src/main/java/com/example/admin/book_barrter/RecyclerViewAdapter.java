package com.example.admin.book_barrter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ADMIN on 10/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    TextView naam;
    TextView number;
    TextView mail;
    TextView location;

    ImageView imageView;

    public static long count2;

    public final String Database_pathh = "borrow";
    public final String Database_pathh2 = "borrow2";
    private DatabaseReference databaseReference2;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    Profile_model pm;
    //that is the reference for the profile picture  . . .  ..
    private DatabaseReference databaseReferenceforprofile;


    DatabaseReference myref;

    private DatabaseReference myprofile;

    public static String clicked_owner_info;
    public static String owner;


    public String s1;
    public String s2;
    public String s3;
    public String s4;

    public String s5;

    public String s6;

    // private static final String LOG = "TESTLOG";
    FirebaseAuth firebaseAuth;
    FirebaseUser profile;
    public String test;
    public String test2;
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



// that is just the object of the fragment class, from which i am just getting the number of child nodes.

    Book_catalog catalog = new Book_catalog();
        count2 = catalog.getCount();

    Log.i("Tag", count2 + ""+ "outeeeeer.");






        TextView tv = (TextView) view.findViewById(R.id.Muser);

        test = tv.getText().toString().trim();
        profile = firebaseAuth.getInstance().getCurrentUser();
        test2 = profile.getEmail().toString().trim();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final uploading UploadInfo = MainImageUploadInfoList.get(position);

        holder.user_name.setText(UploadInfo.getmuser());
        holder.book_type.setText(UploadInfo.getBook_type());
        holder.arther_name.setText(UploadInfo.getAther_name());
        holder.DisplayDateTime.setText(UploadInfo.getDate());

        Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);


        //that button will jsut upload the data on the server for the requested book by the requesting user
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = firebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                s2 = UploadInfo.getmuser().toString();
                s3 = UploadInfo.getBook_type().toString();
                s4 = UploadInfo.getAther_name().toString();
                s5 = UploadInfo.getUrl().toString();
                s6 = UploadInfo.getDate().toString();
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

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            // uploading UploadInfo = postSnapshot.getValue(uploading.class);
                            uploading imageUploadInfo = MainImageUploadInfoList.get(position);

                            clicked_owner_info = imageUploadInfo.getmuser();

                            owner = clicked_owner_info.replace(".", "_");
                        }
                        // in above data change method, i made datachange method to get the email of the owner of book, then below i am makng again
                        // database reference to get the data of the got email address... . .

                        myprofile = FirebaseDatabase.getInstance().getReference("Profile_Data").child(owner);
                        myprofile.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //   for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {

//pm is  the object of my profile model class
                                pm = dataSnapshot.getValue(Profile_model.class);


                                Log.i("Tag", pm.getContactnum() + "  " + pm.getName() + "  " + pm.getColg());


                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

                                  LayoutInflater inflater = LayoutInflater.from(context);
                                        View dialogView = inflater.inflate(R.layout.popup, null);

                                    naam = (TextView) dialogView.findViewById(R.id.name);
                                mail = (TextView) dialogView.findViewById(R.id.maill);
                                number = (TextView) dialogView.findViewById(R.id.fone);
                                naam = (TextView) dialogView.findViewById(R.id.name);
                                location = (TextView) dialogView.findViewById(R.id.city);
                               imageView= (ImageView) dialogView.findViewById(R.id.profilepic);

                                String num = pm.getContactnum();
                                String col  =pm.getColg();
                              String n=  pm.getName();
                                String e = pm.getEmail();
                            String pic =   pm.getUrlofdp();

                                //that is just to convet URL of  the image into the image from picsso library and set it into the imageview
                                Picasso.with(context)
                                        .load(pic)
                                        .resize(20,20).into(imageView);


                               naam.setText(n);
                               number.setText(num);
                                mail.setText(e);
                                location.setText(col);
                            //    URL url = new URL(pm.getUrlofdp());
                               // Bitmap bmp = BitmapFactory.decodeStream(pic);
                              //  imageView.setImageBitmap(bmp);



                                dialogBuilder.setView(dialogView);

                            /*
                            in order to inflate layout for the dialogue box in android
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
LayoutInflater inflater = this.getLayoutInflater();
View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
dialogBuilder.setView(dialogView);

EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
editText.setText("test label");
AlertDialog alertDialog = dialogBuilder.create();
alertDialog.show();
                                 */

                           //     AlertDialog.Builder altbox = new AlertDialog.Builder(context);
                           //     altbox.setMessage("Name  " + pm.getName() + "\n" + "Colg  " + pm.getColg() + "\n" + "Contact  " + pm.getContactnum()).setCancelable(true);
                                AlertDialog alert = dialogBuilder.create();

                         //       alert.setTitle("Owner Info");
                                // alert.setView(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

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

            DisplayDateTime = (TextView) itemView.findViewById(R.id.Date);


            button = (Button) itemView.findViewById(R.id.newbutton);
            button.setText("Borrow Me");

        }
    }


    public void forborrowthebooks() {
          final ProgressDialog dialog = new ProgressDialog(context);
        //   dialog.setTitle("Sending borrowing request. . .. . .");
        //  dialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_pathh);
        String key = databaseReference.push().getKey();
        final BorrowModel requesting = new BorrowModel(s1, s2, s3, s4, s5, key,s6);
        //till here
        if (count2 <= 3) {
            try {

                databaseReference.child(s1.replace(".", "_")).child(key).setValue(requesting);
//databaseReference2 is just a jugaar to show the requests to the relevant users
                databaseReference2 = FirebaseDatabase.getInstance().getReference(Database_pathh2);
                databaseReference2.child(key).setValue(requesting);

                count2++;
         //       Log.i("Tag", count + ""+ "second");
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            dialog.dismiss();
          Toast.makeText(context, "Request has been sent to the owner of the book.  . .. ", Toast.LENGTH_LONG).show();
        }

           else{
            AlertDialog.Builder altbox =  new AlertDialog.Builder(context);
            altbox.setMessage("Request Failed !!! As you have borrowed four book, which is maximum limit.");
            AlertDialog alert = altbox.create();
            alert.setTitle("Request Response");
            alert.show();


        }

    }


// that method is for searching

    public void setfilter ( List<uploading>newwlsit){

        //re initializing the list
        MainImageUploadInfoList = new ArrayList<>();
        MainImageUploadInfoList.addAll(newwlsit);
        notifyDataSetChanged();
    }

//till here  . . .   ..   .




}
