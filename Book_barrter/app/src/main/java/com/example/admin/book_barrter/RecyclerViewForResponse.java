package com.example.admin.book_barrter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewforresponse, parent, false);
        RecyclerViewForResponse.ViewHolder viewHolder  = new  RecyclerViewForResponse.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final  RecyclerViewForResponse.ViewHolder holder, final int position) {

        final  ResponseModel Responseinfo = responseiteminfo.get(position);
       // holder.user_name.setText(Responseinfo.getRequesting_user());


        holder.user_name.setText( Responseinfo.getCurrent_user());
        holder.book_type.setText(Responseinfo.getBook_category());
        holder.arther_name.setText(Responseinfo.getAther());
        holder.date.setText(Responseinfo.getDate());
        holder.Status.setText(Responseinfo.getResponse());
        Glide.with(context).load(Responseinfo.getPicurl()).into(holder.imageView);


        //here to perform delete on the history/responces......
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder altbox = new AlertDialog.Builder(context);
                altbox.setMessage("Are you sure, you want to permanently delete that record from your side? ").setCancelable(true)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            //    MainImageUploadInfoList.remove(position);
                                responseiteminfo.remove(position);
                                notifyItemRemoved(position);
                                //   Log.i(LOG,"Button clicked"+ UploadInfo.getId());
                                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("response_data").child(Responseinfo.getId());
                                dr.removeValue();
                                // notifyDataSetChanged();
                                Toast.makeText(context, "Record has been deleted. . .", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = altbox.create();
                alert.setTitle("Confirmation Response");
                alert.show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return responseiteminfo.size();
    }
        public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public TextView book_type;
        public TextView arther_name;
        public TextView Status;
            public  TextView date;
        public ImageView imageView;
        public Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            Status=(TextView) itemView.findViewById(R.id.Status);

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