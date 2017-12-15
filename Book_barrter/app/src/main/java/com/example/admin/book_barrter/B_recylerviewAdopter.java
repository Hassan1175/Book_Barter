package com.example.admin.book_barrter;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by ADMIN on 15/12/2017.
 */

public class B_recylerviewAdopter  extends RecyclerView.Adapter<B_recylerviewAdopter.ViewHolder> {



    FirebaseAuth firebaseAuth ;
    FirebaseUser profile;



   // public   String test;
    //public   String test2;
    // Book_upload bu;

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


     //   TextView tv = (TextView) view.findViewById(R.id.Muser);

//        test = tv.getText().toString().trim();
    //    profile = firebaseAuth.getInstance().getCurrentUser();
      //  test2 =  profile.getEmail().toString().trim();

        //   Log.i("Tag",test + test2);


        B_recylerviewAdopter.ViewHolder viewHolder  = new B_recylerviewAdopter.ViewHolder(view);

//        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);



        return viewHolder;



    }

    @Override
    public void onBindViewHolder(B_recylerviewAdopter.ViewHolder holder, final int position) {



// borrowiteminfo is my list which posses the object of the model class of my borrowing items...
      final  BorrowModel borrowinfo = borrowiteminfo.get(position);


        holder.user_name.setText(borrowinfo.getCurrent_user());
        holder.book_type.setText(borrowinfo.getBook_sort());
        holder.arther_name.setText(borrowinfo.getName_ather());
        Glide.with(context).load(borrowinfo.getUrlofpic()).into(holder.imageView);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here i will open dialogue box for getting confirmation of accepting and deleting.....
                //
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
        public ImageView imageView;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.b_Muser);
            book_type = (TextView) itemView.findViewById(R.id.borrow_type);
            arther_name = (TextView) itemView.findViewById(R.id.b_writer_name);
            imageView = (ImageView) itemView.findViewById(R.id.borrow_book_photo);


            button = (Button) itemView.findViewById(R.id.confirmation);

            button.setText("Confirmation");

        }
    }




}
