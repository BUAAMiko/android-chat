package com.mali.mali;

public class MsgConBean {
    private String data;
    private String time,name;
    private int number;

    public MsgConBean() {
    }

    public MsgConBean(String data, int number,String time,String name) {
        this.data = data;
        this.number = number;
        this.name = name;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}