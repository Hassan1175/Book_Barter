package com.example.admin.book_barrter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ADMIN on 10/11/2017.
 */

public class pagerAdopter extends PagerAdapter {

    private int [] layouts;
    private LayoutInflater layoutInflater;
    private Context context;


    public pagerAdopter(int[] layouts,Context context){


        this.context = context;
        this.layouts= layouts;

        //we are just initializing the layout inflator
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        //here we can return the size of the int array. ..

        return layouts.length;


    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(layouts[position],container,false);

        //now add  that view to container
        container.addView(view);
        return view;



        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        //delete previous slide when we move forward. . .

        View view =  (View) object;  // type casting the view object

        container.removeView(view) ;
        //super.destroyItem(container, position, object);
    }
}
