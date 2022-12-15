package com.mobileconnection.controller.company;

import com.mobileconnection.data.TariffPackage;
import com.mobileconnection.controller.Util;
import com.mobileconnection.data.User;
import com.mobileconnection.database.MobileCompanyService;
import com.mobileconnection.database.TariffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.mobileconnection.controller.Util.getCurrentUser;
import static com.mobileconnection.controller.Util.setCurrentTariff;

public class Company implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button addTariffButton;
    @FXML
    private Button showStatisticButton;
    @FXML
    private ImageView deleteCompanyButton;
    @FXML
    private Label deleteCompanyLabel;
    @FXML
    private TableView<TariffPackage> table;
    @FXML
    private TableColumn<TariffPackage, String> nameColumn;
    @FXML
    private TableColumn<TariffPackage, Double> priceColumn;
    @FXML
    private TableColumn<TariffPackage, Integer> discountColumn;
    @FXML
    private TableColumn<TariffPackage, String> trafficColumn;
    @FXML
    private TableColumn<TariffPackage, String> callMinutesColumn;
    @FXML
    private TableColumn<TariffPackage, String> callMinutesOnOtherNumbersColumn;
    @FXML
    private TableColumn<TariffPackage, String> roamingColumn;
    @FXML
    private TableColumn<TariffPackage, String> smsColumn;
    @FXML
    private TableColumn<TariffPackage, Integer> usersNumberColumn;
    @FXML
    private Button filtrationButton;
    @FXML
    private Circle costCircle;
    @FXML
    private Circle discountCircle;
    @FXML
    private Circle trafficCircle;
    @FXML
    private Circle minutesCircle;
    @FXML
    private Circle minutesOnOtherNumbersCircle;
    @FXML
    private Circle roamingCircle;
    @FXML
    private Circle smsCircle;
    @FXML
    private Circle usersNumberCircle;
    @FXML
    private CubicCurve costCurve;
    @FXML
    private CubicCurve discountCurve;
    @FXML
    private CubicCurve trafficCurve;
    @FXML
    private CubicCurve minutesCurve;
    @FXML
    private CubicCurve minutesOnOtherNumbersCurve;
    @FXML
    private CubicCurve roamingCurve;
    @FXML
    private CubicCurve smsCurve;
    @FXML
    private CubicCurve numberOfUsersCurve;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Label toLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private TextField toTextField;
    @FXML
    private TextField fromTextField;
    private String costTo, costFrom, discountTo, discountFrom, trafficTo, trafficFrom, minutesTo, minutesFrom, minutesOnOtherNumbersTo, minutesOnOtherNumbersFrom, roamingTo, roamingFrom, smsTo, smsFrom, usersNumberTo, usersNumberFrom;
    private int choice = 0;
    private boolean isFiltering = false;
    private ObservableList<TariffPackage> currentTariffs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(Util.getCurrentCompany().getName());

        currentTariffs = FXCollections.observableArrayList(new TariffService().selectTariffs());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("costPerMonth"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        trafficColumn.setCellValueFactory(new PropertyValueFactory<>("trafficString"));
        callMinutesColumn.setCellValueFactory(new PropertyValueFactory<>("callMinutesString"));
        callMinutesOnOtherNumbersColumn.setCellValueFactory(new PropertyValueFactory<>("callMinutesOnOtherNumbersString"));
        roamingColumn.setCellValueFactory(new PropertyValueFactory<>("callMinutesInRoamingString"));
        smsColumn.setCellValueFactory(new PropertyValueFactory<>("freeSmsNumberString"));
        usersNumberColumn.setCellValueFactory(new PropertyValueFactory<>("usersNumber"));

        table.setItems(currentTariffs);

        searchTextField.setOnKeyTyped(e -> {
                table.setItems(onSearchType());
        });

        User user = getCurrentUser();

        if(!user.hasEditPermission()){
            addTariffButton.setVisible(false);
            rectangle.setVisible(false);
            deleteCompanyButton.setVisible(false);
            deleteCompanyLabel.setVisible(false);
        }
    }

    @FXML
    public void clickItem(MouseEvent event) {
        TariffPackage selectedItem = table.getSelectionModel().getSelectedItem();
        if (event.getButton() == MouseButton.PRIMARY && selectedItem != null) {
            setCurrentTariff(selectedItem);
            new Util().setScene(getClass().getResource("/com/mobileconnection/controller/tariff/tariff.fxml"));
        }
    }

    @FXML
    public void onMouseMoved(){
        deleteCompanyButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/dustbin.png")).toExternalForm()));
        deleteCompanyLabel.setVisible(true);
    }
    @FXML
    public void onMouseExited(){
        deleteCompanyButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/green-dustbin.png")).toExternalForm()));
        deleteCompanyLabel.setVisible(false);
    }

    @FXML
    public void onBackButtonClick(){
        Util.setCurrentCompany(null);
        new Util().setScene(getClass().getResource("main.fxml"));
    }

    private ObservableList<TariffPackage> onSearchType() {
        String searchString = searchTextField.getText();
        ObservableList<TariffPackage> currentItems = currentTariffs, newItems = FXCollections.observableArrayList();

        if (searchString.isEmpty()) return currentItems;

        for (TariffPackage tariffPackage : currentItems)
            if (tariffPackage.getName().toLowerCase().contains(searchString.toLowerCase()))
                newItems.add(tariffPackage);

        return newItems;
    }

    @FXML
    public void onAddTariffButtonClick(){
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/tariff/create-tariff.fxml"));
    }

    @FXML
    public void onShowStatisticButtonClick(){
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/tariff/tariff-statistic.fxml"));
    }

    @FXML
    public void onDeleteCompanyImageViewClick(){
        MobileCompanyService delete = new MobileCompanyService();
        delete.deleteMobileCompany(Util.getCurrentCompany());
        Util.setCurrentCompany(null);
        new Util().setScene(getClass().getResource("main.fxml"));
    }

    @FXML
    public void onFiltrationButtonClick() {
        isFiltering = !isFiltering;

        costCircle.setVisible(isFiltering);
        discountCircle.setVisible(isFiltering);
        trafficCircle.setVisible(isFiltering);
        minutesCircle.setVisible(isFiltering);
        minutesOnOtherNumbersCircle.setVisible(isFiltering);
        roamingCircle.setVisible(isFiltering);
        smsCircle.setVisible(isFiltering);
        usersNumberCircle.setVisible(isFiltering);

        costCurve.setVisible(false);
        discountCurve.setVisible(false);
        trafficCurve.setVisible(false);
        minutesCurve.setVisible(false);
        minutesOnOtherNumbersCurve.setVisible(false);
        roamingCurve.setVisible(false);
        smsCurve.setVisible(false);
        numberOfUsersCurve.setVisible(false);

        costCircle.setFill(Color.web("#ff7878"));
        discountCircle.setFill(Color.web("#ff7878"));
        trafficCircle.setFill(Color.web("#ff7878"));
        minutesCircle.setFill(Color.web("#ff7878"));
        minutesOnOtherNumbersCircle.setFill(Color.web("#ff7878"));
        roamingCircle.setFill(Color.web("#ff7878"));
        smsCircle.setFill(Color.web("#ff7878"));
        usersNumberCircle.setFill(Color.web("#ff7878"));

        if (costCircle.isVisible())
            costTo = costFrom = discountTo = discountFrom = trafficTo = trafficFrom = minutesTo = minutesFrom = minutesOnOtherNumbersTo = minutesOnOtherNumbersFrom = roamingTo = roamingFrom = smsTo = smsFrom = usersNumberTo = usersNumberFrom = "";

        searchTextField.setDisable(isFiltering);
        searchTextField.setText("");
        choice = 0;
        setFieldVisibility(0);
        updateFiltration();
    }

    @FXML
    public void updateFiltration() {
        ObservableList<TariffPackage> newItems = FXCollections.observableArrayList();
        String toText = toTextField.getText(), fromText = fromTextField.getText();
        boolean isTyped = costTo.length() > 0 || costFrom.length() > 0;

        if (choice == 1) {
            costTo = toText;
            costFrom = fromText;
            costCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            costCurve.setVisible(isTyped);
        }else if (choice == 2) {
            discountTo = toText;
            discountFrom = fromText;
            discountCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            discountCurve.setVisible(isTyped);
        }else if (choice == 3) {
            trafficTo = toText;
            trafficFrom = fromText;
            trafficCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            trafficCurve.setVisible(isTyped);
        }else if (choice == 4) {
            minutesTo = toText;
            minutesFrom = fromText;
            minutesCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            minutesCurve.setVisible(isTyped);
        }else if (choice == 5) {
            minutesOnOtherNumbersTo = toText;
            minutesOnOtherNumbersFrom = fromText;
            minutesOnOtherNumbersCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            minutesOnOtherNumbersCurve.setVisible(isTyped);
        }else if (choice == 6) {
            roamingTo = toText;
            roamingFrom = fromText;
            roamingCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            roamingCurve.setVisible(isTyped);
        }else if (choice == 7) {
            smsTo = toText;
            smsFrom = fromText;
            smsCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            smsCurve.setVisible(isTyped);
        }else if (choice == 8) {
            usersNumberTo = toText;
            usersNumberFrom = fromText;
            usersNumberCircle.setFill(Color.web(isTyped ? "#7aff84" : "#ff7878"));
            numberOfUsersCurve.setVisible(isTyped);
        }

        for (TariffPackage tariffPackage : currentTariffs) {
            if (isFiltered(tariffPackage.getCostPerMonth(), costFrom, costTo)) continue;
            if (isFiltered((double)tariffPackage.getDiscount(), discountFrom, discountTo)) continue;
            if (isFiltered((double)tariffPackage.getTraffic(), trafficFrom, trafficTo)) continue;
            if (isFiltered((double)tariffPackage.getCallMinutes(), minutesFrom, minutesTo)) continue;
            if (isFiltered((double)tariffPackage.getCallMinutesOnOtherNumbers(), minutesOnOtherNumbersFrom, minutesOnOtherNumbersTo)) continue;
            if (isFiltered((double)tariffPackage.getCallMinutesInRoaming(), roamingFrom, roamingTo)) continue;
            if (isFiltered((double)tariffPackage.getFreeSmsNumber(), smsFrom, smsTo)) continue;
            if (isFiltered((double)tariffPackage.getUsersNumber(), usersNumberFrom, usersNumberTo)) continue;

            newItems.add(tariffPackage);
        }
        table.setItems(newItems);
    }

    private boolean isFiltered(Double real, String wantedFrom, String wantedTo){
        if (isNumeric(wantedFrom) && isNumeric(wantedTo))
            return !((real > Double.parseDouble(wantedFrom)) && (real < Double.parseDouble(wantedTo)));
        if (isNumeric(wantedFrom))
            return real < Double.parseDouble(wantedFrom);
        if (isNumeric(wantedTo))
            return real > Double.parseDouble(wantedTo);
        return false;
    }

    public static boolean isNumeric(String stringNumber) {
        if (stringNumber == null) return false;
        if (stringNumber.equals("")) return false;
        try {
            Double.parseDouble(stringNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    public void onCostCircleClick(){
        setFieldVisibility(1);
        if (choice != 0){
            fromTextField.setText(costFrom);
            toTextField.setText(costTo);
        }
    }
    @FXML
    public void onDiscountCircleClick(){
        setFieldVisibility(2);
        if (choice != 0){
            fromTextField.setText(discountFrom);
            toTextField.setText(discountTo);
        }
    }
    @FXML
    public void onTrafficCircleClick(){
        setFieldVisibility(3);
        if (choice != 0){
            fromTextField.setText(trafficFrom);
            toTextField.setText(trafficTo);
        }
    }
    @FXML
    public void onMinutesCircleClick(){
        setFieldVisibility(4);
        if (choice != 0){
            fromTextField.setText(minutesFrom);
            toTextField.setText(minutesTo);
        }
    }
    @FXML
    public void onMinutesOnOtherNumbersCircleClick(){
        setFieldVisibility(5);
        if (choice != 0){
            fromTextField.setText(minutesOnOtherNumbersFrom);
            toTextField.setText(minutesOnOtherNumbersTo);
        }
    }
    @FXML
    public void onRoamingCircleClick(){
        setFieldVisibility(6);
        if (choice != 0){
            fromTextField.setText(roamingFrom);
            toTextField.setText(roamingTo);
        }
    }
    @FXML
    public void onSmsCircleClick(){
        setFieldVisibility(7);
        if (choice != 0){
            fromTextField.setText(smsFrom);
            toTextField.setText(smsTo);
        }
    }
    @FXML
    public void onUsersNumberCircleClick(){
        setFieldVisibility(8);
        if (choice != 0){
            fromTextField.setText(usersNumberFrom);
            toTextField.setText(usersNumberTo);
        }
    }
    private void setFieldVisibility(int chosen){
        if (choice == chosen) choice = 0;
        else choice = chosen;

        boolean isVisible = (choice != 0);

        fromTextField.setVisible(isVisible);
        toTextField.setVisible(isVisible);
        fromLabel.setVisible(isVisible);
        toLabel.setVisible(isVisible);
    }
}