package com.mobileconnection.controller.tariff;

import com.mobileconnection.data.TariffPackage;
import com.mobileconnection.controller.Util;
import com.mobileconnection.database.TariffService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static com.mobileconnection.controller.Util.*;

public class CreateTariff implements Initializable {
    private boolean isTariff = true;
    private static boolean isEditMode = false;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label callMinutesLabel;
    @FXML
    private Label callMinutesOnOtherNumbersLabel;
    @FXML
    private Label roamingLabel;
    @FXML
    private Label trafficLabel;
    @FXML
    private Label smsLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label appsWithoutTrafficLabel;
    @FXML
    private Label usersNumberLabel;
    @FXML
    private Label daysUsingLabel;
    @FXML
    private Label tariffErrorLabel;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField costTextField;
    @FXML
    private TextField callMinutesTextField;
    @FXML
    private TextField callMinutesOnOtherNumbersTextField;
    @FXML
    private TextField roamingTextField;
    @FXML
    private TextField trafficTextField;
    @FXML
    private TextField smsTextField;
    @FXML
    private TextField discountTextField;
    @FXML
    private TextField appsWithoutTrafficTextField;
    @FXML
    private TextField usersNumberTextField;
    @FXML
    private TextField daysUsingTextField;

    @FXML
    private Button backButton;
    @FXML
    private Button tariffButton;
    @FXML
    private Button tariffPackageButton;
    @FXML
    private Button createButton;

    @FXML
    private CheckBox callMinutesCheckBox;
    @FXML
    private CheckBox callMinutesOnOtherNumbersCheckBox;
    @FXML
    private CheckBox roamingCheckBox;
    @FXML
    private CheckBox trafficCheckBox;
    @FXML
    private CheckBox smsCheckBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TariffPackage tariff = getCurrentTariff();
        if (isEditMode){
            nameTextField.setText(tariff.getName());
            costTextField.setText(String.valueOf(tariff.getCostPerMonth()));
            if (tariff.getCallMinutes() == 0) callMinutesCheckBox.setSelected(true);
            else callMinutesTextField.setText(String.valueOf(tariff.getCallMinutes()));
            if (tariff.getCallMinutesOnOtherNumbers() == 0) callMinutesOnOtherNumbersCheckBox.setSelected(true);
            else callMinutesOnOtherNumbersTextField.setText(String.valueOf(tariff.getCallMinutesOnOtherNumbers()));
            if (tariff.getCallMinutesInRoaming() == 0) roamingCheckBox.setSelected(true);
            else roamingTextField.setText(String.valueOf(tariff.getCallMinutesInRoaming()));
            if (tariff.getTraffic() == 0) trafficCheckBox.setSelected(true);
            else trafficTextField.setText(String.valueOf(tariff.getTraffic()));
            if (tariff.getFreeSmsNumber() == 0) smsCheckBox.setSelected(true);
            else smsTextField.setText(String.valueOf(tariff.getFreeSmsNumber()));
            discountTextField.setText(String.valueOf(tariff.getDiscount()));
            appsWithoutTrafficTextField.setText(String.valueOf(tariff.getAppsWithoutTraffic()));
            usersNumberTextField.setText(String.valueOf(tariff.getUsersNumber()));
            daysUsingTextField.setText(String.valueOf(tariff.getDaysOfUsing()));

            backButton.setText("Скасувати");
            createButton.setText("Зберегти");
            descriptionLabel.setText("Редагування тарифу");
            if (tariff.getDaysOfUsing() == null)
                tariffPackageButton.setDisable(true);
            else tariffButton.setDisable(true);
        }
        else tariffButton.setDisable(true);
    }

    @FXML
    public void onTariffButtonClick(){
        isTariff = true;
        daysUsingLabel.setVisible(false);
        daysUsingTextField.setVisible(false);
        tariffButton.setDisable(true);
        tariffPackageButton.setDisable(false);
    }
    @FXML
    public void onTariffPackageButtonClick(){
        isTariff = false;
        daysUsingLabel.setVisible(true);
        daysUsingTextField.setVisible(true);
        tariffButton.setDisable(false);
        tariffPackageButton.setDisable(true);
    }

    @FXML
    public void clickCallMinutesCheckBox(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY) {
            callMinutesTextField.setDisable(!callMinutesTextField.isDisable());
        }
    }
    @FXML
    public void clickCallMinutesOnOtherNumbersCheckBox(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY) {
            callMinutesOnOtherNumbersTextField.setDisable(!callMinutesOnOtherNumbersTextField.isDisable());
        }
    }
    @FXML
    public void clickRoamingCheckBox(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY) {
            roamingTextField.setDisable(!roamingTextField.isDisable());
        }
    }
    @FXML
    public void clickTrafficCheckBox(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY) {
            trafficTextField.setDisable(!trafficTextField.isDisable());
        }
    }
    @FXML
    public void clickSmsCheckBox(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY) {
            smsTextField.setDisable(!smsTextField.isDisable());
        }
    }

    @FXML
    public void onBackButtonClick(){
        setEditMode(false);
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/company.fxml"));
    }

    @FXML
    public void onCreateButtonClick() {
        boolean isValid = true;

        String name = nameTextField.getText();
        String cost = costTextField.getText();
        String callMinutes = callMinutesTextField.getText();
        String callMinutesOnOtherNumbers = callMinutesOnOtherNumbersTextField.getText();
        String roaming = roamingTextField.getText();
        String traffic = trafficTextField.getText();
        String sms = smsTextField.getText();
        String discount = discountTextField.getText();
        String appsWithoutTraffic = appsWithoutTrafficTextField.getText();
        String usersNumber = usersNumberTextField.getText();
        String daysUsing = daysUsingTextField.getText();

        if (!isEditMode) {
            TariffPackage[] tariffs = new TariffService().selectTariffs();

            tariffErrorLabel.setVisible(false);

            for (TariffPackage tariff : tariffs) {
                if (tariff.getName().equals(name)) {
                    tariffErrorLabel.setVisible(true);
                    return;
                }
            }
        }

        TariffPackage tariffPackage = new TariffPackage();
        Border borderRed = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        Border borderBlack = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        nameTextField.setBorder(borderBlack);
        appsWithoutTrafficTextField.setBorder(borderBlack);
        daysUsingTextField.setBorder(borderBlack);
        costTextField.setBorder(borderBlack);
        discountTextField.setBorder(borderBlack);
        usersNumberTextField.setBorder(borderBlack);
        callMinutesTextField.setBorder(borderBlack);
        callMinutesOnOtherNumbersTextField.setBorder(borderBlack);
        roamingTextField.setBorder(borderBlack);
        trafficTextField.setBorder(borderBlack);
        smsTextField.setBorder(borderBlack);


        if (!name.equals("")) {
            tariffPackage.setName(name);
        } else {
            isValid = false;
            nameTextField.setBorder(borderRed);
        }
        if (!appsWithoutTraffic.equals("")) {
            tariffPackage.setAppsWithoutTraffic(appsWithoutTraffic);
        } else {
            isValid = false;
            appsWithoutTrafficTextField.setBorder(borderRed);
        }

        if (!isTariff) {
            if (!daysUsing.equals("")) {
                tariffPackage.setDaysOfUsing(daysUsing);
            } else {
                isValid = false;
                daysUsingTextField.setBorder(borderRed);
            }
        } else tariffPackage.setDaysOfUsing("");

        try {
            if (Double.parseDouble(cost) > 0)
                tariffPackage.setCostPerMonth(Double.parseDouble(cost));
        } catch (NumberFormatException e) {
            isValid = false;
            costTextField.setBorder(borderRed);
        }
        try {
            if (Double.parseDouble(discount) > 0)
                tariffPackage.setDiscount(Integer.parseInt(discount));
        } catch (NumberFormatException e) {
            isValid = false;
            discountTextField.setBorder(borderRed);
        }
        try {
            if (Double.parseDouble(usersNumber) > 0)
                tariffPackage.setUsersNumber(Integer.parseInt(usersNumber));
        } catch (NumberFormatException e) {
            isValid = false;
            usersNumberTextField.setBorder(borderRed);
        }

        if (callMinutesCheckBox.isSelected()) tariffPackage.setCallMinutes(-1);
        else {
            try {
                if (Double.parseDouble(callMinutes) > 0)
                    tariffPackage.setCallMinutes(Integer.parseInt(callMinutes));
            } catch (NumberFormatException e) {
                isValid = false;
                callMinutesTextField.setBorder(borderRed);
            }
        }

        if (callMinutesOnOtherNumbersCheckBox.isSelected()) tariffPackage.setCallMinutesOnOtherNumbers(-1);
        else {
            try {
                if (Double.parseDouble(callMinutesOnOtherNumbers) > 0)
                    tariffPackage.setCallMinutesOnOtherNumbers(Integer.parseInt(callMinutesOnOtherNumbers));
            } catch (NumberFormatException e) {
                isValid = false;
                callMinutesOnOtherNumbersTextField.setBorder(borderRed);
            }
        }

        if (roamingCheckBox.isSelected()) tariffPackage.setCallMinutesInRoaming(-1);
        else {
            try {
                if (Double.parseDouble(roaming) > 0)
                    tariffPackage.setCallMinutesInRoaming(Integer.parseInt(roaming));
            } catch (NumberFormatException e) {
                isValid = false;
                roamingTextField.setBorder(borderRed);
            }
        }

        if (trafficCheckBox.isSelected()) tariffPackage.setTraffic(-1);
        else {
            try {
                if (Double.parseDouble(traffic) > 0)
                    tariffPackage.setTraffic(Integer.parseInt(traffic));
            } catch (NumberFormatException e) {
                isValid = false;
                trafficTextField.setBorder(borderRed);
            }
        }

        if (smsCheckBox.isSelected()) tariffPackage.setFreeSmsNumber(-1);
        else {
            try {
                if (Double.parseDouble(sms) > 0)
                    tariffPackage.setFreeSmsNumber(Integer.parseInt(sms));
            } catch (NumberFormatException e) {
                isValid = false;
                smsTextField.setBorder(borderRed);
            }
        }

        if (isValid) {
            if (isEditMode){
                new TariffService().updateTariff(tariffPackage, getCurrentTariff());
                setCurrentTariff(tariffPackage);
                new Util().setScene(getClass().getResource("tariff.fxml"));
            }
            else {
                new TariffService().insertTariff(tariffPackage);
                new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/company.fxml"));
            }
            setEditMode(false);
        }
    }

    public static boolean isEditMode() {
        return isEditMode;
    }

    public static void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }
}