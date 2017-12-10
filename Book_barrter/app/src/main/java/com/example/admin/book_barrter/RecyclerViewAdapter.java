package com.example.admin.book_barrter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ADMIN on 10/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {


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

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        uploading UploadInfo = MainImageUploadInfoList.get(position);

        holder.user_name.setText(UploadInfo.getmuser());
        holder.book_type.setText(UploadInfo.getBook_type());
        holder.arther_name.setText(UploadInfo.getAther_name());


        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);
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

        public ViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.Muser);
            book_type = (TextView) itemView.findViewById(R.id.type);
            arther_name = (TextView) itemView.findViewById(R.id.writer_name);
            imageView = (ImageView) itemView.findViewById(R.id.book_photo);


        }
    }
}
