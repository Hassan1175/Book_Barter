package com.example.admin.book_barrter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADMIN on 15/12/2017.
 */

public class B_recylerviewAdopter  extends RecyclerView.Adapter<B_recylerviewAdopter.ViewHolder> {
    private DatabaseReference ref;
    Profile_model pm;
    Calendar calendar;
    SimpleDateFormat simpledateformat ;
    public String Date;

    String response;
    String ather;
    String book_category;
    String current_user;
    String requesting_user;
    String PICURL;


    private DatabaseReference databaseReference;
//that path is to uplaod data on new child node named as response_data
    public final String Database_pathhh = "response_data";
    public TextView tv_response;


    FirebaseAuth firebaseAuth ;
    FirebaseUser profile;

    Context context;
    List<BorrowModel> borrowiteminfo;


    public B_recylerviewAdopter(Context context, List<BorrowModel> TempList) {

        this.borrowiteminfo = TempList;

        this.context = context;
    }

    @Override
    public B_recylerviewAdopter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewitemforborrw, parent, false);



        B_recylerviewAdopter.ViewHolder viewHolder  = new B_recylerviewAdopter.ViewHolder(view);

//        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);



        return viewHolder;



    }

    @Override
    public void onBindViewHolder(final B_recylerviewAdopter.ViewHolder holder, final int position) {



// borrowiteminfo is my list which posses the object of the model class of my borrowing items...
      final  BorrowModel borrowinfo = borrowiteminfo.get(position);


        holder.user_name.setText(borrowinfo.getrequesting_user());
        holder.book_type.setText(borrowinfo.getBook_sort());
        holder.arther_name.setText(borrowinfo.getName_ather());
        holder.date.setText(borrowinfo.getDate());
        Glide.with(context).load(borrowinfo.getUrlofpic()).into(holder.imageView);

// that is  the button for the  confirmaion of the bok which has been requested from a particular user . ..
        holder.confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here i will open dialogue box for getting confirmation of accepting and deleting.....

                AlertDialog.Builder altbox =  new AlertDialog.Builder(context);
// altbox.setMessage("Hy"+ FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString()+ System.getProperty("line.separator")+ "A book named  as "+borrowinfo.book_owner+" "+"has been requested by "+borrowinfo.getrequesting_user()+" "+"what is your response ? " ).setCancelable(true)
              altbox.setMessage("Do you want to share that book? " ).setCancelable(true)
                      .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                              response = "YES: Book is available for you.";

                              ather = borrowinfo.getName_ather().toString();

                              book_category = borrowinfo.getBook_sort();
                              current_user =  firebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                              requesting_user = borrowinfo.getrequesting_user().toString();
                              PICURL  =  borrowinfo.getUrlofpic().toString();
                              // here i am commenting that date, cos i supposed to send current date/time.. that is old one.. so new is found below
                           //   date = borrowinfo.getDate().toString();


                              Log.i("Tag", response + "       "+"       "+ather + "       "+book_category+ "   "+current_user+"       "+PICURL  );


                              responseback();


                              Toast.makeText(context,"Thanks for sharing your book. . .",Toast.LENGTH_LONG).show();

                              // here i am deleting the request of the book after getting confirmation


//                              Mange_request mange_request = new Mange_request();
  //                            mange_request.getPositin(position);

                              borrowiteminfo.remove(position);
                              notifyItemRemoved(position);
                              //   Log.i(LOG,"Button clicked"+ UploadInfo.getId());
                              DatabaseReference dr = FirebaseDatabase.getInstance().getReference("borrow2").child(borrowinfo.getId());
                              dr.removeValue();
                              notifyDataSetChanged();





                          }
                      })
                      .setNegativeButton("NO",new DialogInterface.OnClickListener(){

                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                              response = "Sorry: I can't share that book.";

                              ather = borrowinfo.getName_ather().toString();

                              book_category = borrowinfo.getBook_sort();
                              current_user =  firebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                              requesting_user = borrowinfo.getrequesting_user().toString();
                              PICURL  =  borrowinfo.getUrlofpic().toString();
                           //   date = borrowinfo.getDate().toString();



                              Log.i("Tag", response + "       "+"       "+ather + "       "+book_category+ "   "+current_user+"       "+PICURL  );


                              responseback();

                              Toast.makeText(context,"Thanks for your response. . . .",Toast.LENGTH_LONG).show();
                          }
                      });
                AlertDialog alert = altbox.create();
                alert.setTitle("Confirmation Response");
                alert.show();



                }
        });


        holder.requestinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestinguser= borrowinfo.getrequesting_user();
                String user = requestinguser.replace(".","_");


               ref = FirebaseDatabase.getInstance().getReference("Profile_Data").child(user);
               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       pm = dataSnapshot.getValue(Profile_model.class);
                       AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                       LayoutInflater inflater = LayoutInflater.from(context);
                       View dialogView = inflater.inflate(R.layout.popup, null);
                       TextView    naam = (TextView) dialogView.findViewById(R.id.name);
                       TextView     mail = (TextView) dialogView.findViewById(R.id.maill);
                       TextView     number = (TextView) dialogView.findViewById(R.id.fone);
                       //naam = (TextView) dialogView.findViewById(R.id.name);
                       TextView     location = (TextView) dialogView.findViewById(R.id.city);
                       ImageView    imageView= (ImageView) dialogView.findViewById(R.id.profilepic);

                       Log.i("Tag", pm.getContactnum() + "  " + pm.getName() + "  " + pm.getColg());

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

                       dialogBuilder.setView(dialogView);
                       AlertDialog alert = dialogBuilder.create();
                       alert.show();
                   }
                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });



                //borrowinfo.getUrlofpic();

                Toast.makeText(context,"Mubarkaaaaaaan",Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public int getItemCount() {


        return borrowiteminfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView user_name;
        public TextView book_type;
        public TextView arther_name;
        public TextView date;
        public ImageView imageView;
        public Button confirmation;

        public Button requestinguser;

        public ViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.b_Muser);
            book_type = (TextView) itemView.findViewById(R.id.borrow_type);
            arther_name = (TextView) itemView.findViewById(R.id.b_writer_name);
            imageView = (ImageView) itemView.findViewById(R.id.borrow_book_photo);
            requestinguser = (Button) itemView.findViewById(R.id.profileinfoo);
           date = (TextView) itemView.findViewById(R.id.Date);

            confirmation = (Button) itemView.findViewById(R.id.confirmation);

            confirmation.setText("Confirmation");
            requestinguser.setText("Requesting user profile");

        }
    }



    // here i am sending  response back to the  the user who requested a book from owner side. . ..


     public void    responseback(){



         calendar = Calendar.getInstance();
         simpledateformat = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");

         Date = simpledateformat.format(calendar.getTime());
         Date = simpledateformat.format(calendar.getTime());


         final ProgressDialog dialog = new ProgressDialog(context);
         dialog.setTitle("Sending borrowing request. . .. . .");
         dialog.show();

         databaseReference =  FirebaseDatabase.getInstance().getReference(Database_pathhh);
         String key = databaseReference.push().getKey();

         ResponseModel responseModel = new ResponseModel(response,ather,book_category,current_user,requesting_user,PICURL,Date);
        // final ResponseModel responseInfo = new ResponseModel(response,book_category,current_user,requesting_user,PICURL);

         try{
             // databaseReference.child(requesting.getrequesting_user().replace(".","_")).setValue(requesting);

             databaseReference.child(key).setValue(responseModel);

             //                 databaseReference.setValue(uploadiiinngg);
         }
         catch (Exception e){
             Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
         }
         dialog.dismiss();
         Toast.makeText(context,"Response has been sent to the requested user..  . .. ",Toast.LENGTH_LONG).show();


     }


}





