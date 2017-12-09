package com.example.admin.book_barrter;

/**
 * Created by ADMIN on 07/12/2017.
 */

public class uploading {


   public String Muser ;
    public String book_type;
    public String ather_name;
    public String url;

    /*
    public String getMuser() {
        return Muser;
    }

    public String getBook_type() {
        return book_type;
    }

    public String getAther_name() {
        return ather_name;
    }

    public String getUrl() {
        return url;
    }

*/
    public uploading(String Muser, String book_type,String ather_name, String url) {
        this.ather_name =  ather_name;
        this.Muser = Muser;
        this.book_type = book_type;
        this.url = url;

    }

    public uploading() {
    }




}
