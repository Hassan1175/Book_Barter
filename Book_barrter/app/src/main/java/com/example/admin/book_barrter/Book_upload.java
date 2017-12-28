package com.example.admin.book_barrter;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ADMIN on 18/11/2017.
 */

public class Book_upload extends Fragment {

    Calendar calander;
    SimpleDateFormat simpledateformat;
    static String Date;

    Toolbar toolbar;



     Spinner spinner;
    ArrayAdapter<CharSequence> adapter;




    FirebaseAuth firebaseAuth ;

    Button btn;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    FirebaseUser profile;



    TextView user;
    public static String user_profile ;
    public static String display_date ;
  public  String id;


    public static String category;
    private EditText editText;
    private ImageView imageView;

    private Uri imageuri;

    Button  browse;
    Button upload;
    ProgressDialog progressDialog;

    public static final String Storage_path = "image/";

    public static final String Database_path = "image";

    public static final int request_code = 1234;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.book_upload,container,false);


        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        //that is how to use setsupportActionbar for fragment...above one is for activity..

      ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);


        progressDialog =  new ProgressDialog(getActivity());

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference =  FirebaseDatabase.getInstance().getReference(Database_path);
        user= (TextView) view.findViewById(R.id.useremail);
        editText = (EditText) view.findViewById(R.id.arther_name);
//String which has category, we have decalred above
        browse = (Button) view.findViewById(R.id.browse_pic);
        upload = (Button) view.findViewById(R.id.upload);
        imageView = (ImageView)view.findViewById(R.id.image);


      //  Date = simpledateformat.format(calander.getTime());


     //   FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            profile =firebaseAuth.getInstance().getCurrentUser();
        user.setText(profile.getEmail().trim());

           user_profile =  user.getText().toString();
        //    user_profile =

       // DisplayDateTime.setText(Date);

      //  display_date =   DisplayDateTime.getText().toString();




//  cos here i am using fragments for my application, so i will inherit findviewby id methid from view claass
       // /*
        spinner = (Spinner) view.findViewById(R.id.book_category);

        //here i am getting data fromm the  string resourse, where i have made an array named as Book_Category,(arts, science, general)
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Book_Category,android.R.layout.simple_spinner_item);
        // the first one is  for the each item of the drop down, while second is the for dropdown view resourse
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //here action listener for the selected item of the spinner

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),parent.getItemAtPosition(position) + "Selected",Toast.LENGTH_LONG).show();
                category =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        browse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),request_code);

            }
        });

        upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               UPLOADED();
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  Log.i("hahaha","in calling start activity");

        if(requestCode == requestCode && resultCode == Activity.RESULT_OK && data!=null && data.getData()!=null){

            imageuri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageuri);
                imageView.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public  String getImageExt(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
       // Log.i("hahaha","Sting");

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


public void UPLOADED(){

    //the method to get date and time
  datetime();
    if(imageuri!=null){
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Uploading Image.. .");
        dialog.show();

        //get the storgae reference
        StorageReference ref = storageReference.child(Storage_path + System.currentTimeMillis()+"." +getImageExt(imageuri));


        //add file to reference
        ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();
                Toast.makeText(getActivity()," Image Upoaded.  .",Toast.LENGTH_LONG).show();

                //save image info to firebase database

                String uploaded = databaseReference.push().getKey();
                uploading uploadiiinngg = new uploading(user_profile,category,editText.getText().toString(),uploaded , taskSnapshot.getDownloadUrl().toString(),Date);
                try{


                    databaseReference.child(uploaded).setValue(uploadiiinngg);

//                    databaseReference.setValue(uploadiiinngg);
                }
                catch (Exception e){
                   Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //show upload progress
                double progress = (100 *taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                dialog.setMessage("Uploaded "+ (int)progress+"");

            }
        });

    }else {
        Toast.makeText(getActivity(),"Please Select Image",Toast.LENGTH_LONG).show();
    }



}


public void datetime(){

    calander = Calendar.getInstance();
    simpledateformat = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");

    Date = simpledateformat.format(calander.getTime());
    Date = simpledateformat.format(calander.getTime());


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
