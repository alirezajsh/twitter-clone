package com.example.demo5.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Post implements Serializable ,Iterable<Integer>, Comparable<Post>{
    private static final long serialVersionUID = 1L;
    protected int postId;
    protected String postText;
    protected File file;
    protected int writterId;
    protected Date madeDate;
    protected ArrayList<Integer> hashtags;
    protected ArrayList<Integer> likes;
    protected int fathersPostId;
    protected ArrayList<Integer> answerPosts;
    protected int sawNumber;
    protected int likedNumber;
    protected boolean isBlocked;
    protected boolean isDeleted;
    protected int commentNumber;
    protected ArrayList<String> comments;
    protected int retweetNumber;

    public Post(int fathersPostId, boolean isBlocked, ArrayList<String> comments, int retweetNumber, int commentNumber, boolean isDeleted, ArrayList<Integer> answerPosts, int sawNumber, int likedNumber, int postId, String postText, File file, int writterId, Date madeDate, ArrayList<Integer> hashtags, ArrayList<Integer> likes){
        this.fathersPostId = fathersPostId;
        this.isBlocked = isBlocked;
        this.comments = comments;
        this.retweetNumber = retweetNumber;
        this.commentNumber = commentNumber;
        this.isDeleted = isDeleted;
        this.answerPosts = answerPosts;
        this.sawNumber = sawNumber;
        this.likedNumber = likedNumber;
        this.postId = postId;
        this.postText = postText;
        this.file = file;
        this.writterId = writterId;
        this.madeDate = madeDate;
        this.hashtags = hashtags;
        this.likes = likes;

    }

    public Post(String postText, int writterId, ArrayList<Integer> hashtags, File file, int postId){
        this.postText = postText;
        this.writterId = writterId;
        this.hashtags = hashtags;
        this.fathersPostId = fathersPostId;
        this.file = file;
        this.madeDate = new Date();
        this.likes = new ArrayList<>();
        this.answerPosts = new ArrayList<>();
        this.sawNumber = 0;
        this.likedNumber = 0;
        this.isBlocked = false;
        this.isDeleted = false;
        this.postId = postId;
        this.comments = new ArrayList<>();
        this.retweetNumber = 0;
    }

    public Post(String postText, int writterId, ArrayList<Integer> hashtags, int postId){
        this.postText = postText;
        this.writterId = writterId;
        this.hashtags = hashtags;
        this.madeDate = new Date();
        this.likes = new ArrayList<>();
        this.answerPosts = new ArrayList<>();
        this.sawNumber = 0;
        this.likedNumber = 0;
        this.isBlocked = false;
        this.isDeleted = false;
        this.postId = postId;
        this.comments = new ArrayList<>();
        this.retweetNumber = 0;
    }

    public Post(){

    }

    public Post(String postText, int writterId, ArrayList<Integer> hashtags, int postId, int fathersPostId){
        this.postText = postText;
        this.writterId = writterId;
        this.hashtags = hashtags;
        this.madeDate = new Date();
        this.likes = new ArrayList<>();
        this.answerPosts = new ArrayList<>();
        this.sawNumber = 0;
        this.likedNumber = 0;
        this.isBlocked = false;
        this.isDeleted = false;
        this.postId = postId;
        this.comments = new ArrayList<>();
        this.retweetNumber = 0;
        this.fathersPostId = fathersPostId;
    }

    public int getPostId(){
        return this.postId;
    }

    public void likePost(int userId){
        this.likedNumber++;
        likes.add(userId);
    }

    public File getPostFile(){
        return this.file;
    }
    public void seePost(){
        this.sawNumber++;
    }
    public void unlikedPost(int userId){
        this.likes.remove(userId);
        this.likedNumber--;
    }

    public int getId(){
        return this.postId;
    }
    public int getWritterId(){
        return this.writterId;
    }
    public void block(){
        this.isBlocked = true;
    }

    public void unblock(){
        this.isBlocked = false;
    }

    public String getPostText(){
        return this.postText;
    }
    public int getLikesNumber(){
        return this.likedNumber;
    }
    public int getCommentsNumber(){
        return this.commentNumber;
    }
    public int getRetweetCount(){
        return this.retweetNumber;
    }

    public Date getMadeDate(){
        return this.madeDate;
    }

    public void like(int userId){
        this.likedNumber++;
        this.likes.add(userId);
    }

    public void unlike(int userId){
        this.likes.remove(userId);
        this.likedNumber--;
    }

    public int getLikedUserById(int id){
        return this.likes.get(id);
    }

    public void increaseRetweet(int userId){
        this.retweetNumber++;
        this.answerPosts.add(userId);
    }

    public int getSawNumber(){
        return this.sawNumber;
    }

    public void increaseSawNumber(){
        this.sawNumber++;
    }

    public void addAnswerPost(int postId){
        this.answerPosts.add(postId);
    }


    public int getReplyByIndex(int i){
        return this.answerPosts.get(i);
    }

    public int getFatherPostId(){
        return this.fathersPostId;
    }

    /*public int getFullName(){
        return this.writterId;
    }

    public String getUserName(){
        return DataBase.getUserByIndex(this.writterId).getUserName();
    }

    public String getSymbol(){
        return DataBase.getUserByIndex(this.writterId).getSymbolImage();
    }*/

    public boolean isBlocked(){return this.isBlocked;}
    public boolean isDeleted(){return this.isDeleted;}
    public ArrayList<Integer> getHashtags(){return this.hashtags;}

    public void setFile(File file) {this.file = file;}
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public ArrayList<Integer> getLikes(){return this.likes;}

    public void setPostText(String text){this.postText = text;}

    public  void setWritterId(int id){this.writterId = id;}

    public void setMadeDate(Date date){this.madeDate = date;}

    public void setHashtags(ArrayList<Integer> hashtags){this.hashtags = hashtags;}

    public void setFatherPostId(int id){this.fathersPostId = id;}

    @Override
    public Iterator<Integer> iterator() {

        return this.hashtags.iterator();
    }

    @Override
    public int compareTo(Post other) {

        int textCompare = this.postText.compareTo(other.postText);
        if (textCompare != 0) return textCompare;

        return Integer.compare(this.hashtags.size(), other.hashtags.size());
    }

}
