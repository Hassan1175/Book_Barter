package com.example.admin.book_barrter;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

public class welcome extends AppCompatActivity implements View.OnClickListener{

public  String my ;
    private ViewPager viewPager;

    //here i am declaring the array of intgers, which will have the ID's  of all introduction slides

    private int [] layouts = {R.layout.first_slide,R.layout.second_slide,R.layout.third_slide,R.layout.fourth_slide};

   private LinearLayout dots;
    private ImageView [] dotsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // the code to initialize the skip and next buttons of slider
        final Button skip = (Button) findViewById(R.id.skip);
        final Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        skip.setOnClickListener(this);

        //object of the adopter class
        pagerAdopter pd = new pagerAdopter(layouts,this);
        viewPager.setAdapter(pd);
        //the dots is the linear layout in which i have made the dots for slides. .
        dots = (LinearLayout)findViewById(R.id.dots);
        //callimg below method
        createdots(0);
        //the dots does not move next with slide moving so for that we have to add following method
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                createdots(position);
                //here we are specifying that if we reach the last slide then remove the buttons and load home etc
                if(position==layouts.length-1){
                    next.setText("START");
                    skip.setVisibility(View.INVISIBLE);
                }
                else {
                    next.setText("NEXT");
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //cutom method for dcreating dots on the slides
    private  void createdots(int position){
        if(dots!=null){
            dots.removeAllViews();
       dotsl = new ImageView[layouts.length];
       for (int i = 0;i<layouts.length;i++){
           dotsl [i] =  new ImageView(this);
           if(i==position){
               dotsl[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.unactive_dots));
           }
           else
               dotsl[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           //margin for linear layout
           params.setMargins(4,0,4,0);
           dots.addView(dotsl[i],params);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.next:
                nestslide();
                break;
            case R.id.skip:
                loadhome();
                break;
        }
    }

//if user press the skip button, then we have to load the home view .. so that method is for that purpose. .
    private void loadhome(){

        startActivity(new Intent(this,MainActivity.class));
        finish();

    }
    // now another method for where user will press the next button so we are supposed to show next slide to user..
    private void nestslide(){
        int next = viewPager.getCurrentItem()+1;
        if(next< layouts.length){
            viewPager.setCurrentItem(next);
        }
        else
            loadhome();

    }
}
