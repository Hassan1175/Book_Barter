package com.example.admin.book_barrter;

/**
 * Created by ADMIN on 03/01/2018.
 */

public class signupmodel {

    public  String bnda;
    public  String contactnumber;
    public String place;
    public  String id ;

    public signupmodel(String bnda, String contactnumber, String place, String id) {
        this.bnda = bnda;
        this.contactnumber = contactnumber;
        this.place = place;
        this.id = id;
    }
// thay is just a default model class, which is mandatory ..
    public signupmodel(){

    }

    public String getBnda() {
        return bnda;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public String getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }
}
