package com.example.admin.book_barrter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signup(View view) {

        signup sign = new signup();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft  = fm.beginTransaction();

        // the last parameter is known as the fragment TAG ,findfragmentbyTAG, it can be used for that finding purpose...so it just a string value
         ft.add(R.id.frames,sign,"SIGNUP");
        ft.commit();



    }
}
