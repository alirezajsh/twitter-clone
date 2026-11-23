package com.example.demo5.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public abstract class User extends Account  implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Integer> choseHashtags;
    private int chooseHashtagsNumber;
    private static int userNumber = 0;
    protected int initialBalance;
    protected int tokenNumber;
    protected String biographi;
    protected ArrayList<Integer> posts;
    protected ArrayList<Integer> followers;
    protected ArrayList<Integer> following;
    protected ArrayList<Integer> likedList;
    protected String symbol;

    protected int likedNumber;
    protected File userBadge;
    protected boolean isBlocked;
    public User() {
        super();
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.likedList = new ArrayList<>();
        this.likedNumber = 0;
        this.initialBalance = 0;
        this.chooseHashtagsNumber = 0;
        this.biographi = "";
        this.symbol = "";
    }

    public User(String fullName, String phoneNumber,  String userName, String email, String password) {
        super(fullName, phoneNumber, userName, email, password);
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.likedList = new ArrayList<>();

        this.likedNumber = 0;
        this.initialBalance = 0;

        this.chooseHashtagsNumber = 0;
    }
// به خاطر اینکه در اینجا با هر بار فراخوان کامستراکتور یه عدد به تعداد یوزر ها اضافه میشود مشکل به وجود می آید برای زمانی که بیسیک یوزر به موارد دیگ تبدیل میشود
    public User(String fullName, String phoneNumber,  String userName, String email, String password, int userId){
        super(fullName, phoneNumber, userName, email, password);
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.likedList = new ArrayList<>();

        this.likedNumber = 0;
        this.initialBalance = 0;
        this.userId = userId;
        this.chooseHashtagsNumber = 0;
    }

    public void setUserHashtags(ArrayList<Integer> hashtags){
        this.chooseHashtagsNumber = hashtags.size();
        this.choseHashtags = hashtags;
    }
    public void addPost(int id){
        this.posts.add(id);
    }

    public void likePost(int id){
        this.likedList.add(id);
    }

    public void addFollowers(int id){
        this.followers.add(id);
    }

    public void addFollowing(int id){
        this.following.add(id);
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setInitialBalance(int initialBalance){
        this.initialBalance = initialBalance;
    }

    public void setTokenNumber(int tokenNumber){
        this.tokenNumber = tokenNumber;
    }

    public void setBiographi(String biographi){
        this.biographi = biographi;
    }

    public void setUserBadge(File userBadge){
        this.userBadge = userBadge;
    }

    public void setIsBlocked(boolean isBlocked){
        this.isBlocked = isBlocked;
    }
    public boolean getIsBlocked(){
        return this.isBlocked;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUserName(){
        return this.userName;
    }

    public String  getPassword(){
        return this.password;
    }

    public int followerIdByIndex(int index){
        return this.followers.get(index);
    }

    public int followingIdByIndex(int index){
        return this.following.get(index);
    }

    public void removeFollowing(int index){
        this.following.remove(index);
    }

    public void removeFollower(int index){
        this.followers.remove(index);
    }

    public int getUserId(){
        return this.userId;
    }

    public int getFollowingNumber(){
        return this.following.size();
    }

    public int getFollowerNumber(){
        return this.followers.size();
    }

    public int getInitialBalance(){
        return this.initialBalance;
    }

    public String getFullName(){
        return this.fullName;
    }

    public File getUserBadge() {
        return userBadge;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public String getBiographi() {
        return biographi;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public Date getBirthDate(){
        return this.birthDate;
    }

    public Date getMadeDate(){
        return this.madeDate;
    }

    public ArrayList<Integer> getPosts(){
        return this.posts;
    }

    public ArrayList<Integer> getFollowers(){
        return this.followers;
    }

    public ArrayList<Integer> getFollowing(){
        return this.following;
    }

    public ArrayList<Integer> getLikedList(){
        return this.likedList;
    }

    public String getSymbolImage(){

        return this.symbol;
    }

    public int getPostNumber(){
        return this.posts.size();
    }
    public void increaseInitialBalance(int price){
        this.initialBalance += price;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getPost(int index){

        return this.posts.get(index);
    }

    public int getChooseHashtags(int i) {
        return this.choseHashtags.get(i);
    }

    public int getChooseHashtagsNumber(){
        System.out.println(this.chooseHashtagsNumber);
        return this.chooseHashtagsNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isBlocked(){return this.isBlocked;}

    public void setBlocked(boolean bool){this.isBlocked = bool;}

    public void setMadeDate(Date madeDate){
        this.madeDate = madeDate;
    }


    public void setPosts(ArrayList<Integer> posts) {
        this.posts = posts;
    }

    public void setFollowers(ArrayList<Integer> followers) {
        this.followers = followers;
    }

    public void setFollowing(ArrayList<Integer> following) {
        this.following = following;
    }

    public void setLikedList(ArrayList<Integer> likedList) {
        this.likedList = likedList;
    }
}
