package com.mobileconnection.controller;

import com.mobileconnection.data.User;
import com.mobileconnection.database.UserService;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.mobileconnection.controller.Util.getCurrentUser;
import static com.mobileconnection.controller.Util.setStage;

public class InitializeStage {
    public InitializeStage(Stage stage){
        setStage(stage);
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/image/icon.png")).toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Мобільний зв'язок");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            if (getCurrentUser() != null) {
                User user = getCurrentUser();
                user.setDateLastSeen(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now()));
                new UserService().updateUser(getCurrentUser(), getCurrentUser());
            }
        });
        new Util().setScene(getClass().getResource("accounting/log-in.fxml"));
    }
}
