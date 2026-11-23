package com.example.demo5.view;

import com.example.demo5.controller.AdminController;
import com.example.demo5.controller.UserController;
import com.example.demo5.models.Hashtag;
import com.example.demo5.models.Post;
import com.example.demo5.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanel {

    // ======== تعریف تمام متغیرهای FXML (بدون تغییر) ========
    @FXML private Label userProfileFullName;
    @FXML private Label userProfileUserName;
    @FXML private Label userProfileSymbol;
    @FXML private Label userProfileMadeDate;
    @FXML private Text userProfileBiography;
    @FXML private Label userProfileFollowers;
    @FXML private Label userProfileFollowings;
    @FXML private VBox userProfilePost;
    @FXML private Label userProfilePostFullName;
    @FXML private Label userProfilePostUserName;
    @FXML private Label userProfilePostSymbol;
    @FXML private Text userProfilePostContent;
    @FXML private MediaView userProfilePostMedia;

    @FXML private VBox userProfilePost1;
    @FXML private Label userProfilePostFullName1;
    @FXML private Label userProfilePostUserName1;
    @FXML private Label userProfilePostSymbol1;
    @FXML private Text userProfilePostContent1;
    @FXML private MediaView userProfilePostMedia1;

    @FXML private VBox userProfilePost2;
    @FXML private Label userProfilePostFullName2;
    @FXML private Label userProfilePostUserName2;
    @FXML private Label userProfilePostSymbol2;
    @FXML private Text userProfilePostContent2;
    @FXML private MediaView userProfilePostMedia2;

    @FXML private VBox userProfilePost3;
    @FXML private Label userProfilePostFullName3;
    @FXML private Label userProfilePostUserName3;
    @FXML private Label userProfilePostSymbol3;
    @FXML private Text userProfilePostContent3;
    @FXML private MediaView userProfilePostMedia3;

    @FXML private VBox userProfilePost4;
    @FXML private Label userProfilePostFullName4;
    @FXML private Label userProfilePostUserName4;
    @FXML private Label userProfilePostSymbol4;
    @FXML private Text userProfilePostContent4;
    @FXML private MediaView userProfilePostMedia4;

    @FXML private VBox userProfilePost5;
    @FXML private Label userProfilePostFullName5;
    @FXML private Label userProfilePostUserName5;
    @FXML private Label userProfilePostSymbol5;
    @FXML private Text userProfilePostContent5;
    @FXML private MediaView userProfilePostMedia5;

    @FXML private VBox userProfilePost6;
    @FXML private Label userProfilePostFullName6;
    @FXML private Label userProfilePostUserName6;
    @FXML private Label userProfilePostSymbol6;
    @FXML private Text userProfilePostContent6;
    @FXML private MediaView userProfilePostMedia6;

    @FXML private VBox userProfilePost7;
    @FXML private Label userProfilePostFullName7;
    @FXML private Label userProfilePostUserName7;
    @FXML private Label userProfilePostSymbol7;
    @FXML private Text userProfilePostContent7;
    @FXML private MediaView userProfilePostMedia7;

    @FXML private VBox userProfilePost8;
    @FXML private Label userProfilePostFullName8;
    @FXML private Label userProfilePostUserName8;
    @FXML private Label userProfilePostSymbol8;
    @FXML private Text userProfilePostContent8;
    @FXML private MediaView userProfilePostMedia8;

    // Replies (13 عدد)
    @FXML private Label userProfileRepliesfullName;
    @FXML private Label userProfileRepliesSymbol;
    @FXML private Label userProfileRepliesUserName;
    @FXML private Text userProfileRepliesContent;
    @FXML private VBox userProfileReplies;
    // ... به همین ترتیب برای 13 تای دیگر (برای اختصار حذف شده، ولی در فایل اصلی وجود دارد)

    @FXML private ScrollPane goodPost;
    @FXML private MediaView Media1;
    @FXML private MediaView Media11;
    @FXML private MediaView Media111;
    @FXML private MediaView Media1111;
    @FXML private MediaView Media11111;
    @FXML private MediaView Media111111;
    @FXML private MediaView Media1111111;
    @FXML private MediaView Media11111111;
    @FXML private MediaView Media111111111;

    @FXML private Label fullName;
    @FXML private Label fullName1;
    @FXML private Label fullName11;
    @FXML private Label fullName111;
    @FXML private Label fullName1111;
    @FXML private Label fullName11111;
    @FXML private Label fullName111111;
    @FXML private Label fullName1111111;
    @FXML private Label fullName11111111;
    @FXML private Label fullName111111111;

    @FXML private HBox hashtag1;
    @FXML private HBox hashtag11;
    @FXML private HBox hashtag111;
    @FXML private HBox hashtag1111;
    @FXML private HBox hashtag11111;
    @FXML private HBox hashtag111111;
    @FXML private HBox hashtag1111111;
    @FXML private HBox hashtag11111111;
    @FXML private HBox hashtag111111111;
    @FXML private HBox hashtag1111111111;
    @FXML private HBox hashtag11111111111;
    @FXML private HBox hashtag111111111111;
    @FXML private HBox hashtag1111111111111;
    @FXML private HBox hashtag11111111111111;
    @FXML private HBox hashtag111111111111111;
    @FXML private HBox hashtag1111111111111111;
    @FXML private HBox hashtag11111111111111111;
    @FXML private HBox hashtag111111111111111111;
    @FXML private HBox hashtag1111111111111111111;
    @FXML private HBox hashtag11111111111111111111;

    @FXML private Label hashtagName1;
    @FXML private Label hashtagName11;
    @FXML private Label hashtagName111;
    @FXML private Label hashtagName1111;
    @FXML private Label hashtagName11111;
    @FXML private Label hashtagName111111;
    @FXML private Label hashtagName1111111;
    @FXML private Label hashtagName11111111;
    @FXML private Label hashtagName111111111;
    @FXML private Label hashtagName1111111111;
    @FXML private Label hashtagName11111111111;
    @FXML private Label hashtagName111111111111;
    @FXML private Label hashtagName1111111111111;
    @FXML private Label hashtagName11111111111111;
    @FXML private Label hashtagName111111111111111;
    @FXML private Label hashtagName1111111111111111;
    @FXML private Label hashtagName11111111111111111;
    @FXML private Label hashtagName111111111111111111;
    @FXML private Label hashtagName1111111111111111111;
    @FXML private Label hashtagName11111111111111111111;

    @FXML private Label hashtagNumber1;
    @FXML private Label hashtagNumber11;
    @FXML private Label hashtagNumber111;
    @FXML private Label hashtagNumber1111;
    @FXML private Label hashtagNumber11111;
    @FXML private Label hashtagNumber111111;
    @FXML private Label hashtagNumber1111111;
    @FXML private Label hashtagNumber11111111;
    @FXML private Label hashtagNumber111111111;
    @FXML private Label hashtagNumber1111111111;
    @FXML private Label hashtagNumber11111111111;
    @FXML private Label hashtagNumber111111111111;
    @FXML private Label hashtagNumber1111111111111;
    @FXML private Label hashtagNumber11111111111111;
    @FXML private Label hashtagNumber111111111111111;
    @FXML private Label hashtagNumber1111111111111111;
    @FXML private Label hashtagNumber11111111111111111;
    @FXML private Label hashtagNumber111111111111111111;
    @FXML private Label hashtagNumber1111111111111111111;
    @FXML private Label hashtagNumber11111111111111111111;

    @FXML private ScrollPane hashtags;
    @FXML private VBox post1;
    @FXML private VBox post11;
    @FXML private VBox post111;
    @FXML private VBox post1111;
    @FXML private VBox post11111;
    @FXML private VBox post111111;
    @FXML private VBox post1111111;
    @FXML private VBox post11111111;
    @FXML private VBox post111111111;
    @FXML private Label postComment1;
    @FXML private Label postComment11;
    @FXML private Label postComment111;
    @FXML private Label postComment1111;
    @FXML private Label postComment11111;
    @FXML private Label postComment111111;
    @FXML private Label postComment1111111;
    @FXML private Label postComment11111111;
    @FXML private Label postComment111111111;
    @FXML private Text postContent1;
    @FXML private Text postContent11;
    @FXML private Text postContent111;
    @FXML private Text postContent1111;
    @FXML private Text postContent11111;
    @FXML private Text postContent111111;
    @FXML private Text postContent1111111;
    @FXML private Text postContent11111111;
    @FXML private Text postContent111111111;
    @FXML private Text postFullName1;
    @FXML private Text postFullName11;
    @FXML private Text postFullName111;
    @FXML private Text postFullName1111;
    @FXML private Text postFullName11111;
    @FXML private Text postFullName111111;
    @FXML private Text postFullName1111111;
    @FXML private Text postFullName11111111;
    @FXML private Text postFullName111111111;
    @FXML private Label postLike1;
    @FXML private Label postLike11;
    @FXML private Label postLike111;
    @FXML private Label postLike1111;
    @FXML private Label postLike11111;
    @FXML private Label postLike111111;
    @FXML private Label postLike1111111;
    @FXML private Label postLike11111111;
    @FXML private Label postLike111111111;
    @FXML private ScrollPane postReports;
    @FXML private Label postRetweet1;
    @FXML private Label postRetweet11;
    @FXML private Label postRetweet111;
    @FXML private Label postRetweet1111;
    @FXML private Label postRetweet11111;
    @FXML private Label postRetweet111111;
    @FXML private Label postRetweet1111111;
    @FXML private Label postRetweet11111111;
    @FXML private Label postRetweet111111111;
    @FXML private Text postUserName1;
    @FXML private Text postUserName11;
    @FXML private Text postUserName111;
    @FXML private Text postUserName1111;
    @FXML private Text postUserName11111;
    @FXML private Text postUserName111111;
    @FXML private Text postUserName1111111;
    @FXML private Text postUserName11111111;
    @FXML private Text postUserName111111111;
    @FXML private Text postUserSymbol1;
    @FXML private Text postUserSymbol11;
    @FXML private Text postUserSymbol111;
    @FXML private Text postUserSymbol1111;
    @FXML private Text postUserSymbol11111;
    @FXML private Text postUserSymbol111111;
    @FXML private Text postUserSymbol1111111;
    @FXML private Text postUserSymbol11111111;
    @FXML private Text postUserSymbol111111111;
    @FXML private ScrollPane postView;
    @FXML private Label postView1;
    @FXML private Label postView11;
    @FXML private Label postView111;
    @FXML private Label postView1111;
    @FXML private Label postView11111;
    @FXML private Label postView111111;
    @FXML private Label postView1111111;
    @FXML private Label postView11111111;
    @FXML private Label postView111111111;
    @FXML private ScrollPane posts;
    @FXML private VBox reportPost1;
    @FXML private VBox reportPost11;
    @FXML private VBox reportPost111;
    @FXML private VBox reportPost1111;
    @FXML private VBox reportPost11111;
    @FXML private VBox reportPost111111;
    @FXML private VBox reportPost1111111;
    @FXML private Text reportPostContent1;
    @FXML private Text reportPostContent11;
    @FXML private Text reportPostContent111;
    @FXML private Text reportPostContent1111;
    @FXML private Text reportPostContent11111;
    @FXML private Text reportPostContent111111;
    @FXML private Text reportPostContent1111111;
    @FXML private Text reportPostFullName1;
    @FXML private Text reportPostFullName11;
    @FXML private Text reportPostFullName111;
    @FXML private Text reportPostFullName1111;
    @FXML private Text reportPostFullName11111;
    @FXML private Text reportPostFullName111111;
    @FXML private Text reportPostFullName1111111;
    @FXML private Text reportPostUserName1;
    @FXML private Text reportPostUserName11;
    @FXML private Text reportPostUserName111;
    @FXML private Text reportPostUserName1111;
    @FXML private Text reportPostUserName11111;
    @FXML private Text reportPostUserName111111;
    @FXML private Text reportPostUserName1111111;
    @FXML private Text reportPostUserSymbol;
    @FXML private Text reportPostUserSymbol1;
    @FXML private Text reportPostUserSymbol11;
    @FXML private Text reportPostUserSymbol111;
    @FXML private Text reportPostUserSymbol1111;
    @FXML private Text reportPostUserSymbol11111;
    @FXML private Text reportPostUserSymbol111111;
    @FXML private Text reportPostUserSymbol1111111;
    @FXML private HBox user;
    @FXML private HBox user1;
    @FXML private HBox user11;
    @FXML private HBox user111;
    @FXML private HBox user1111;
    @FXML private HBox user11111;
    @FXML private HBox user111111;
    @FXML private HBox user1111111;
    @FXML private HBox user11111111;
    @FXML private HBox user111111111;
    @FXML private Label userName;
    @FXML private Label userName1;
    @FXML private Label userName11;
    @FXML private Label userName111;
    @FXML private Label userName1111;
    @FXML private Label userName11111;
    @FXML private Label userName111111;
    @FXML private Label userName1111111;
    @FXML private Label userName11111111;
    @FXML private Label userName111111111;
    @FXML private ScrollPane userProfile;
    @FXML private ScrollPane userReports;
    @FXML private Label userSymbol;
    @FXML private Label userSymbol1;
    @FXML private Label userSymbol11;
    @FXML private Label userSymbol111;
    @FXML private Label userSymbol1111;
    @FXML private Label userSymbol11111;
    @FXML private Label userSymbol111111;
    @FXML private Label userSymbol1111111;
    @FXML private Label userSymbol11111111;
    @FXML private Label userSymbol111111111;
    @FXML private ScrollPane users;

    // ======== متدهای رویداد ========

    @FXML
    void blockPost(ActionEvent event) {
        // همان کد قبلی (بدون تغییر)
        Button clickedButton = (Button) event.getSource();
        VBox parentVBox = (VBox) clickedButton.getParent().getParent();
        String vboxId = parentVBox.getId();
        if(vboxId.equals("iif")){
            // ...
        }
    }

    @FXML
    void blockUser(ActionEvent event) {
        // ...
    }

    @FXML
    void goToGoodPost(ActionEvent event) {
        goodPost.setVisible(true);
        goodPost.toFront();
    }

    @FXML
    void goToUserReports(ActionEvent event){
        userReports.setVisible(true);
        userReports.toFront();
    }

    @FXML
    void openUserProfile(MouseEvent event) {
        // بدون تغییر (از AdminController استفاده می‌کند که قبلاً اصلاح شده)
        Object source = event.getSource();
        if (source instanceof HBox) {
            HBox clickedPane = (HBox) source;
            String paneId = clickedPane.getId();
            int index = 0;
            switch (paneId) {
                case "user": index = 0; break;
                case "user1": index = 1; break;
                case "user11": index = 2; break;
                case "user111": index = 3; break;
                case "user1111": index = 4; break;
                case "user11111": index = 5; break;
                case "user111111": index = 6; break;
                case "user1111111": index = 7; break;
                case "user11111111": index = 8; break;
                case "user111111111": index = 9; break;
            }
            System.out.println(index);
            AdminController.setCurrentUser(index);
        }

        AdminController.updateReplies1();

        userProfileUserName.setText(AdminController.getCurrentUserName());
        userProfileFullName.setText(AdminController.getCurrentUserFullName());
        userProfileBiography.setText(AdminController.getCurrentUserBiography());
        userProfileFollowers.setText("followers" + AdminController.getCurrentUserFollowersNumber());
        userProfileFollowings.setText("following" + AdminController.getCurrentUserFollowingNumber());

        // نمایش پست‌های کاربر (کد قبلی بدون تغییر)
        userProfilePost.setVisible(false);
        userProfilePost1.setVisible(false);
        userProfilePost2.setVisible(false);
        userProfilePost3.setVisible(false);
        userProfilePost4.setVisible(false);
        userProfilePost5.setVisible(false);
        userProfilePost6.setVisible(false);
        userProfilePost7.setVisible(false);
        userProfilePost8.setVisible(false);

        if(AdminController.getCurrentUserPostNumber() > 0){
            userProfilePost.setVisible(true);
            userProfilePostContent.setText(AdminController.getUserPostByIndex(0).getPostText());
            userProfilePostUserName.setText(AdminController.getCurrentUserName());
            userProfilePostFullName.setText(AdminController.getCurrentUserFullName());
            userProfilePostSymbol.setText(AdminController.getCurrentUSerSymbol());
            // ... اگر فایل داشت
        }
        // ... به همین ترتیب برای سایر پست‌ها (کد قبلی)

        userProfile.setVisible(true);
        userProfile.toFront();
    }

    @FXML
    void goToUsers(ActionEvent event) {
        // ======== اصلاح: جایگزینی DataBase با AdminController و به‌روزرسانی ========
        AdminController.refreshUsers(); // بروزرسانی لیست کاربران

        user.setVisible(false);
        user1.setVisible(false);
        user11.setVisible(false);
        user111.setVisible(false);
        user1111.setVisible(false);
        user11111.setVisible(false);
        user111111.setVisible(false);
        user1111111.setVisible(false);
        user11111111.setVisible(false);
        user111111111.setVisible(false);

        if (AdminController.getUserNumbere() > 0) {
            User u = AdminController.getUserByIndex(0);
            user.setVisible(true);
            fullName.setText(u.getFullName());
            userName.setText(u.getUserName());
            userSymbol.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 1) {
            User u = AdminController.getUserByIndex(1);
            user1.setVisible(true);
            fullName1.setText(u.getFullName());
            userName1.setText(u.getUserName());
            userSymbol1.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 2) {
            User u = AdminController.getUserByIndex(2);
            user11.setVisible(true);
            fullName11.setText(u.getFullName());
            userName11.setText(u.getUserName());
            userSymbol11.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 3) {
            User u = AdminController.getUserByIndex(3);
            user111.setVisible(true);
            fullName111.setText(u.getFullName());
            userName111.setText(u.getUserName());
            userSymbol111.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 4) {
            User u = AdminController.getUserByIndex(4);
            user1111.setVisible(true);
            fullName1111.setText(u.getFullName());
            userName1111.setText(u.getUserName());
            userSymbol1111.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 5) {
            User u = AdminController.getUserByIndex(5);
            user11111.setVisible(true);
            fullName11111.setText(u.getFullName());
            userName11111.setText(u.getUserName());
            userSymbol11111.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 6) {
            User u = AdminController.getUserByIndex(6);
            user111111.setVisible(true);
            fullName111111.setText(u.getFullName());
            userName111111.setText(u.getUserName());
            userSymbol111111.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 7) {
            User u = AdminController.getUserByIndex(7);
            user1111111.setVisible(true);
            fullName1111111.setText(u.getFullName());
            userName1111111.setText(u.getUserName());
            userSymbol1111111.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 8) {
            User u = AdminController.getUserByIndex(8);
            user11111111.setVisible(true);
            fullName11111111.setText(u.getFullName());
            userName11111111.setText(u.getUserName());
            userSymbol11111111.setText(u.getSymbolImage());
        }
        if (AdminController.getUserNumbere() > 9) {
            User u = AdminController.getUserByIndex(9);
            user111111111.setVisible(true);
            fullName111111111.setText(u.getFullName());
            userName111111111.setText(u.getUserName());
            userSymbol111111111.setText(u.getSymbolImage());
        }

        users.setVisible(true);
        users.toFront();
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo5/view/hello-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    void goToPosts(ActionEvent event) {
        // ======== اصلاح: جایگزینی DataBase با AdminController و به‌روزرسانی ========
        AdminController.refreshPosts(); // بروزرسانی لیست پست‌ها

        post1.setVisible(false);
        post11.setVisible(false);
        post111.setVisible(false);
        post1111.setVisible(false);
        post11111.setVisible(false);
        post111111.setVisible(false);
        post1111111.setVisible(false);
        post11111111.setVisible(false);
        post111111111.setVisible(false);

        if (AdminController.getPostNumber() > 0) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(0);
            post1.setVisible(true);
            postContent1.setText(p.getPostText());
            postUserName1.setText(p.getUserName());
            postFullName1.setText(p.getFullName());
            postUserSymbol1.setText(p.getSymbol());
            postLike1.setText(String.valueOf(p.getLikesNumber()));
            postRetweet1.setText(String.valueOf(p.getRetweetCount()));
            postComment1.setText(String.valueOf(p.getCommentsNumber()));
            postView1.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 1) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(1);
            post11.setVisible(true);
            postContent11.setText(p.getPostText());
            postUserName11.setText(p.getUserName());
            postFullName11.setText(p.getFullName());
            postUserSymbol11.setText(p.getSymbol());
            postLike11.setText(String.valueOf(p.getLikesNumber()));
            postRetweet11.setText(String.valueOf(p.getRetweetCount()));
            postComment11.setText(String.valueOf(p.getCommentsNumber()));
            postView11.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 2) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(2);
            post111.setVisible(true);
            postContent111.setText(p.getPostText());
            postUserName111.setText(p.getUserName());
            postFullName111.setText(p.getFullName());
            postUserSymbol111.setText(p.getSymbol());
            postLike111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet111.setText(String.valueOf(p.getRetweetCount()));
            postComment111.setText(String.valueOf(p.getCommentsNumber()));
            postView111.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 3) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(3);
            post1111.setVisible(true);
            postContent1111.setText(p.getPostText());
            postUserName1111.setText(p.getUserName());
            postFullName1111.setText(p.getFullName());
            postUserSymbol1111.setText(p.getSymbol());
            postLike1111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet1111.setText(String.valueOf(p.getRetweetCount()));
            postComment1111.setText(String.valueOf(p.getCommentsNumber()));
            postView1111.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 4) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(4);
            post11111.setVisible(true);
            postContent11111.setText(p.getPostText());
            postUserName11111.setText(p.getUserName());
            postFullName11111.setText(p.getFullName());
            postUserSymbol11111.setText(p.getSymbol());
            postLike11111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet11111.setText(String.valueOf(p.getRetweetCount()));
            postComment11111.setText(String.valueOf(p.getCommentsNumber()));
            postView11111.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 5) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(5);
            post111111.setVisible(true);
            postContent111111.setText(p.getPostText());
            postUserName111111.setText(p.getUserName());
            postFullName111111.setText(p.getFullName());
            postUserSymbol111111.setText(p.getSymbol());
            postLike111111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet111111.setText(String.valueOf(p.getRetweetCount()));
            postComment111111.setText(String.valueOf(p.getCommentsNumber()));
            postView111111.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 6) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(6);
            post1111111.setVisible(true);
            postContent1111111.setText(p.getPostText());
            postUserName1111111.setText(p.getUserName());
            postFullName1111111.setText(p.getFullName());
            postUserSymbol1111111.setText(p.getSymbol());
            postLike1111111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet1111111.setText(String.valueOf(p.getRetweetCount()));
            postComment1111111.setText(String.valueOf(p.getCommentsNumber()));
            postView1111111.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 7) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(7);
            post11111111.setVisible(true);
            postContent11111111.setText(p.getPostText());
            postUserName11111111.setText(p.getUserName());
            postFullName11111111.setText(p.getFullName());
            postUserSymbol11111111.setText(p.getSymbol());
            postLike11111111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet11111111.setText(String.valueOf(p.getRetweetCount()));
            postComment11111111.setText(String.valueOf(p.getCommentsNumber()));
            postView11111111.setText(String.valueOf(p.getSawNumber()));
        }
        if (AdminController.getPostNumber() > 8) {
            AdminController.AdminPostView p = AdminController.getPostByIndex(8);
            post111111111.setVisible(true);
            postContent111111111.setText(p.getPostText());
            postUserName111111111.setText(p.getUserName());
            postFullName111111111.setText(p.getFullName());
            postUserSymbol111111111.setText(p.getSymbol());
            postLike111111111.setText(String.valueOf(p.getLikesNumber()));
            postRetweet111111111.setText(String.valueOf(p.getRetweetCount()));
            postComment111111111.setText(String.valueOf(p.getCommentsNumber()));
            postView111111111.setText(String.valueOf(p.getSawNumber()));
        }

        posts.setVisible(true);
        posts.toFront();
    }

    @FXML
    void goToHashtags(ActionEvent event) {
        // ======== اصلاح: جایگزینی DataBase با AdminController و به‌روزرسانی ========
        AdminController.refreshHashtags();

        hashtag1.setVisible(false);
        hashtag11.setVisible(false);
        hashtag111.setVisible(false);
        hashtag1111.setVisible(false);
        hashtag11111.setVisible(false);
        hashtag111111.setVisible(false);
        hashtag1111111.setVisible(false);
        hashtag11111111.setVisible(false);
        hashtag111111111.setVisible(false);
        hashtag1111111111.setVisible(false);
        hashtag11111111111.setVisible(false);
        hashtag111111111111.setVisible(false);
        hashtag1111111111111.setVisible(false);
        hashtag11111111111111.setVisible(false);
        hashtag111111111111111.setVisible(false);
        hashtag1111111111111111.setVisible(false);
        hashtag11111111111111111.setVisible(false);
        hashtag111111111111111111.setVisible(false);
        hashtag1111111111111111111.setVisible(false);
        hashtag11111111111111111111.setVisible(false);

        if (AdminController.getHashtagNumber() > 0) {
            Hashtag h = AdminController.getHashtagByIndex(0);
            hashtag1.setVisible(true);
            hashtagName1.setText(h.getName());
            hashtagNumber1.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 1) {
            Hashtag h = AdminController.getHashtagByIndex(1);
            hashtag11.setVisible(true);
            hashtagName11.setText(h.getName());
            hashtagNumber11.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 2) {
            Hashtag h = AdminController.getHashtagByIndex(2);
            hashtag111.setVisible(true);
            hashtagName111.setText(h.getName());
            hashtagNumber111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 3) {
            Hashtag h = AdminController.getHashtagByIndex(3);
            hashtag1111.setVisible(true);
            hashtagName1111.setText(h.getName());
            hashtagNumber1111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 4) {
            Hashtag h = AdminController.getHashtagByIndex(4);
            hashtag11111.setVisible(true);
            hashtagName11111.setText(h.getName());
            hashtagNumber11111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 5) {
            Hashtag h = AdminController.getHashtagByIndex(5);
            hashtag111111.setVisible(true);
            hashtagName111111.setText(h.getName());
            hashtagNumber111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 6) {
            Hashtag h = AdminController.getHashtagByIndex(6);
            hashtag1111111.setVisible(true);
            hashtagName1111111.setText(h.getName());
            hashtagNumber1111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 7) {
            Hashtag h = AdminController.getHashtagByIndex(7);
            hashtag11111111.setVisible(true);
            hashtagName11111111.setText(h.getName());
            hashtagNumber11111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 8) {
            Hashtag h = AdminController.getHashtagByIndex(8);
            hashtag111111111.setVisible(true);
            hashtagName111111111.setText(h.getName());
            hashtagNumber111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 9) {
            Hashtag h = AdminController.getHashtagByIndex(9);
            hashtag1111111111.setVisible(true);
            hashtagName1111111111.setText(h.getName());
            hashtagNumber1111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 10) {
            Hashtag h = AdminController.getHashtagByIndex(10);
            hashtag11111111111.setVisible(true);
            hashtagName11111111111.setText(h.getName());
            hashtagNumber11111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 11) {
            Hashtag h = AdminController.getHashtagByIndex(11);
            hashtag111111111111.setVisible(true);
            hashtagName111111111111.setText(h.getName());
            hashtagNumber111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 12) {
            Hashtag h = AdminController.getHashtagByIndex(12);
            hashtag1111111111111.setVisible(true);
            hashtagName1111111111111.setText(h.getName());
            hashtagNumber1111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 13) {
            Hashtag h = AdminController.getHashtagByIndex(13);
            hashtag11111111111111.setVisible(true);
            hashtagName11111111111111.setText(h.getName());
            hashtagNumber11111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 14) {
            Hashtag h = AdminController.getHashtagByIndex(14);
            hashtag111111111111111.setVisible(true);
            hashtagName111111111111111.setText(h.getName());
            hashtagNumber111111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 15) {
            Hashtag h = AdminController.getHashtagByIndex(15);
            hashtag1111111111111111.setVisible(true);
            hashtagName1111111111111111.setText(h.getName());
            hashtagNumber1111111111111111.setText(String.valueOf(h.getCount()));
        }
        // برای اندیس‌های ۱۶ تا ۱۹ از متد getHashtagsById استفاده می‌کنیم (همانند کد قبلی)
        if (AdminController.getHashtagNumber() > 16) {
            Hashtag h = AdminController.getHashtagsById(16);
            hashtag11111111111111111.setVisible(true);
            hashtagName11111111111111111.setText(h.getName());
            hashtagNumber11111111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 17) {
            Hashtag h = AdminController.getHashtagsById(17);
            hashtag111111111111111111.setVisible(true);
            hashtagName111111111111111111.setText(h.getName());
            hashtagNumber111111111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 18) {
            Hashtag h = AdminController.getHashtagsById(18);
            hashtag1111111111111111111.setVisible(true);
            hashtagName1111111111111111111.setText(h.getName());
            hashtagNumber1111111111111111111.setText(String.valueOf(h.getCount()));
        }
        if (AdminController.getHashtagNumber() > 19) {
            Hashtag h = AdminController.getHashtagsById(19);
            hashtag11111111111111111111.setVisible(true);
            hashtagName11111111111111111111.setText(h.getName());
            hashtagNumber11111111111111111111.setText(String.valueOf(h.getCount()));
        }

        hashtags.setVisible(true);
        hashtags.toFront();
    }

    @FXML
    void goToPostReports(ActionEvent event) {
        // ======== اصلاح: جایگزینی DataBase با AdminController و به‌روزرسانی ========
        AdminController.refreshReports();

        reportPost1.setVisible(false);
        reportPost11.setVisible(false);
        reportPost111.setVisible(false);
        reportPost1111.setVisible(false);
        reportPost11111.setVisible(false);
        reportPost111111.setVisible(false);
        reportPost1111111.setVisible(false);

        if (AdminController.getPostReportsNumber() > 0) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(0);
            reportPost1.setVisible(true);
            reportPostContent1.setText(r.getPostText());
            reportPostUserName1.setText(r.getUserName());
            reportPostFullName1.setText(r.getFullName());
            reportPostUserSymbol1.setText(r.getSymbol());
        }
        if (AdminController.getPostReportsNumber() > 1) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(1);
            reportPost11.setVisible(true);
            reportPostContent11.setText(r.getPostText());
            reportPostUserName11.setText(r.getUserName());
            reportPostFullName11.setText(r.getFullName());
            reportPostUserSymbol11.setText(r.getSymbol());
        }
        if (AdminController.getPostReportsNumber() > 2) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(2);
            reportPost111.setVisible(true);
            reportPostContent111.setText(r.getPostText());
            reportPostUserName111.setText(r.getUserName());
            reportPostFullName111.setText(r.getFullName());
            reportPostUserSymbol111.setText(r.getSymbol());
        }
        if (AdminController.getPostReportsNumber() > 3) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(3);
            reportPost1111.setVisible(true);
            reportPostContent1111.setText(r.getPostText());
            reportPostUserName1111.setText(r.getUserName());
            reportPostFullName1111.setText(r.getFullName());
            reportPostUserSymbol1111.setText(r.getSymbol());
        }
        if (AdminController.getPostReportsNumber() > 4) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(4);
            reportPost11111.setVisible(true);
            reportPostContent11111.setText(r.getPostText());
            reportPostUserName11111.setText(r.getUserName());
            reportPostFullName11111.setText(r.getFullName());
            reportPostUserSymbol11111.setText(r.getSymbol());
        }
        if (AdminController.getPostReportsNumber() > 5) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(5);
            reportPost111111.setVisible(true);
            reportPostContent111111.setText(r.getPostText());
            reportPostUserName111111.setText(r.getUserName());
            reportPostFullName111111.setText(r.getFullName());
            reportPostUserSymbol111111.setText(r.getSymbol());
        }
        if (AdminController.getPostReportsNumber() > 6) {
            AdminController.AdminReportView r = AdminController.getPostReportByIndex(6);
            reportPost1111111.setVisible(true);
            reportPostContent1111111.setText(r.getPostText());
            reportPostUserName1111111.setText(r.getUserName());
            reportPostFullName1111111.setText(r.getFullName());
            reportPostUserSymbol1111111.setText(r.getSymbol());
        }

        postReports.setVisible(true);
        postReports.toFront();
    }
}