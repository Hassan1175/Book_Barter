package com.example.admin.book_barrter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
 * Created by ADMIN on 14/11/2017.
 */

public class login extends Fragment{
    String email;
    String password;


    Button signup1;
    FragmentManager fm;
Button loginbtn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    EditText ed1;
    EditText  ed2;
    @Nullable
    @Override

    //instances of the edit texts

   // Button signup;




    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.login,container,false);

        //cos here we are using fragments, so for it we will use view.findview
            ed1 = (EditText) view.findViewById(R.id.username);
            ed2 = (EditText) view.findViewById(R.id.password);
        loginbtn = (Button) view.findViewById(R.id.login);
        signup1 =(Button) view.findViewById(R.id.signupu);
        fm = getFragmentManager();

        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // the signin is the method , which i am calling bellow, which will login the users....
                signin();
            }
        });



        signup1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signup_window();

            }
        });


        return view;

    }

    public void signup_window(){
        signup signup = new signup();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_screen,signup);
        ft.commit();

      //  Toast.makeText(getActivity(),"hello how are u",Toast.LENGTH_SHORT).show();


    }

    public void signin(){

        email = ed1.getText().toString().trim();
        password = ed2.getText().toString().trim();



        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"plz enter your name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "plz enter your password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Authenticating. ..  .Wait. .");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                       // Toast.makeText(getActivity(),"hello i am here",Toast.LENGTH_LONG).show();

                        //     progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent i = new Intent(getActivity(),welcome.class);
                            startActivity(i);

                            Toast.makeText(getActivity(),"Welcome welcomeee",Toast.LENGTH_SHORT).show();


                        }
                        else
                            Toast.makeText(getActivity(),"Sorry somthing went wrong, try again. . ",Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
