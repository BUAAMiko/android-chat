package com.mali.mali;

import java.io.Serializable;

public class Chat implements Serializable {
    private String id;
    private String name;
    private  String LastMsg;

    public Chat(){}

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public  String getLastMsg(){return LastMsg;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastMsg(String lastMsg) {
        LastMsg = lastMsg;
    }
}

