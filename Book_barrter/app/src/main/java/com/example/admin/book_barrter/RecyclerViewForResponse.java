package com.example.admin.book_barrter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * Created by ADMIN on 17/12/2017.
 */

public class RecyclerViewForResponse extends RecyclerView.Adapter<RecyclerViewForResponse.ViewHolder> {

    FirebaseAuth firebaseAuth ;
    FirebaseUser profile;


    Context context;
    List<ResponseModel> responseiteminfo;


    public RecyclerViewForResponse (Context context, List<ResponseModel> TempList) {

        this.responseiteminfo = TempList;

        this.context = context;
    }

    @Override
    public  RecyclerViewForResponse.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewforresponse, parent, false);



        RecyclerViewForResponse.ViewHolder viewHolder  = new  RecyclerViewForResponse.ViewHolder(view);

//        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);



        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final  RecyclerViewForResponse.ViewHolder holder, final int position) {

        final  ResponseModel Responseinfo = responseiteminfo.get(position);



     //   holder.user_name.setText(Responseinfo.getCurrent_user());

        holder.user_name.setText(Responseinfo.getRequesting_user());
        holder.book_type.setText(Responseinfo.getBook_category());
        holder.arther_name.setText(Responseinfo.getAther());
        holder.date.setText(Responseinfo.getDate());

        holder.Status.setText(Responseinfo.getResponse());
        Glide.with(context).load(Responseinfo.getPicurl()).into(holder.imageView);



        //here i am performing the deleting actions where the user can delete those books, which he uploaded. ..  .
/*
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG,"Button clicked"+ UploadInfo.getId());
                DatabaseReference dr =    FirebaseDatabase.getInstance().getReference(Book_upload.Database_path).child(UploadInfo.getId());
                dr.removeValue();

                //  Toast.makeText(RecyclerViewAdapter.this,"Here i have deletd your user. . . ",Toast.LENGTH_LONG).show();

            }
        });

*/
    }

    @Override
    public int getItemCount() {


        return responseiteminfo.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
           /// public TextView Requested_user;
        public TextView book_type;
        public TextView arther_name;
        public TextView Status;
            public  TextView date;
        public ImageView imageView;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            Status=(TextView) itemView.findViewById(R.id.Status);
         //   Requested_user =(TextView) itemView.findViewById(R.id.requested);
            user_name = (TextView) itemView.findViewById(R.id.R_Muser);
            book_type = (TextView) itemView.findViewById(R.id.R_type);
            arther_name = (TextView) itemView.findViewById(R.id.R_writer_name);
            imageView = (ImageView) itemView.findViewById(R.id.R_book_photo);
            date = (TextView) itemView.findViewById(R.id.R_Date);


            button = (Button) itemView.findViewById(R.id.confirmation);

            button.setText("Delete Info");

        }
    }
}
