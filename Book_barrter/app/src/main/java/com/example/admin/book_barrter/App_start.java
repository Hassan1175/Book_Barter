package com.example.admin.book_barrter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class App_start extends AppCompatActivity {

    public String compare1 = "Books Catalog";
    public String compare2 = "Upload Books";
    public String compare3 = "Books Exchanging";
    public String compare4 = "Manage Requests";
    public String compare5 = "History";
    public String compare6 = "My Profile";

    public String get;

    public String ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        //here i am getting all of the strings which are names of the header of the cardview in the home_scereen activity. .
        get = getIntent().getStringExtra("key");

//The checkmethoosis made below, which is getting track that which cardview has been clicked. the on that base the relevant fragement will be loaded..
        checkmethod();

    }
    public void checkmethod(){
        if(get.equals(compare1) ){
            book_catalog();
        }
        else if(get.equals(compare2) ){
            book_upload();
        }
        else if(get.equals(compare3) ){
            book_exchange();
        }
        else if(get.equals(compare4) ){
            manage_requets();
        }
        else if(get.equals(compare5) ){
            history();
        }
        else if(get.equals(compare6) ){
            My_profile();
        }
    }

    //here i am making all methods for fragemnt, which will load on loading that class, mean that will be the

    public void book_catalog(){
        Book_catalog books=  new Book_catalog();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.app_start,books,"Book_catalog");
        ft.commit();
    }


    public void book_upload(){
     Book_upload bu =  new Book_upload();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.app_start,bu,"book_upload");
        ft.commit();
    }



    public void book_exchange(){
        Book_exchange be = new Book_exchange();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.app_start,be,"Book_catalog");
        ft.commit();
    }

    public void manage_requets(){
        Mange_request mange_request = new Mange_request();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.app_start, mange_request,"Requests");
        ft.commit();
    }

    public void history(){
        History history = new History();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.app_start,history,"Book_catalog");
        ft.commit();
    }

    public void My_profile(){
Profile profile = new Profile();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.app_start,profile,"Book_catalog");
        ft.commit();
    }



}
