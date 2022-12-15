package com.mobileconnection.controller.tariff;

import com.mobileconnection.data.TariffPackage;
import com.mobileconnection.controller.Util;
import com.mobileconnection.data.User;
import com.mobileconnection.database.TariffService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

import static com.mobileconnection.controller.Util.getCurrentUser;
import static com.mobileconnection.controller.tariff.CreateTariff.setEditMode;
import static com.mobileconnection.controller.Util.getCurrentTariff;

public class Tariff implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label tariffTypeLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label costValueLabel;
    @FXML
    private Label daysOfUsingLabel;
    @FXML
    private Label daysOfUsingValueLabel;
    @FXML
    private Label callsLabel;
    @FXML
    private Label callMinutesLabel;
    @FXML
    private Label callMinutesValueLabel;
    @FXML
    private Label callMinutesOnOtherNumbersLabel;
    @FXML
    private Label callMinutesOnOtherNumbersValueLabel;
    @FXML
    private Label callMinutesInRoamingLabel;
    @FXML
    private Label callMinutesInRoamingValueLabel;
    @FXML
    private Label trafficLabel;
    @FXML
    private Label trafficValueLabel;
    @FXML
    private Label freeSmsLabel;
    @FXML
    private Label freeSmsValueLabel;
    @FXML
    private Label appsWithoutTrafficLabel;
    @FXML
    private Label appsWithoutTrafficValueLabel;
    @FXML
    private Label usersNumberLabel;
    @FXML
    private Label usersNumberValueLabel;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Line line;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TariffPackage tariff = getCurrentTariff();

        nameLabel.setText(tariff.getName());
        tariffTypeLabel.setText(tariff.getDaysOfUsing().equals("") ? "Тариф" : "Пакет даних");
        if (tariff.getDaysOfUsing().equals("")){
            daysOfUsingLabel.setVisible(false);
            daysOfUsingValueLabel.setVisible(false);
            rectangle.setVisible(false);
        }
        costValueLabel.setText(String.valueOf(tariff.getCostPerMonth()));
        daysOfUsingValueLabel.setText(String.valueOf(tariff.getDaysOfUsing()));
        callMinutesValueLabel.setText(tariff.getCallMinutesString());
        callMinutesOnOtherNumbersValueLabel.setText(tariff.getCallMinutesOnOtherNumbersString());
        callMinutesInRoamingValueLabel.setText(tariff.getCallMinutesInRoamingString());
        appsWithoutTrafficValueLabel.setText(tariff.getAppsWithoutTraffic());
        trafficValueLabel.setText(tariff.getTrafficString());
        freeSmsValueLabel.setText(tariff.getFreeSmsNumberString());
        usersNumberValueLabel.setText(String.valueOf(tariff.getUsersNumber()));

        User user = getCurrentUser();

        if (!user.hasEditPermission()){
            line.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
        }
    }

    @FXML
    public void onBackButtonClick(){
        Util.setCurrentTariff(null);
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/company.fxml"));
    }

    @FXML
    public void onEditButtonClick(){
        setEditMode(true);
        new Util().setScene(getClass().getResource("create-tariff.fxml"));
    }

    @FXML
    public void onDeleteButtonClick(){
        TariffService delete = new TariffService();
        delete.deleteTariff(getCurrentTariff());
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/company.fxml"));
    }
}
