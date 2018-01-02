package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.ProgressDialog;
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

/**
 * Created by ADMIN on 12/11/2017.
 */

public class signup extends Fragment {
    View view;
    Button enroll;

    EditText ed1;
    EditText ed2;
    EditText ed3;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Nullable
   @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);


        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.signup,container,false);
        }
        else {
            view = inflater.inflate(R.layout.signup2,container,false);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog =  new ProgressDialog(getActivity());



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
        String email = ed1.getText().toString().trim();
        String password = ed2.getText().toString().trim();
        String matchpassword = ed3.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Please enter your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "plz enter your Password", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "User has successfully enrolled", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(),Home_screen.class);
                            startActivity(i);
                          progressDialog.dismiss();
                }
                else
                    Toast.makeText(getActivity(), "Something went wring. Try Again !!!", Toast.LENGTH_LONG).show();
            }
        });

    }

    else {

            Toast.makeText(getActivity(), "Password does not match !!!", Toast.LENGTH_LONG).show();
        }
    }

}
