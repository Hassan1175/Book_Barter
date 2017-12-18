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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by ADMIN on 20/11/2017.
 */

public class Profile extends Fragment {
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    EditText name;
    EditText colg_name;
    EditText contactnum;
    EditText emailid;
    FirebaseAuth firebaseAuth;
    String user_email;
    Button update ;
    Button change_photo;
    ImageView profilepic;


    static String user_name;
    static  String emailaddress;
    static  String colgname;
    static  String contactnumber;




    private Uri imageuri;
    public static final String Storage_path = "image/";
    public static final int request_code = 1234;

 public static final String Database_path =  "Profile_Data";
    String node_name;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile,container,false);

        user_email = firebaseAuth.getInstance().getCurrentUser().getEmail().trim();

      profilepic = (ImageView) view.findViewById(R.id.dp);
        name = (EditText) view.findViewById(R.id.display_name);
        colg_name  = (EditText) view.findViewById(R.id.colg_name);
        contactnum = (EditText) view.findViewById(R.id.contactno);
          emailid=(EditText) view.findViewById(R.id.email);
        change_photo = (Button) view.findViewById(R.id.change_pic);
        update = (Button) view.findViewById(R.id.profileinfo);

        emailid.setText(user_email);


        user_name =  name.getText().toString();
    //    emailaddress =emailid.getText().toString();
        colgname = colg_name.getText().toString();
        contactnumber = contactnum.getText().toString();


        //cos foirebase does not accept .  in node name.. se   "."    of .com  has been replaced by _



        storageReference = FirebaseStorage.getInstance().getReference();

        //i  am setting the node as the email address of the current user
        databaseReference =  FirebaseDatabase.getInstance().getReference(Database_path);





        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_name =  name.getText().toString();
                //    emailaddress =emailid.getText().toString();
                colgname = colg_name.getText().toString();
                contactnumber = contactnum.getText().toString();




                change_photo.setVisibility(v.VISIBLE);

                name.setEnabled(true);
                name.setVisibility(v.VISIBLE);
                colg_name.setEnabled(true);
                contactnum.setEnabled(true);


                //the method to put data on firebase

                UPLOADED();


            }
        });


        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getActivity(),"Hy you can chnage pic hee",Toast.LENGTH_LONG).show();
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),request_code);


            }
        });




        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  Log.i("hahaha","in calling start activity");

        if (requestCode == requestCode && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            imageuri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageuri);
                profilepic.setImageBitmap(bm);


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

    public void   UPLOADED(){

        if(imageuri!=null){
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Uploading Data.. .");
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

                   // String uploaded = databaseReference.push().getKey();
                    node_name =  user_email.replace(".","_");

                   Profile_model p_model = new Profile_model(user_name, contactnumber,colgname,user_email,taskSnapshot.getDownloadUrl().toString());


                    try{
                       databaseReference.child(node_name).setValue(p_model);

               //     databaseReference.setValue(p_model);
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

}
