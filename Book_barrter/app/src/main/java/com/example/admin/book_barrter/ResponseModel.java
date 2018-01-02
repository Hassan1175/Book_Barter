package com.example.admin.book_barrter;

/**
 * Created by ADMIN on 17/12/2017.
 */

public class ResponseModel {

    String response;
    String ather;
    String book_category;
    String current_user;
    String requesting_user;
    String picurl;
    String date;
    String id;
    public ResponseModel(String response, String ather, String book_category, String current_user, String requesting_user, String picurl, String date, String id) {
        this.response = response;
        this.ather = ather;
        this.book_category = book_category;
        this.current_user = current_user;
        this.requesting_user = requesting_user;
        this.picurl = picurl;
        this.date = date;
        this.id = id;
    }

    public ResponseModel(){

    }

    public String getResponse() {
        return response;
    }

    public String getAther() {
        return ather;
    }

    public String getBook_category() {
        return book_category;
    }

    public String getCurrent_user() {
        return current_user;
    }

   public String getRequesting_user() {
        return requesting_user;
    }

    public String getPicurl() {
        return picurl;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
