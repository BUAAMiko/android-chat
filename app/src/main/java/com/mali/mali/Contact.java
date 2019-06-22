package com.mali.mali;

import java.io.Serializable;

public class Contact implements Serializable {
    private String id;
    private String name;
    private String phoneNum;
    private String Email;

    public Contact(){}

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }
    public String getPhoneNum(){
        return phoneNum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum=phoneNum;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

