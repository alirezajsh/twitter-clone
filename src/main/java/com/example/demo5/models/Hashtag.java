package com.example.demo5.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Hashtag  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private int hashtagId;
    private ArrayList<Integer> posts;
    public Hashtag(String title, int firstPost) {
        this.title = title;
        this.posts = new ArrayList<>();
        posts.add(firstPost);
    }

    public Hashtag(String title){
        this.title = title;
        this.posts = new ArrayList<>();
    }
    public void addPost(int postId){
        posts.add(postId);
    }

    public int getHashtagPostNumber(){
        return this.posts.size();
    }

    public int getHashtagPosts(int i){
        return this.posts.get(i);
    }

    public String getHashtagTitle(){
        return this.title;
    }

    public String getName(){
        return this.title;
    }

    public int getCount(){return this.posts.size();}

    public int getHashtagId(){return this.hashtagId;}

    public void setHashtagId(int id){this.hashtagId = id;}


}
