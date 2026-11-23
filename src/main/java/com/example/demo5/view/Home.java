package com.example.demo5.view;

import com.example.demo5.models.Post;
import com.example.demo5.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo5.controller.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Home implements Initializable {

    public static final String LIKE = "❤️";
    public static final String COMMENT = "💬";
    public static final String RETWEET = "🔁";
    public static final String VIEW = "👁️";
    @FXML
    private ScrollPane chat;
    @FXML
    private TextField messageBox;
    @FXML private Text message;
    @FXML private Text message1;
    @FXML private Text message2;
    @FXML private Text message3;
    @FXML private Text message4;
    @FXML private Text message5;
    @FXML private Text message6;
    @FXML private Text message7;
    @FXML private Text message8;
    @FXML private Text message9;
    @FXML private Text message10;

    @FXML
    private AnchorPane homePost;
    @FXML
    private Label homePostFullName;
    @FXML
    private Label homePostUserName;
    @FXML
    private MediaView homePostMedia;
    @FXML
    private Text homePostContent;


    @FXML
    private AnchorPane homePost1;
    @FXML
    private Label homePostFullName1;
    @FXML
    private Label homePostUserName1;
    @FXML
    private MediaView homePostMedia1;
    @FXML
    private Text homePostContent1;
    @FXML
    private AnchorPane homePost11;
    @FXML
    private Label homePostFullName11;
    @FXML
    private Label homePostUserName11;
    @FXML
    private MediaView homePostMedia11;
    @FXML
    private Text homePostContent11;
    @FXML
    private AnchorPane homePost111;
    @FXML
    private Label homePostFullName111;
    @FXML
    private Label homePostUserName111;
    @FXML
    private MediaView homePostMedia111;
    @FXML
    private Text homePostContent111;
    @FXML
    private AnchorPane homePost1111;
    @FXML
    private Label homePostFullName1111;
    @FXML
    private Label homePostUserName1111;
    @FXML
    private MediaView homePostMedia1111;
    @FXML
    private Text homePostContent1111;
    @FXML
    private AnchorPane homePost11111;
    @FXML
    private Label homePostFullName11111;
    @FXML
    private Label homePostUserName11111;
    @FXML
    private MediaView homePostMedia11111;
    @FXML
    private Text homePostContent11111;
    @FXML
    private AnchorPane homePost111111;
    @FXML
    private Label homePostFullName111111;
    @FXML
    private Label homePostUserName111111;
    @FXML
    private MediaView homePostMedia111111;
    @FXML
    private Text homePostContent111111;
    @FXML
    private AnchorPane homePost1111111;
    @FXML
    private Label homePostFullName1111111;
    @FXML
    private Label homePostUserName1111111;
    @FXML
    private MediaView homePostMedia1111111;
    @FXML
    private Text homePostContent1111111;
    @FXML
    private AnchorPane homePost11111111;
    @FXML
    private Label homePostFullName11111111;
    @FXML
    private Label homePostUserName11111111;
    @FXML
    private MediaView homePostMedia11111111;
    @FXML
    private Text homePostContent11111111;
    @FXML
    private AnchorPane homePost111111111;
    @FXML
    private Label homePostFullName111111111;
    @FXML
    private Label homePostUserName111111111;
    @FXML
    private MediaView homePostMedia111111111;
    @FXML
    private Text homePostContent111111111;

    @FXML
    private AnchorPane reply1;
    @FXML
    private AnchorPane reply11;
    @FXML
    private AnchorPane reply111;
    @FXML
    private AnchorPane reply1111;
    @FXML
    private AnchorPane reply11111;
    @FXML
    private AnchorPane reply111111;
    @FXML
    private AnchorPane reply1111111;
    @FXML
    private AnchorPane reply11111111;
    @FXML
    private AnchorPane reply111111111;
    @FXML
    private AnchorPane reply1111111111;
    @FXML
    private AnchorPane reply11111111111;
    @FXML
    private Text openPostFullName;

    @FXML
    private Text openPostUserName;

    @FXML
    private Text openPostUserSymbol;
    @FXML
    private Text openPostContent;

    @FXML
    private Label openPostLikeNumber;
    @FXML
    private Label openPostCommentNumber;

    @FXML
    private Label openPostRetweetNumber;

    @FXML
    private Label openPostSawNumber;

    @FXML
    TextField replyHashtags;
    @FXML
    TextField replyText;
    @FXML
    private Text replyUserSymbol1;

    @FXML
    private Text replyUserSymbol11;

    @FXML
    private Text replyUserSymbol111;
    @FXML
    private Text replyUserSymbol1111;
    @FXML
    private Text replyUserSymbol11111;
    @FXML
    private Text replyUserSymbol111111;
    @FXML
    private Text replyUserSymbol1111111;
    @FXML
    private Text replyUserSymbol11111111;
    @FXML
    private Text replyUserSymbol111111111;

    @FXML
    private Text replyUserSymbol1111111111;
    @FXML
    private Text replyUserSymbol11111111111;


    @FXML
    private ScrollPane postView;

    @FXML
    private Text replyFullName1;
    @FXML
    private Text replyUserName1;
    @FXML
    private Text replyContent1;
    @FXML
    private Text replyFullName11;
    @FXML
    private Text replyUserName11;
    @FXML
    private Text replyContent11;
    @FXML
    private Text replyFullName111;
    @FXML
    private Text replyUserName111;
    @FXML
    private Text replyContent111;
    @FXML
    private Text replyFullName1111;
    @FXML
    private Text replyUserName1111;
    @FXML
    private Text replyContent1111;
    @FXML
    private Text replyFullName11111;
    @FXML
    private Text replyUserName11111;
    @FXML
    private Text replyContent11111;
    @FXML
    private Text replyFullName111111;
    @FXML
    private Text replyUserName111111;
    @FXML
    private Text replyContent111111;
    @FXML
    private Text replyFullName1111111;
    @FXML
    private Text replyUserName1111111;
    @FXML
    private Text replyContent1111111;
    @FXML
    private Text replyFullName11111111;
    @FXML
    private Text replyUserName11111111;
    @FXML
    private Text replyContent11111111;
    @FXML
    private Text replyFullName111111111;
    @FXML
    private Text replyUserName111111111;
    @FXML
    private Text replyContent111111111;
    @FXML
    private Text replyFullName1111111111;
    @FXML
    private Text replyUserName1111111111;
    @FXML
    private Text replyContent1111111111;
    @FXML
    private Text replyFullName11111111111;
    @FXML
    private Text replyUserName11111111111;
    @FXML
    private Text replyContent11111111111;

    @FXML
    private Label hashtagErrorMessage;
    @FXML
    private VBox otherUserPost1;

    @FXML
    private Text otherPostFullName1;

    @FXML
    private Text otherPostUserName1;

    @FXML
    private Text otherPostContent1;

    @FXML
    private Text otherPostUserSymbol1;

    @FXML
    private Text otherPostMadeDate1;

    @FXML
    private Text otherPostLikeNumber1;

    @FXML
    private Text otherPostRetweetNumber1;

    @FXML
    private Text otherPostCommentNumber1;

    @FXML
    private Text otherPostSawNumber1;

    @FXML
    private MediaView otherPostMedia1;

    @FXML
    private VBox otherUserPost11;

    @FXML
    private Text otherPostFullName11;

    @FXML
    private Text otherPostUserName11;

    @FXML
    private Text otherPostContent11;

    @FXML
    private Text otherPostUserSymbol11;

    @FXML
    private Text otherPostMadeDate11;

    @FXML
    private Text otherPostLikeNumber11;

    @FXML
    private Text otherPostRetweetNumber11;

    @FXML
    private Text otherPostCommentNumber11;

    @FXML
    private Text otherPostSawNumber11;

    @FXML
    private MediaView otherPostMedia11;


    @FXML
    private VBox otherUserPost111;

    @FXML
    private Text otherPostFullName111;

    @FXML
    private Text otherPostUserName111;

    @FXML
    private Text otherPostContent111;

    @FXML
    private Text otherPostUserSymbol111;

    @FXML
    private Text otherPostMadeDate111;

    @FXML
    private Text otherPostLikeNumber111;

    @FXML
    private Text otherPostRetweetNumber111;

    @FXML
    private Text otherPostCommentNumber111;

    @FXML
    private Text otherPostSawNumber111;

    @FXML
    private MediaView otherPostMedia111;

    @FXML
    private VBox otherUserPost1111;

    @FXML
    private Text otherPostFullName1111;

    @FXML
    private Text otherPostUserName1111;

    @FXML
    private Text otherPostContent1111;

    @FXML
    private Text otherPostUserSymbol1111;

    @FXML
    private Text otherPostMadeDate1111;

    @FXML
    private Text otherPostLikeNumber1111;

    @FXML
    private Text otherPostRetweetNumber1111;

    @FXML
    private Text otherPostCommentNumber1111;

    @FXML
    private Text otherPostSawNumber1111;

    @FXML
    private MediaView otherPostMedia1111;

    @FXML
    private VBox userPost1;

    @FXML
    private VBox userPost2;

    @FXML
    private VBox userPost3;

    @FXML
    private VBox userPost4;



    @FXML
    private Text postFullName1;
    @FXML
    Text postUserName1;

    @FXML
    private Text postUserSymbol1;

    @FXML
    private Text postSawNumber1;
    @FXML
    private Text postCommentNumber1;

    @FXML
    private Text postRetweetNumber1;

    @FXML
    private Text postLikeNumber1;

    @FXML
    private Text postMadeDate1;

    @FXML
    private Text postContent1;


    @FXML
    private Text postFullName111;
    @FXML
    Text postUserName111;

    @FXML
    private Text postUserSymbol111;

    @FXML
    private Text postSawNumber111;
    @FXML
    private Text postCommentNumber111;

    @FXML
    private Text postRetweetNumber111;

    @FXML
    private Text postLikeNumber111;

    @FXML
    private Text postMadeDate111;

    @FXML
    private Text postContent111;


    @FXML
    private Text postFullName1111;
    @FXML
    Text postUserName1111;

    @FXML
    private Text postUserSymbol1111;

    @FXML
    private Text postSawNumber1111;
    @FXML
    private Text postCommentNumber1111;

    @FXML
    private Text postRetweetNumber1111;

    @FXML
    private Text postLikeNumber1111;

    @FXML
    private Text postMadeDate1111;

    @FXML
    private Text postContent1111;

    @FXML
    private Text postFullName11;
    @FXML
    Text postUserName11;

    @FXML
    private Text postUserSymbol11;

    @FXML
    private Text postSawNumber11;
    @FXML
    private Text postCommentNumber11;

    @FXML
    private Text postRetweetNumber11;

    @FXML
    private Text postLikeNumber11;

    @FXML
    private Text postMadeDate11;

    @FXML
    private Text postContent11;

    @FXML
    private ListView<Post> userPostList;
    @FXML
    private Text postErrorText;
    @FXML
    private TextField fullNameEdit;

    @FXML
    private TextField restockField;

    @FXML
    private TextField userNameEdit;

    @FXML
    private ScrollPane restockPane;

    @FXML
    private TextField phoneNumberEdit;

    @FXML
    private ScrollPane editPage;

    @FXML
    private TextField emailEdit;

    @FXML
    private TextField passwordEdit;

    @FXML
    private TextField confirmPasswordEdit;

    @FXML
    private DatePicker birthDateEdit;

    @FXML
    private TextArea biographiEdit;

    @FXML
    private Button followButton;

    @FXML
    private Label fullName1;

    @FXML
    private Label userName1;

    @FXML
    private Text biographi1;

    @FXML
    private Text symbol1;

    @FXML
    private Text symbol;
    @FXML
    private Label followers1;

    @FXML
    private Label followings1;


    @FXML
    private Text followersNumber;

    @FXML
    private Text followingNumber;

    @FXML
    private Label fullName;

    @FXML
    private ScrollPane home;

    @FXML
    private Label madeDate1;

    @FXML
    private Text madeDate;

    @FXML
    private ScrollPane newnew;

    @FXML
    private ListView<?> homeListView;
    @FXML
    private Text errorText;

    @FXML
    private ScrollPane profile;

    @FXML
    private ScrollPane search;

    @FXML
    private ChoiceBox<String> searchType;

    @FXML
    private Label userName;

    @FXML
    private ScrollPane userPage;

    @FXML
    private ListView<String> searchList;

    @FXML
    private TextField hashtags;

    @FXML
    private TextField photoAddress;

    @FXML
    private TextField videoAddress;

    @FXML
    private TextArea postContent;

    @FXML
    private Button sendButton;


    @FXML
    private TextField searchField;

    @FXML
    private Text biographi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UserController.fillAllUsername();


        searchField.textProperty().addListener((obs, oldText, newText) -> {
            search(newText);
        });


        searchList.setOnMouseClicked(event -> {
            String selectedUsername = searchList.getSelectionModel().getSelectedItem();
            if (selectedUsername != null) {
                User selectedUser = UserController.findUserByUsername(selectedUsername);
                UserController.setCurrentUser2(selectedUser);

                showUserPage(selectedUser);

            }
        });

        search("");
    }

    private void search(String searchText) {
        searchList.getItems().clear();

        if (searchText == null || searchText.isEmpty()) {
            for (int i = 0; i < Math.min(50, UserController.allUsername.size()); i++) {
                searchList.getItems().add(UserController.allUsername.get(i));
            }
        } else {
            int count = 0;
            for (String name : UserController.allUsername) {
                if (name.toLowerCase().contains(searchText.toLowerCase()) && count < 30) {
                    searchList.getItems().add(name);
                    count++;
                }
            }
        }
    }

    private void showUserPage(User user) {
        if (user.getSymbolImage() != null) {
            symbol1.setText(user.getSymbolImage());

        }
        fullName1.setText(UserController.getCurrentUser2().getFullName());
        userName1.setText(UserController.getCurrentUser2().getUserName());
        madeDate1.setText(UserController.getCurrentUser2().getMadeDate().toString());
        biographi1.setText(UserController.getCurrentUser2().getBiographi());
        followers1.setText("followers" + UserController.getCurrentUser2().getFollowerNumber());
        followings1.setText("following" + UserController.getCurrentUser2().getFollowingNumber());

        System.out.println(UserController.areYouFollowHim());
        if(UserController.areYouFollowHim()) followButton.setText("unfollow");
        else followButton.setText("follow");

            otherUserPost1.setVisible(false);
            otherUserPost11.setVisible(false);
            otherUserPost111.setVisible(false);
            otherUserPost1111.setVisible(false);

        if(UserController.getCurrentUser2PostNumber() > 0) {
            Post post = UserController.getCurrentUser2PostById(0);
            otherUserPost1.setUserData(post);

            otherUserPost1.setVisible(true);
            otherPostContent1.setText(UserController.getPostText2(0));
            otherPostFullName1.setText(UserController.getCurrentUser2FullName());
            otherPostUserName1.setText(UserController.getCurrentUser2Name());
            otherPostUserSymbol1.setText(UserController.getCurrentUser2Symbol());
            otherPostSawNumber1.setText("\uD83D\uDC41️" + UserController.getCurrentUser2PostSawNumberById(0));
            otherPostCommentNumber1.setText("\uD83D\uDCAC" + UserController.getCurrentUser2PostCommentNumberById(0));
            otherPostRetweetNumber1.setText("\uD83D\uDD04" + UserController.getCurrentUser2PostRetweetNumberById(0));
            otherPostLikeNumber1.setText("❤️" + UserController.getCurrentUser2PostLikeNumberById(0));
            otherPostMadeDate1.setText(UserController.getCurrentUser2PostMadeDateById(0));
            otherPostContent1.setText(UserController.getCurrentUser2PostContentById(0));
        }


        if(UserController.getCurrentUser2PostNumber() > 1) {

            Post post = UserController.getCurrentUser2PostById(1);
            otherUserPost1.setUserData(post);
            otherUserPost11.setVisible(true);
            otherPostContent11.setText(UserController.getPostText2(1));
            otherPostFullName11.setText(UserController.getCurrentUser2FullName());
            otherPostUserName11.setText(UserController.getCurrentUser2Name());
            otherPostUserSymbol11.setText(UserController.getCurrentUser2Symbol());
            otherPostSawNumber11.setText("\uD83D\uDC41️" + UserController.getCurrentUser2PostSawNumberById(1));
            otherPostCommentNumber11.setText("\uD83D\uDCAC" + UserController.getCurrentUser2PostCommentNumberById(1));
            otherPostRetweetNumber11.setText("\uD83D\uDD04" + UserController.getCurrentUser2PostRetweetNumberById(1));
            otherPostLikeNumber11.setText("❤️" + UserController.getCurrentUser2PostLikeNumberById(1));
            otherPostMadeDate11.setText(UserController.getCurrentUser2PostMadeDateById(1));
            otherPostContent11.setText(UserController.getCurrentUser2PostContentById(1));
        }

        if(UserController.getCurrentUser2PostNumber() > 2) {

            Post post = UserController.getCurrentUser2PostById(0);
            otherUserPost1.setUserData(post);


            otherUserPost111.setVisible(true);
            otherPostContent111.setText(UserController.getPostText2(2));
            otherPostFullName111.setText(UserController.getCurrentUser2FullName());
            otherPostUserName111.setText(UserController.getCurrentUser2Name());
            otherPostUserSymbol111.setText(UserController.getCurrentUser2Symbol());
            otherPostSawNumber111.setText("\uD83D\uDC41️" + UserController.getCurrentUser2PostSawNumberById(2));
            otherPostCommentNumber111.setText("\uD83D\uDCAC" + UserController.getCurrentUser2PostCommentNumberById(2));
            otherPostRetweetNumber111.setText("\uD83D\uDD04" + UserController.getCurrentUser2PostRetweetNumberById(2));
            otherPostLikeNumber111.setText("❤️" + UserController.getCurrentUser2PostLikeNumberById(2));
            otherPostMadeDate111.setText(UserController.getCurrentUser2PostMadeDateById(2));
            otherPostContent111.setText(UserController.getCurrentUser2PostContentById(2));
        }

        if(UserController.getCurrentUser2PostNumber() > 3) {
            Post post = UserController.getCurrentUser2PostById(0);
            otherUserPost1.setUserData(post);


            otherUserPost1111.setVisible(true);
            otherPostContent1111.setText(UserController.getPostText2(3));
            otherPostFullName1111.setText(UserController.getCurrentUser2FullName());
            otherPostUserName1111.setText(UserController.getCurrentUser2Name());
            otherPostUserSymbol1111.setText(UserController.getCurrentUser2Symbol());
            otherPostSawNumber1111.setText("\uD83D\uDC41️" + UserController.getCurrentUser2PostSawNumberById(3));
            otherPostCommentNumber1111.setText("\uD83D\uDCAC" + UserController.getCurrentUser2PostCommentNumberById(3));
            otherPostRetweetNumber1111.setText("\uD83D\uDD04" + UserController.getCurrentUser2PostRetweetNumberById(3));
            otherPostLikeNumber1111.setText("❤️" + UserController.getCurrentUser2PostLikeNumberById(3));
            otherPostMadeDate1111.setText(UserController.getCurrentUser2PostMadeDateById(3));
            otherPostContent1111.setText(UserController.getCurrentUser2PostContentById(3));
        }

        userPage.setVisible(true);
        userPage.toFront();
    }

    @FXML
    void goToHome(ActionEvent event) {
        UserController.refreshHomePost();
        homePost.setVisible(true);
        homePost1.setVisible(true);
        homePost11.setVisible(true);
        homePost111.setVisible(true);
        homePost1111.setVisible(true);
        homePost11111.setVisible(true);
        homePost111111.setVisible(true);
        homePost1111111.setVisible(true);
        homePost11111111.setVisible(true);
        homePost111111111.setVisible(true);
        if(UserController.getHomePostNumber() > 7){
            homePost111111111.setVisible(false);
        }else if(UserController.getHomePostNumber() > 6){
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);

        }else if(UserController.getHomePostNumber() > 5) {
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);

        }else if(UserController.getHomePostNumber() > 4){
            homePost111111.setVisible(false);
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);
        }else if(UserController.getHomePostNumber() > 3){
            homePost11111.setVisible(false);
            homePost111111.setVisible(false);
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);

        }else if(UserController.getHomePostNumber() > 2){
            homePost1111.setVisible(false);
            homePost11111.setVisible(false);
            homePost111111.setVisible(false);
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);
        }else if(UserController.getHomePostNumber() > 1){
            homePost11.setVisible(false);
            homePost111.setVisible(false);
            homePost1111.setVisible(false);
            homePost11111.setVisible(false);
            homePost111111.setVisible(false);
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);
        }else if(UserController.getHomePostNumber() > 0){
            homePost1.setVisible(false);
            homePost11.setVisible(false);
            homePost111.setVisible(false);
            homePost1111.setVisible(false);
            homePost11111.setVisible(false);
            homePost111111.setVisible(false);
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);
        }else if(UserController.getHomePostNumber() > -1){
            homePost.setVisible(false);
            homePost1.setVisible(false);
            homePost11.setVisible(false);
            homePost111.setVisible(false);
            homePost1111.setVisible(false);
            homePost11111.setVisible(false);
            homePost111111.setVisible(false);
            homePost1111111.setVisible(false);
            homePost11111111.setVisible(false);
            homePost111111111.setVisible(false);
        }



        if(UserController.getHomePostNumber() > 0) {
            homePostContent.setText(UserController.getHomePostContent(0));
            homePostFullName.setText(UserController.getHomePostWritter(0));
            homePostUserName.setText(UserController.getHomePostContent(0));
        }
        if(UserController.getHomePostNumber() > 1) {
            homePostContent1.setText(UserController.getHomePostContent(1));
            homePostFullName1.setText(UserController.getHomePostWritter(1));
            homePostUserName1.setText(UserController.getHomePostContent(1));
        }
        if(UserController.getHomePostNumber() > 2) {
            homePostContent11.setText(UserController.getHomePostContent(2));
            homePostFullName11.setText(UserController.getHomePostWritter(2));
            homePostUserName11.setText(UserController.getHomePostContent(2));
        }
        if(UserController.getHomePostNumber() > 3) {
            homePostContent111.setText(UserController.getHomePostContent(3));
            homePostFullName111.setText(UserController.getHomePostWritter(3));
            homePostUserName111.setText(UserController.getHomePostContent(3));
        }
        if(UserController.getHomePostNumber() > 4) {
            homePostContent1111.setText(UserController.getHomePostContent(4));
            homePostFullName1111.setText(UserController.getHomePostWritter(4));
            homePostUserName1111.setText(UserController.getHomePostContent(4));
        }
        if(UserController.getHomePostNumber() > 5) {
            homePostContent11111.setText(UserController.getHomePostContent(5));
            homePostFullName11111.setText(UserController.getHomePostWritter(5));
            homePostUserName11111.setText(UserController.getHomePostContent(5));
        }
        if(UserController.getHomePostNumber() > 6) {
            homePostContent111111.setText(UserController.getHomePostContent(6));
            homePostFullName111111.setText(UserController.getHomePostWritter(6));
            homePostUserName111111.setText(UserController.getHomePostContent(6));
        }
        if(UserController.getHomePostNumber() > 7) {
            homePostContent1111111.setText(UserController.getHomePostContent(7));
            homePostFullName1111111.setText(UserController.getHomePostWritter(7));
            homePostUserName1111111.setText(UserController.getHomePostContent(7));
        }
        if(UserController.getHomePostNumber() > 8) {
            homePostContent11111111.setText(UserController.getHomePostContent(8));
            homePostFullName11111111.setText(UserController.getHomePostWritter(8));
            homePostUserName11111111.setText(UserController.getHomePostContent(8));
        }
        if(UserController.getHomePostNumber() > 9) {
            homePostContent111111111.setText(UserController.getHomePostContent(9));
            homePostFullName111111111.setText(UserController.getHomePostWritter(9));
            homePostUserName111111111.setText(UserController.getHomePostContent(9));
        }


        home.setVisible(true);
        home.toFront();


    }

    @FXML
    void goToNew(ActionEvent event) {

        newnew.setVisible(true);
        newnew.toFront();
    }

    @FXML
    void goToProfile(ActionEvent event) {
        symbol.setText(UserController.getCurrentUser().getSymbolImage());
        fullName.setText(UserController.getCurrentUser().getFullName());
        userName.setText(UserController.getCurrentUser().getUserName());
        madeDate.setText(UserController.getCurrentUser().getMadeDate().toString());
        biographi.setText(UserController.getCurrentUser().getBiographi());
        followersNumber.setText("followers" + UserController.getCurrentUser().getFollowerNumber());
        followingNumber.setText("following" + UserController.getCurrentUser().getFollowingNumber());
        System.out.println(UserController.getCurrentUser().getBiographi());
        errorText.setText("");


        if(UserController.getCurrentUserPostNumber() < 1){
            userPost1.setVisible(false);
            userPost2.setVisible(false);
            userPost3.setVisible(false);
            userPost4.setVisible(false);
        }else if(UserController.getCurrentUserPostNumber() < 2){
            userPost2.setVisible(false);
            userPost3.setVisible(false);
            userPost4.setVisible(false);
        }else if(UserController.getCurrentUserPostNumber() < 3){
            userPost3.setVisible(false);
            userPost4.setVisible(false);
        }else if(UserController.getCurrentUserPostNumber() < 4){
            userPost4.setVisible(false);
        }
        if(UserController.getCurrentUserPostNumber() > 0) {
            Post post = UserController.getCurrentUserPostById(0);
            otherUserPost1.setUserData(post);
            userPost1.setVisible(true);
            postContent1.setText(UserController.getPostText(0));
            postFullName1.setText(UserController.getCurrentUserFullName());
            postUserName1.setText(UserController.getCurrentUserName());
            postUserSymbol1.setText(UserController.getCurrentUserSymbol());
            postSawNumber1.setText("\uD83D\uDC41️" + UserController.getCurrentUserPostSawNumberById(0));
            postCommentNumber1.setText("\uD83D\uDCAC" + UserController.getCurrentUserPostCommentNumberById(0));
            postRetweetNumber1.setText("\uD83D\uDD04" + UserController.getCurrentUserPostRetweetNumberById(0));
            postLikeNumber1.setText("❤️" + UserController.getCurrentUserPostLikeNumberById(0));
            postMadeDate1.setText(UserController.getCurrentUserPostMadeDateById(0));
            postContent1.setText(UserController.getCurrentUserPostContentById(0));
        }


        if(UserController.getCurrentUserPostNumber() > 1) {
            Post post = UserController.getCurrentUserPostById(0);
            otherUserPost1.setUserData(post);
            userPost2.setVisible(true);
            postContent11.setText(UserController.getPostText(1));
            postFullName11.setText(UserController.getCurrentUserFullName());
            postUserName11.setText(UserController.getCurrentUserName());
            postUserSymbol11.setText(UserController.getCurrentUserSymbol());
            postSawNumber11.setText("\uD83D\uDC41️" + UserController.getCurrentUserPostSawNumberById(1));
            postCommentNumber11.setText("\uD83D\uDCAC" + UserController.getCurrentUserPostCommentNumberById(1));
            postRetweetNumber11.setText("\uD83D\uDD04" + UserController.getCurrentUserPostRetweetNumberById(1));
            postLikeNumber11.setText("❤️" + UserController.getCurrentUserPostLikeNumberById(1));
            postMadeDate11.setText(UserController.getCurrentUserPostMadeDateById(1));
            postContent11.setText(UserController.getCurrentUserPostContentById(1));
        }

        if(UserController.getCurrentUserPostNumber() > 2) {

            Post post = UserController.getCurrentUserPostById(0);
            otherUserPost1.setUserData(post);

            userPost3.setVisible(true);
            postContent111.setText(UserController.getPostText(2));
            postFullName111.setText(UserController.getCurrentUserFullName());
            postUserName111.setText(UserController.getCurrentUserName());
            postUserSymbol111.setText(UserController.getCurrentUserSymbol());
            postSawNumber111.setText("\uD83D\uDC41️" + UserController.getCurrentUserPostSawNumberById(2));
            postCommentNumber111.setText("\uD83D\uDCAC" + UserController.getCurrentUserPostCommentNumberById(2));
            postRetweetNumber111.setText("\uD83D\uDD04" + UserController.getCurrentUserPostRetweetNumberById(2));
            postLikeNumber111.setText("❤️" + UserController.getCurrentUserPostLikeNumberById(2));
            postMadeDate111.setText(UserController.getCurrentUserPostMadeDateById(2));
            postContent111.setText(UserController.getCurrentUserPostContentById(2));
        }

        if(UserController.getCurrentUserPostNumber() > 3) {

            Post post = UserController.getCurrentUserPostById(0);
            otherUserPost1.setUserData(post);


            userPost4.setVisible(true);
            postContent1111.setText(UserController.getPostText(3));
            postFullName1111.setText(UserController.getCurrentUserFullName());
            postUserName1111.setText(UserController.getCurrentUserName());
            postUserSymbol1111.setText(UserController.getCurrentUserSymbol());
            postSawNumber1111.setText("\uD83D\uDC41️" + UserController.getCurrentUserPostSawNumberById(3));
            postCommentNumber1111.setText("\uD83D\uDCAC" + UserController.getCurrentUserPostCommentNumberById(3));
            postRetweetNumber1111.setText("\uD83D\uDD04" + UserController.getCurrentUserPostRetweetNumberById(3));
            postLikeNumber1111.setText("❤️" + UserController.getCurrentUserPostLikeNumberById(3));
            postMadeDate1111.setText(UserController.getCurrentUserPostMadeDateById(3));
            postContent1111.setText(UserController.getCurrentUserPostContentById(3));
        }










        profile.setVisible(true);

        profile.toFront();
    }

    @FXML
    void goToSearch(ActionEvent event) {
        search.setVisible(true);
       search.toFront();
    }

    @FXML
    void buyBlue() {
        if(UserController.getCurrentUser().getInitialBalance() > 9) {
            UserController.buyBlueUser(UserController.getCurrentUser().getUserId(), UserController.getCurrentUser());
        }else {
            errorText.setText("you don't have enough money.");
            System.out.println(UserController.getCurrentUser().getInitialBalance());
        }
    }

    @FXML
    void buyGold() {
        if(UserController.getCurrentUser().getInitialBalance() > 19) {
            UserController.buyGoldUser(UserController.getCurrentUser().getUserId(), UserController.getCurrentUser());
        }else {
            errorText.setText("you don't have enough money.");
        }
    }

    @FXML
    void follow(ActionEvent event) {

        if(!UserController.areYouFollowHim()){

            UserController.followPerson(UserController.getCurrentUser().getUserId(), UserController.getCurrentUser2().getUserId());
            followButton.setText("unfollow");
        }else{
            UserController.unfollowPerson(UserController.getCurrentUser().getUserId(), UserController.getCurrentUser2().getUserId());
            followButton.setText("follow");
        }
    }

    @FXML
    void goToSignupPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo5/view/hello-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Signup/Login");
        stage.show();
    }

    @FXML
    void reportUser(ActionEvent event) {

    }

    @FXML
    void back(){
        if(newnew.isVisible()){
            newnew.setVisible(false);
        }else if(home.isVisible()){
            home.setVisible(false);
        }else if(userPage.isVisible()){
            userPage.setVisible(false);
        }else if(profile.isVisible()){
            profile.setVisible(false);
        }else if(search.isVisible()){
            search.setVisible(false);
        }
    }


    @FXML
    void save() throws ParseException {
        UserController.setCurrentUserFullName(fullNameEdit.getText());
        UserController.setCurrentUserEmail(emailEdit.getText());
        UserController.setCurrentUserName(userNameEdit.getText());
        UserController.setCurrentUserPassword(passwordEdit.getText(), confirmPasswordEdit.getText());
        LocalDate localDate = birthDateEdit.getValue();
        if (localDate != null) {

            String dateString = localDate.toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            UserController.setCurrentUserBirthDate(sdf.parse(dateString));
        }
        UserController.setCurrentUserBiographi(biographiEdit.getText());
    }



    @FXML
    void edit(){
        fullNameEdit.setPromptText("full name:" + UserController.getCurrentUser().getFullName());
        userNameEdit.setPromptText("user name:" + UserController.getCurrentUser().getUserName());
        emailEdit.setPromptText("email:" + UserController.getCurrentUser().getEmail());
        passwordEdit.setPromptText("password:" + UserController.getCurrentUser().getPassword());

        phoneNumberEdit.setPromptText("phone number:" + UserController.getCurrentUser().getPhoneNumber());
        biographiEdit.setText(UserController.getCurrentUser().getBiographi());
        editPage.setVisible(true);
        editPage.toFront();
    }

    @FXML
    void restock(){
        UserController.restockCurrentUser(Integer.parseInt(restockField.getText()));
        restockPane.setVisible(false);
        restockField.clear();
    }

    @FXML
    void goToRestock(){
        restockPane.setVisible(true);
        restockPane.toFront();
    }

    @FXML
    void send(){
        if(postContent.getText() != null && hashtags.getText() != null && !hashtags.getText().equals("") && !postContent.getText().equals("")) {
            UserController.addPost(postContent.getText(), photoAddress.getText(), videoAddress.getText(), hashtags.getText());
            postContent.setText("");
            hashtags.setText("");
            photoAddress.setText("");
            videoAddress.setText("");
        }else{
            postErrorText.setText("you must fill all neccessery field.");
        }


    }


    @FXML
    private void openPost(MouseEvent event) throws IOException {
        // مخفی کردن همه replyها
        reply1.setVisible(false);
        reply11.setVisible(false);
        reply111.setVisible(false);
        reply1111.setVisible(false);
        reply11111.setVisible(false);
        reply111111.setVisible(false);
        reply1111111.setVisible(false);
        reply11111111.setVisible(false);
        reply111111111.setVisible(false);
        reply1111111111.setVisible(false);
        reply11111111111.setVisible(false);

        VBox clickedVBox = (VBox) event.getSource();
        Post selectedPost = (Post) clickedVBox.getUserData();

        // اگر selectedPost null بود، از currentPost استفاده کن (برای زمانی که از reply کلیک شود)
        if (selectedPost == null && UserController.getCurrentPost() != null) {
            selectedPost = UserController.getCurrentPost();
        }
        if (selectedPost == null) return;

        UserController.setCurrentPost(selectedPost);
        UserController.setCurrentPost(selectedPost);
        UserController.openPost(selectedPost);

        // نمایش اطلاعات پست
        openPostCommentNumber.setText(COMMENT + UserController.getPostCommentNumber(selectedPost));
        openPostContent.setText(UserController.getPostContent(selectedPost));
        openPostFullName.setText(UserController.getPostFullName(selectedPost));
        openPostUserName.setText(UserController.getPostUserName(selectedPost));
        openPostUserSymbol.setText(UserController.getPostSymbol(selectedPost));
        openPostLikeNumber.setText(LIKE + UserController.getLikesNumber(selectedPost));
        openPostSawNumber.setText(VIEW + UserController.getSawNumber(selectedPost));
        openPostRetweetNumber.setText(RETWEET + UserController.getReplyPostNumber(selectedPost));

        // تنظیم کلیک روی openPostLikeNumber برای لایک
        openPostLikeNumber.setOnMouseClicked(e -> handleLikeOnLabel());
        // تغییر ظاهر نشانگر ماوس برای نشان دادن کلیک‌پذیری
        openPostLikeNumber.setStyle("-fx-cursor: hand;");

        boolean liked = UserController.isLikedByCurrentUser(selectedPost);
        openPostLikeNumber.setStyle(liked ? "-fx-text-fill: red; -fx-cursor: hand;" : "-fx-text-fill: black; -fx-cursor: hand;");
        // نمایش replyها
        int replyCount = UserController.getReplyPostNumber(selectedPost);
        if (replyCount > 0) {
            reply1.setVisible(true);
            replyContent1.setText(UserController.getReplyPostContent(0, selectedPost));
            replyFullName1.setText(UserController.getCurrentUserFullName());
            replyUserName1.setText(UserController.getCurrentUserName());
            replyUserSymbol1.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 1) {
            reply11.setVisible(true);
            replyContent11.setText(UserController.getReplyPostContent(1, selectedPost));
            replyFullName11.setText(UserController.getCurrentUserFullName());
            replyUserName11.setText(UserController.getCurrentUserName());
            replyUserSymbol11.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 2) {
            reply111.setVisible(true);
            replyContent111.setText(UserController.getReplyPostContent(2, selectedPost));
            replyFullName111.setText(UserController.getCurrentUserFullName());
            replyUserName111.setText(UserController.getCurrentUserName());
            replyUserSymbol111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 3) {
            reply1111.setVisible(true);
            replyContent1111.setText(UserController.getReplyPostContent(3, selectedPost));
            replyFullName1111.setText(UserController.getCurrentUserFullName());
            replyUserName1111.setText(UserController.getCurrentUserName());
            replyUserSymbol1111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 4) {
            reply11111.setVisible(true);
            replyContent11111.setText(UserController.getReplyPostContent(4, selectedPost));
            replyFullName11111.setText(UserController.getCurrentUserFullName());
            replyUserName11111.setText(UserController.getCurrentUserName());
            replyUserSymbol11111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 5) {
            reply111111.setVisible(true);
            replyContent111111.setText(UserController.getReplyPostContent(5, selectedPost));
            replyFullName111111.setText(UserController.getCurrentUserFullName());
            replyUserName111111.setText(UserController.getCurrentUserName());
            replyUserSymbol111111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 6) {
            reply1111111.setVisible(true);
            replyContent1111111.setText(UserController.getReplyPostContent(6, selectedPost));
            replyFullName1111111.setText(UserController.getCurrentUserFullName());
            replyUserName1111111.setText(UserController.getCurrentUserName());
            replyUserSymbol1111111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 7) {
            reply11111111.setVisible(true);
            replyContent11111111.setText(UserController.getReplyPostContent(7, selectedPost));
            replyFullName11111111.setText(UserController.getCurrentUserFullName());
            replyUserName11111111.setText(UserController.getCurrentUserName());
            replyUserSymbol11111111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 8) {
            reply111111111.setVisible(true);
            replyContent111111111.setText(UserController.getReplyPostContent(8, selectedPost));
            replyFullName111111111.setText(UserController.getCurrentUserFullName());
            replyUserName111111111.setText(UserController.getCurrentUserName());
            replyUserSymbol111111111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 9) {
            reply1111111111.setVisible(true);
            replyContent1111111111.setText(UserController.getReplyPostContent(9, selectedPost));
            replyFullName1111111111.setText(UserController.getCurrentUserFullName());
            replyUserName1111111111.setText(UserController.getCurrentUserName());
            replyUserSymbol1111111111.setText(UserController.getCurrentUserSymbol());
        }
        if (replyCount > 10) {
            reply11111111111.setVisible(true);
            replyContent11111111111.setText(UserController.getReplyPostContent(10, selectedPost));
            replyFullName11111111111.setText(UserController.getCurrentUserFullName());
            replyUserName11111111111.setText(UserController.getCurrentUserName());
            replyUserSymbol11111111111.setText(UserController.getCurrentUserSymbol());
        }

        postView.setVisible(true);
        postView.toFront();
    }

    private void handleLikeOnLabel() {
        if (UserController.getCurrentPost() == null) return;
        Post updated = UserController.toggleLike(UserController.getCurrentPost());
        if (updated != null) {
            UserController.setCurrentPost(updated);
            openPostLikeNumber.setText(LIKE + String.valueOf(updated.getLikesNumber()));
            // به‌روزرسانی رنگ
            boolean liked = UserController.isLikedByCurrentUser(updated);
            openPostLikeNumber.setStyle(liked ? "-fx-text-fill: red; -fx-cursor: hand;" : "-fx-text-fill: black; -fx-cursor: hand;");
        }
    }

    @FXML
    void sendReply() {
        if (replyText.getText().trim().isEmpty()) return;
        UserController.addAnswerPost(replyText.getText(), replyHashtags.getText());
        openPostCommentNumber.setText(COMMENT + UserController.getCurrentPost().getCommentsNumber());
        replyText.setText("");
        replyHashtags.setText("");
        // به‌روزرسانی نمایش replies
        refreshReplies();
    }

    private void refreshReplies() {
        if (UserController.getCurrentPost() == null) return;
        // مخفی کردن همه
        reply1.setVisible(false);
        reply11.setVisible(false);
        reply111.setVisible(false);
        reply1111.setVisible(false);
        reply11111.setVisible(false);
        reply111111.setVisible(false);
        reply1111111.setVisible(false);
        reply11111111.setVisible(false);
        reply111111111.setVisible(false);
        reply1111111111.setVisible(false);
        reply11111111111.setVisible(false);

        int replyCount = UserController.getReplyPostNumber(UserController.getCurrentPost());
        System.out.println(replyCount);
        // نمایش به تعداد replyCount (همان کدی که در openPost برای نمایش replyها نوشته شده)
        if (replyCount > 0) {
            reply1.setVisible(true);
            replyContent1.setText(UserController.getReplyPostContent(0, UserController.getCurrentPost()));
            replyFullName1.setText(UserController.getCurrentUserFullName());
            replyUserName1.setText(UserController.getCurrentUserName());
            replyUserSymbol1.setText(UserController.getCurrentUserSymbol());
        }

        if (replyCount > 1) {
            reply11.setVisible(true);
            replyContent11.setText(UserController.getReplyPostContent(0, UserController.getCurrentPost()));
            replyFullName11.setText(UserController.getCurrentUserFullName());
            replyUserName11.setText(UserController.getCurrentUserName());
            replyUserSymbol11.setText(UserController.getCurrentUserSymbol());
        }

        if (replyCount > 2) {
            reply111.setVisible(true);
            replyContent111.setText(UserController.getReplyPostContent(0, UserController.getCurrentPost()));
            replyFullName111.setText(UserController.getCurrentUserFullName());
            replyUserName111.setText(UserController.getCurrentUserName());
            replyUserSymbol111.setText(UserController.getCurrentUserSymbol());
        }

        if (replyCount > 3) {
            reply1111.setVisible(true);
            replyContent1111.setText(UserController.getReplyPostContent(0, UserController.getCurrentPost()));
            replyFullName1111.setText(UserController.getCurrentUserFullName());
            replyUserName1111.setText(UserController.getCurrentUserName());
            replyUserSymbol1111.setText(UserController.getCurrentUserSymbol());
        }

        if (replyCount > 4) {
            reply1.setVisible(true);
            replyContent11111.setText(UserController.getReplyPostContent(0, UserController.getCurrentPost()));
            replyFullName11111.setText(UserController.getCurrentUserFullName());
            replyUserName11111.setText(UserController.getCurrentUserName());
            replyUserSymbol11111.setText(UserController.getCurrentUserSymbol());
        }
        // ... به همین ترتیب برای بقیه
    }

    @FXML
    private void openHomePost(){
        postView.setVisible(true);
        postView.toFront();
    }


    @FXML
    public void reportPost(ActionEvent event) {
        // کد مربوط به گزارش پست
    }
    @FXML
    private void goToChat(){
        // 1. مخفی کردن همه پیام‌ها
        message.setVisible(false);
        message1.setVisible(false);
        message2.setVisible(false);
        message3.setVisible(false);
        message4.setVisible(false);
        message5.setVisible(false);
        message6.setVisible(false);
        message7.setVisible(false);
        message8.setVisible(false);
        message9.setVisible(false);
        message10.setVisible(false);

        // 2. بارگذاری تاریخچه چت از سرور
        if (UserController.getCurrentUser() != null && UserController.getCurrentUser2() != null) {

            UserController.loadChatHistory(UserController.getCurrentUser().getUserId(), UserController.getCurrentUser2().getUserId());
        }

        // 3. نمایش پیام‌ها بر اساس تعداد
        int messageCount = UserController.getChatMessageNumber();


        // لیست تمام Textهای پیام
        List<Text> messageTexts = Arrays.asList(
                message, message1, message2, message3, message4,
                message5, message6, message7, message8, message9, message10
        );

        for (int i = 0; i < Math.min(messageCount, messageTexts.size()); i++) {
            Text msg = messageTexts.get(i);
            msg.setVisible(true);


            // دریافت پیام از UserController
            String sender = UserController.getChatMessageSenderName(i);
            String content = UserController.getChatMessageContent(i);
            String time = UserController.getChatMessageTime(i);

            // تنظیم محتوای پیام با فرمت: [ساعت] فرستنده: محتوا
            msg.setText(time + " " + sender + ": " + content);

            // تنظیم رنگ بر اساس فرستنده
            if (UserController.isChatMessageSentByCurrentUser(i)) {
                // پیام‌های خودم (آبی)
                msg.setFill(javafx.scene.paint.Color.BLUE);
                msg.setStyle("-fx-font-weight: bold;");
            } else {
                // پیام‌های کاربر دیگر (سبز تیره)
                msg.setFill(javafx.scene.paint.Color.DARKGREEN);
            }
        }

        // 4. نمایش جعبه ورودی پیام
        chat.setVisible(true);
        chat.toFront();

    }

    @FXML
    private void sendMessage() {
        String content = messageBox.getText().trim();
        if (!content.isEmpty() && UserController.getCurrentUser() != null && UserController.getCurrentUser2() != null) {
            // ارسال پیام
            UserController.sendChatMessage(content);

            // پاک کردن فیلد ورودی
            messageBox.clear();

            // به‌روزرسانی نمایش چت
            goToChat();
        }
    }



}

