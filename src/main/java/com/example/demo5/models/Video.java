package com.example.demo5.models;

import java.io.Serializable;

public class Video extends File  implements Serializable {
    private static final long serialVersionUID = 1L;
    private double time;
    private VideoFormat videoFormat;
    private Keifiat keifiat;

    public Video(VideoFormat videoFormat, String videoAddress) {
        super(videoAddress);
        this.videoFormat = videoFormat;

    }

    public VideoFormat getVideoFormat() { return videoFormat; }
    public double getTime() { return time; }
    public Keifiat getKeifiat() { return keifiat; }
}
