package com.example.demo5.view;

import com.example.demo5.controller.AdminController;
import com.example.demo5.controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField confrimPassword;

    @FXML
    private TextField email;

    @FXML
    private TextField emailOrUsername;

    @FXML
    private TextField fullName;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginPassword;

    @FXML
    private Label loginText;

    @FXML
    private TextField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button signupButton;

    @FXML
    private Label signupText;

    @FXML
    private TextField userName;

    @FXML
    void login(ActionEvent event) throws IOException {
        // اتصال به سرور (اگر قبلاً متصل نشده باشد)
        if (!UserController.connectToServer("localhost", 8080)) {
            loginText.setText("Cannot connect to server");
            return;
        }

        loginText.setText(UserController.getLoginText(emailOrUsername.getText(), loginPassword.getText()));

        if (loginText.getText().equals("")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo5/view/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        } else if (loginText.getText().equals("Admin")) {
            // برای ادمین، connection را به AdminController می‌دهیم
            AdminController.setConnection(UserController.getConnection()); // نیاز به تغییر
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo5/view/admin-panel.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        }
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
        if (!UserController.connectToServer("localhost", 8080)) {
            signupText.setText("Cannot connect to server");
            return;
        }

        signupText.setText(UserController.getSignUpText(fullName.getText(), userName.getText(), phoneNumber.getText(), email.getText(), password.getText(), confrimPassword.getText()));
        if (signupText.getText().equals("")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo5/view/choose-hashtags.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        }
    }
}