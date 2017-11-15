package com.example.admin.book_barrter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm = getFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        login_window();


    }

    public void login_window(){

        login log =  new login();
        FragmentTransaction ft = fm.beginTransaction();
        // that login is just a tag, which we addd just for the sake of easeness .. . .so that we can identify that ft
        ft.replace(R.id.main_screen,log,"LOGIN");
        ft.commit();

    }






}
