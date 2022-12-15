package com.mobileconnection.controller.accounting;

import com.mobileconnection.data.User;
import com.mobileconnection.controller.Util;
import com.mobileconnection.database.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

import java.net.URL;
import java.util.ResourceBundle;

import static com.mobileconnection.controller.Util.setCurrentUser;

public class LogIn implements Initializable {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label signUpLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Circle greyCircle1;
    @FXML
    private Circle greyCircle2;
    @FXML
    private Circle greenCircle1;
    @FXML
    private Circle greenCircle2;
    @FXML
    private Circle redCircle1;
    @FXML
    private Circle redCircle2;
    @FXML
    private CubicCurve greyCurve1;
    @FXML
    private CubicCurve greyCurve2;
    @FXML
    private CubicCurve greenCurve1;
    @FXML
    private CubicCurve greenCurve2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpLabel.setOnMouseClicked(event -> new Util().setScene(getClass().getResource("sign-up.fxml")));

        loginButton.setDisable(true);

        greyCurve2.setVisible(false);
        greenCurve1.setVisible(false);
        greenCurve2.setVisible(true);
        redCircle1.setVisible(false);
        redCircle2.setVisible(false);
        greenCircle1.setVisible(false);
        greenCircle2.setVisible(false);

        loginField.setOnKeyReleased(e -> {
            greenCircle1.setVisible(loginField.getText().length() > 4);
            greyCircle1.setVisible(loginField.getText().length() <= 4);
            redCircle1.setVisible(false);
            checkKeyPressed(e);
        });
        passwordField.setOnKeyReleased(e -> {
            greenCircle2.setVisible(passwordField.getText().length() > 7);
            greyCircle2.setVisible(passwordField.getText().length() <= 7);
            redCircle2.setVisible(false);
            checkKeyPressed(e);
        });

        loginField.setOnMousePressed(e -> {
            greyCircle1.setVisible(loginField.getText().length() <= 4);
            redCircle1.setVisible(false);
            greyCurve1.setVisible(false);
            greenCurve1.setVisible(true);
            greyCurve2.setVisible(true);
            greenCurve2.setVisible(false);
            errorLabel.setVisible(false);
        });
        passwordField.setOnMousePressed(e -> {
            greyCircle2.setVisible(passwordField.getText().length() <= 7);
            redCircle2.setVisible(false);
            greyCurve1.setVisible(true);
            greenCurve1.setVisible(false);
            greyCurve2.setVisible(false);
            greenCurve2.setVisible(true);
            errorLabel.setVisible(false);
        });
    }

    private void checkKeyPressed(KeyEvent e) {
        errorLabel.setVisible(false);
        loginButton.setDisable(true);
        if (loginField.getText().length() > 4 && passwordField.getText().length() > 7)
            loginButton.setDisable(false);
        if (e.getCode() == KeyCode.ENTER)
            onLoginButtonClick();
    }

    @FXML
    protected void onLoginButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();
        User user = new UserService().selectUser(login);

        if (user != null)
            if (user.getPassword().equals(password)) {
                errorLabel.setVisible(false);
                user.setDateLastSeen("");
                setCurrentUser(user);
                new UserService().updateUser(user, user);
                new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/main.fxml"));
            }
        errorLabel.setVisible(true);
        loginField.setText("");
        passwordField.setText("");
        loginButton.setDisable(true);

        greenCurve1.setVisible(false);
        greenCurve2.setVisible(true);
        greyCurve1.setVisible(true);
        greyCurve2.setVisible(false);
        redCircle1.setVisible(true);
        redCircle2.setVisible(true);
        greenCircle1.setVisible(false);
        greenCircle2.setVisible(false);
        greyCircle1.setVisible(false);
        greyCircle2.setVisible(false);
    }
}