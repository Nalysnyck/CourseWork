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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField againPasswordField;
    @FXML
    private Label loginExistLabel;
    @FXML
    private Label loginErrorLogin;
    @FXML
    private Label againPasswordErrorLabel;
    @FXML
    private Line safetyLine;
    @FXML
    private Label passwordTipLabel;
    @FXML
    private Button rightButton;
    @FXML
    private Button leftButton;
    @FXML
    private ImageView avatarImage;
    private int avatarId = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LinearGradient gradientRed = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(0.3333, Color.WHITE));
        LinearGradient gradientYellow = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOW), new Stop(0.6666, Color.WHITE));
        LinearGradient gradientGreen = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#00ff3c")), new Stop(1, Color.WHITE));
        LinearGradient gradientGrey = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#cfcfcf")), new Stop(0.1, Color.WHITE));

        safetyLine.setStroke(gradientGrey);

        loginTextField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                onSignUpButtonClick();
            }
        });
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                onSignUpButtonClick();
            }
        });
        passwordField.setOnKeyReleased(e -> {
            int safety = levelOfPasswordSafety(passwordField.getText());

            passwordTipLabel.setStyle("-fx-text-fill: black");
            if (safety == 0) {
                passwordTipLabel.setText("Пароль має містити від 8 символів, цифри, малі та великі літери");
                safetyLine.setStroke(gradientGrey);
            } else if (safety == 1) {
                passwordTipLabel.setText("Пароль має містити цифри, малі та великі літери");
                safetyLine.setStroke(gradientRed);
            } else if (safety == 2) {
                passwordTipLabel.setText("Пароль має містити малі та великі літери");
                safetyLine.setStroke(gradientYellow);
            } else if (safety == 3) {
                passwordTipLabel.setText("Ви добряче захищені!");
                safetyLine.setStroke(gradientGreen);
            }
        });
        againPasswordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                onSignUpButtonClick();
            }
        });
    }

    @FXML
    public void onRightButtonClick() {
        if (avatarId == 10) {
            avatarId = 1;
        } else {
            avatarId++;
        }
        avatarImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/avatar/" + avatarId + ".png")).toExternalForm()));
    }

    @FXML
    public void onLeftButtonClick() {
        if (avatarId == 1) {
            avatarId = 10;
        } else {
            avatarId--;
        }
        avatarImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/avatar/" + avatarId + ".png")).toExternalForm()));
    }

    private int levelOfPasswordSafety(String password) {
        boolean hasUpperLetters = false, hasLowerLetters = false, hasNumbers = false;
        char letter;

        if (password.length() < 8)
            return 0;
        for (int i = 0; i < password.length(); i++) {
            letter = password.charAt(i);
            if (letter >= '0' && letter <= '9') {
                hasNumbers = true;
            } else if (letter >= 'a' && letter <= 'z' || letter >= 'а' && letter <= 'я') {
                hasLowerLetters = true;
            } else if (letter >= 'A' && letter <= 'Z' || letter >= 'А' && letter <= 'Я') {
                hasUpperLetters = true;
            }
        }
        if (hasNumbers && hasLowerLetters && hasUpperLetters)
            return 3;
        if ((hasLowerLetters || hasUpperLetters) && hasNumbers)
            return 2;
        return 1;
    }

    @FXML
    public void onBackButtonClick(){
        new Util().setScene(getClass().getResource("log-in.fxml"));
    }

    @FXML
    public void onSignUpButtonClick() {
        String login = loginTextField.getText().trim();
        String password = passwordField.getText();
        String passwordAgain = againPasswordField.getText();
        String dateRegistration = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());

        againPasswordErrorLabel.setVisible(false);
        loginExistLabel.setVisible(false);
        loginErrorLogin.setVisible(false);
        passwordField.setText("");
        againPasswordField.setText("");

        User user = new UserService().selectUser(login);

        if (user != null) {
            loginExistLabel.setVisible(true);
            loginTextField.setText("");
        } else if (login.length() < 5 || login.length() > 30) {
            loginErrorLogin.setVisible(true);
        } else if (!password.equals(passwordAgain)) {
            againPasswordErrorLabel.setVisible(true);
        } else if (levelOfPasswordSafety(password) != 3){
            passwordTipLabel.setStyle("-fx-text-fill: red");
        } else {
            onBackButtonClick();
            new UserService().insertUser(new User(login, password, false, false, dateRegistration, dateRegistration, avatarId));
        }
    }
}