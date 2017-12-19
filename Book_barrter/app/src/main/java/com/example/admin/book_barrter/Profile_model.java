package com.example.admin.book_barrter;

/**
 * Created by ADMIN on 18/12/2017.
 */

public class Profile_model {

    String name;
    String contactnum;
    String colg;
    String email;
    String urlofdp;

    public Profile_model(String name, String contactnum, String colg, String email, String urlofdp) {
        this.name = name;
        this.contactnum = contactnum;
        this.colg = colg;
        this.email = email;
        this.urlofdp = urlofdp;
    }


    public Profile_model(){

    }

    public String getName() {
        return name;
    }

    public String getContactnum() {
        return contactnum;
    }

    public String getColg() {
        return colg;
    }

    public String getEmail() {
        return email;
    }

    public String getUrlofdp() {
        return urlofdp;
    }
}
