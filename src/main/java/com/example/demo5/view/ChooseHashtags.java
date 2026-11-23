package com.example.demo5.view;

import com.example.demo5.controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ChooseHashtags {

    @FXML
    private Button hashtag1;

    @FXML
    private Button hashtag2;

    @FXML
    private Button hashtag3;

    @FXML
    private Button hashtag4;

    @FXML
    private Button hashtag5;

    @FXML
    private Button hashtag6;

    @FXML
    private Label hashtagErrorMessage;

    @FXML
    void changeColor(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if(clickedButton.getStyle().contains("white")){
            clickedButton.setStyle("-fx-background-color: #ccc");
        }else{
            clickedButton.setStyle("-fx-background-color: white;");
        }
    }

    @FXML
    void ok(ActionEvent event) throws IOException {
        ArrayList<Integer> hashtags = new ArrayList<>();
        int number = 0;
        if(hashtag1.getStyle().contains("white")){
            number++;
            hashtags.add(0);
        }
        if(hashtag2.getStyle().contains("white")){
            number++;
            hashtags.add(1);
        }
        if(hashtag3.getStyle().contains("white")){
            number++;
            hashtags.add(2);
        }
        if(hashtag4.getStyle().contains("white")){
            number++;
            hashtags.add(3);
        }
        if(hashtag5.getStyle().contains("white")){
            number++;
            hashtags.add(4);
        }
        if(hashtag6.getStyle().contains("white")){
            hashtags.add(5);
            number++;
        }
        if(number > 4){
            hashtagErrorMessage.setText("you choose more than 4 hashtags.");
        }else {
            UserController.setUserHashtags(hashtags);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo5/view/home.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        }

    }

}
