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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by ADMIN on 10/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {


    private static final String LOG = "TESTLOG";
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





       // Log.i("Tag",firebaseAuth.getInstance().getCurrentUser().getEmail().toString()+" "+UploadInfo.getmuser().toString());
       // if(firebaseAuth.getInstance().getCurrentUser().getEmail().equals(UploadInfo.getmuser())){
            holder.user_name.setText(UploadInfo.getmuser());
         //   Log.i("Tag",holder.user_name.getText().toString());

            holder.book_type.setText(UploadInfo.getBook_type());
            holder.arther_name.setText(UploadInfo.getAther_name());
            Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG,"Button clicked"+ UploadInfo.getId());
                DatabaseReference dr =    FirebaseDatabase.getInstance().getReference(Book_upload.Database_path).child(UploadInfo.getId());
                dr.removeValue();

                //  Toast.makeText(RecyclerViewAdapter.this,"Here i have deletd your user. . . ",Toast.LENGTH_LONG).show();

            }
        });
        //}

        }



            //Loading image from Glide library.

//    }

    @Override
    public int getItemCount() {
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

        }
    }
}
