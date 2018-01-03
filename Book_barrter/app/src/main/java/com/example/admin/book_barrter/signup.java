package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 12/11/2017.
 */

public class signup extends Fragment {

    public String naaam;
    public String info;
    public String clg ;


    View view;
    Button enroll;

    EditText ed1;
    EditText ed2;
    EditText ed3;

    EditText name;
    EditText contact;
    EditText city;

    Context context;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Nullable
   @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);


        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.testsignup,container,false);
        }

        else {
            view = inflater.inflate(R.layout.signup2,container,false);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog =  new ProgressDialog(getActivity());


        name = (EditText) view.findViewById(R.id.name);
        contact = (EditText) view .findViewById(R.id.nmber);
        city = (EditText) view.findViewById(R.id.city);







         ed1 = (EditText) view.findViewById(R.id.email);
         ed2 = (EditText) view.findViewById(R.id.paswordd);

        ed3 = (EditText) view.findViewById(R.id.confirmpasword);

     //   Button      btn   = (Button) view.findViewById(R.id.registration);



        enroll = (Button) view.findViewById(R.id.registration);

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //here i will call move method. which is creating new users omn the firebase. . . . .
                move();
            }
        });

        return view;
    }
    public void move (){
         naaam = name.getText().toString().trim();
         info = contact.getText().toString().trim();
         clg = city.getText().toString().trim();
        final String email = ed1.getText().toString().trim();
        String password = ed2.getText().toString().trim();
        String matchpassword = ed3.getText().toString().trim();

       final String picurl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Please enter your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "plz enter your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(   naaam )) {
            Toast.makeText(getActivity(), "plz enter your Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(  info)) {
            Toast.makeText(getActivity(), "plz enter your Contact#", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty( clg)) {
            Toast.makeText(getActivity(), "plz enter your Working Place", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.equals(matchpassword)){
       progressDialog.setMessage("User is Registering. .  ..");
        progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Profile_Data").child(email.replace(".","_"));
                    //    String key = databaseReference.push().getKey();
                    Profile_model p_model = new Profile_model(naaam, info, clg, email, picurl);

                    try{
                        // databaseReference.child(requesting.getrequesting_user().replace(".","_")).setValue(requesting);
                        databaseReference.setValue(p_model);

                    }
                    catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getActivity(), "User has successfully enrolled", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(),Home_screen.class);
                            startActivity(i);
                          progressDialog.dismiss();
                }
                else
                    Toast.makeText(getActivity(), "Something went wring. Try Again !!!", Toast.LENGTH_LONG).show();
            }
        });

            // here i am gonna code the items, which i wil show in the profile tab. .

/*
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Profile_Data").child(email.replace(".","_"));
        //    String key = databaseReference.push().getKey();
            Profile_model p_model = new Profile_model(naaam, info, clg, email, picurl);

            try{
                // databaseReference.child(requesting.getrequesting_user().replace(".","_")).setValue(requesting);
                databaseReference.setValue(p_model);

            }
            catch (Exception e){
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
*/

    }

    else {

            Toast.makeText(getActivity(), "Password does not match !!!", Toast.LENGTH_LONG).show();
        }
    }

}
