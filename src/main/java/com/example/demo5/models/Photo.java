package com.example.demo5.models;

import java.io.Serializable;

public class Photo extends File  implements Serializable {
    private static final long serialVersionUID = 1L;
    private PhotoFormat photoFormat;
    public Photo(PhotoFormat photoFormat, String photoAddress){
        super(photoAddress);
        this.photoFormat = photoFormat;
    }
    public PhotoFormat getPhotoFormat() {
        return photoFormat;
    }
    public void setPhotoFormat(PhotoFormat newPhotoFormat){
        this.photoFormat = newPhotoFormat;
    }
}
