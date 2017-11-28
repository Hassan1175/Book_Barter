package com.example.admin.book_barrter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home_screen extends AppCompatActivity {
    public String head1;
    public String head2;
    public String head3;
    public String head4;
    public String head5;
    public String head6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        TextView tv1 = (TextView) findViewById(R.id.header1);
        TextView tv2 = (TextView) findViewById(R.id.header2);
        TextView tv3 = (TextView) findViewById(R.id.header3);
        TextView tv4 = (TextView) findViewById(R.id.header4);
        TextView tv5 = (TextView) findViewById(R.id.header5);
        TextView tv6 = (TextView) findViewById(R.id.header6);

        head1 = tv1.getText().toString().trim();
        head2 = tv2.getText().toString().trim();
        head3 = tv3.getText().toString().trim();
        head4 = tv4.getText().toString().trim();
        head5 = tv5.getText().toString().trim();
        head6 = tv6.getText().toString().trim();
    }
    public void Book_catalog(View view) {

        Intent i = new Intent(this,App_start.class);
        i.putExtra("key",head1);
        startActivity(i);
    }

    public void Book_uplaod(View view) {
        Intent i = new Intent(this,App_start.class);
        i.putExtra("key",head2);
        startActivity(i);
    }


    public void Books_Exchange(View view) {
        Intent i = new Intent(this,App_start.class);
        i.putExtra("key",head3);
        startActivity(i);
    }

    public void Manage_Requests(View view) {
        Intent i = new Intent(this,App_start.class);
        i.putExtra("key",head4);
        startActivity(i);
    }

    public void History(View view) {
        Intent i = new Intent(this,App_start.class);
        i.putExtra("key",head5);
        startActivity(i);
    }

    public void My_profile(View view) {
        Intent i = new Intent(this,App_start.class);
        i.putExtra("key",head6);
        startActivity(i);
    }

}
