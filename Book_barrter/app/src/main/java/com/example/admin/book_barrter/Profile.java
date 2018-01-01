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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by ADMIN on 20/11/2017.
 */

public class Profile extends Fragment {
    Toolbar toolbar;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference dref;
    EditText name;
    EditText colg_name;
    EditText contactnum;
    EditText emailid;
    FirebaseAuth firebaseAuth;
    String user_email;
    Button update ;
    Button change_photo;

    Button edit;
    ImageView profilepic;

public String s1, s2, s3, s4, s5;
    static String user_name;
    static  String emailaddress;
    static  String colgname;
    static  String contactnumber;

Profile_model profile_model;
    private Uri imageuri;
    public static final String Storage_path = "image/";
    public static final int request_code = 1234;
 public static final String Database_path =  "Profile_Data";
    String node_name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        user_email = firebaseAuth.getInstance().getCurrentUser().getEmail().trim();
         profilepic = (ImageView) view.findViewById(R.id.dp);
        name = (EditText) view.findViewById(R.id.display_name);
        colg_name  = (EditText) view.findViewById(R.id.colg_name);
        contactnum = (EditText) view.findViewById(R.id.contactno);
          emailid=(EditText) view.findViewById(R.id.email);
        change_photo = (Button) view.findViewById(R.id.change_pic);
        edit = (Button) view.findViewById(R.id.edit);
        update = (Button) view.findViewById(R.id.update);
        name.setEnabled(false);
        colg_name.setEnabled(false);
        contactnum.setEnabled(false);
        dref = FirebaseDatabase.getInstance().getReference("Profile_Data").child(user_email.replace(".","_"));
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile_model = dataSnapshot.getValue(Profile_model.class);
                    s1=  profile_model.getName().toString();
                    s2 = profile_model.getColg().toString();
                    s3 = profile_model.getContactnum().toString();
                    s4 = firebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                    s5 = profile_model.getUrlofdp();
                name.setText(s1);
                colg_name.setText(s2);
                contactnum.setText(s3);
                emailid.setText(user_email);
                Picasso.with(view.getContext())
                        .load(s5)
                        .resize(20,20).into(profilepic);
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        change_photo.setVisibility(view.VISIBLE);
        update.setVisibility(view.VISIBLE);
        name.setEnabled(true);
        colg_name.setEnabled(true);
        contactnum.setEnabled(true);

    }
});
        storageReference = FirebaseStorage.getInstance().getReference();
        //i  am setting the node as the email address of the current user
        databaseReference =  FirebaseDatabase.getInstance().getReference(Database_path);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name =  name.getText().toString();
                colgname = colg_name.getText().toString();
                contactnumber = contactnum.getText().toString();
                UPLOADED();
            }
        });
        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void   UPLOADED() {
     if (imageuri != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Updating Profile");
            dialog.show();
            StorageReference ref = storageReference.child(Storage_path + System.currentTimeMillis() + "." + getImageExt(imageuri));
            //add file to reference
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), " Image Upoaded.  .", Toast.LENGTH_LONG).show();
                    node_name = user_email.replace(".", "_");
                    Profile_model p_model = new Profile_model(user_name, contactnumber, colgname, user_email, taskSnapshot.getDownloadUrl().toString());
                    try {
                        databaseReference.child(node_name).setValue(p_model);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //show upload progress
                    double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    dialog.setMessage("Uploaded " + (int) progress + "");
                }
            });
     }
             else{
         Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_LONG).show();
     }

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
