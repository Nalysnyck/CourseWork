package com.mobileconnection.controller;

import com.mobileconnection.data.MobileCompany;
import com.mobileconnection.data.TariffPackage;
import com.mobileconnection.data.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class Util {
    private static MobileCompany currentCompany;
    private static TariffPackage currentTariff;
    private static User currentUser;
    private static Stage stage;

    public void setScene(URL url){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(url));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCurrentCompany(MobileCompany currentCompany) {
        Util.currentCompany = currentCompany;
    }

    public static MobileCompany getCurrentCompany() {
        return currentCompany;
    }

    public static TariffPackage getCurrentTariff() {
        return currentTariff;
    }

    public static void setCurrentTariff(TariffPackage currentTariff) {
        Util.currentTariff = currentTariff;
    }

    public static User getCurrentUser() { return currentUser; }

    public static void setCurrentUser(User currentUser) { Util.currentUser = currentUser; }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Util.stage = stage;
    }
}
