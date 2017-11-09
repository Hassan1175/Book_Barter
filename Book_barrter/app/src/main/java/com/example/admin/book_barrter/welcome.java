package com.example.admin.book_barrter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class welcome extends AppCompatActivity {


    private ViewPager viewPager;

    //here i am declaring the array of intgers, which will have the ID's  of all introduction slides

    private int [] layouts = {R.layout.first_slide,R.layout.second_slide,R.layout.third_slide,R.layout.fourth_slide};

   //  pagerAdopter pageadpt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //object of the adopter class
        pagerAdopter pd = new pagerAdopter(layouts,this);
        viewPager.setAdapter(pd);

    }
}
