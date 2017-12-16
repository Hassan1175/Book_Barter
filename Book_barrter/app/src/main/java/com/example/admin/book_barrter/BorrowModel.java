package com.example.admin.book_barrter;

/**
 * Created by ADMIN on 15/12/2017.
 */

public class BorrowModel {

    public String id;
    public String requesting_user;
    public String book_owner ;
    public String book_sort;
    public String name_ather;
    public String urlofpic;

    public String getId() {
        return id;
    }

    public String getrequesting_user() {
        return requesting_user;
    }

    public String getBook_owner() {
        return book_owner;
    }

    public String getBook_sort() {
        return book_sort;
    }

    public String getName_ather() {
        return name_ather;
    }

    public String getUrlofpic() {
        return urlofpic;
    }


    public BorrowModel(String requesting_user, String book_owner, String book_sort, String name_ather, String urlofpic,String id) {
        this.requesting_user = requesting_user;
        this.book_owner = book_owner;
        this.book_sort = book_sort;
        this.name_ather = name_ather;
        this.urlofpic = urlofpic;
        this.id =id;
    }

    // that is the default constructor which is neccessary to be made in order to display the stuff in the recycler view. .

    public BorrowModel(){
    }
}
