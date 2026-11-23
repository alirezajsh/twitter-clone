package com.example.demo5.models;

import java.io.Serializable;

public abstract class File  implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String fileAddress;
    public File(String fileAddress){
        this.fileAddress = fileAddress;
    }
    public String getFileAddress(){
        return this.fileAddress;
    }
}
