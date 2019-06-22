package com.mali.mali;

import java.io.Serializable;

public class File implements Serializable {
    private String id;
    private String name;

    public File(){}

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

