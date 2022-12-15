package com.mobileconnection.controller.company;

import com.mobileconnection.data.MobileCompany;
import com.mobileconnection.data.User;
import com.mobileconnection.controller.Util;
import com.mobileconnection.database.MobileCompanyService;
import com.mobileconnection.database.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.mobileconnection.controller.Util.getCurrentUser;
import static com.mobileconnection.controller.Util.setCurrentUser;

public class Main implements Initializable {
    @FXML
    private ListView<String> list;
    @FXML
    private Button addCompanyButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField companyTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button statisticButton;
    @FXML
    private ImageView userIcon;
    @FXML
    private Circle userCircle;
    @FXML
    private Label userLoginLabel;
    @FXML
    private Rectangle rectangle;
    private final User currentUser = getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<MobileCompany> items = FXCollections.observableArrayList(new MobileCompanyService().selectMobileCompanies());

        for (MobileCompany item : items)
            list.getItems().add(item.getName());

        companyTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onAddButtonClick();
            }
        });

        if (currentUser.isAdministrator()) {
            userIcon.setCursor(Cursor.HAND);
            userCircle.setCursor(Cursor.HAND);
        }

        if (!currentUser.hasEditPermission()) {
            addCompanyButton.setVisible(false);
            rectangle.setVisible(false);
        }

        userLoginLabel.setText(currentUser.getLogin() + (currentUser.isAdministrator() ? " / налаштування" : ""));
        userIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/avatar/" + currentUser.getAvatarId() + ".png")).toExternalForm()));
    }

    @FXML
    public void clickItem(MouseEvent event){
        String selectedItem = list.getSelectionModel().getSelectedItem();
        MobileCompany[] companies = new MobileCompanyService().selectMobileCompanies();
        if (event.getButton() == MouseButton.PRIMARY && selectedItem != null)
            for (MobileCompany company : companies)
                if (company.getName().equals(selectedItem)) {
                    Util.setCurrentCompany(company);
                    new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/company.fxml"));
                }
    }

    @FXML
    public void onMouseMoved(){
        userLoginLabel.setVisible(true);
    }
    @FXML
    public void onMouseExited(){
        userLoginLabel.setVisible(false);
    }

    @FXML
    public void onBackButtonClick(){
        User user = getCurrentUser();
        user.setDateLastSeen(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss").format(LocalDateTime.now()));
        new UserService().updateUser(user, getCurrentUser());
        setCurrentUser(null);
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/accounting/log-in.fxml"));
    }

    @FXML
    public void onUserClick(){
        if (currentUser.isAdministrator())
            new Util().setScene(getClass().getResource("/com/mobileconnection/controller/accounting/administration.fxml"));
    }

    @FXML
    public void onAddCompanyButtonClick(){
        addCompanyButton.setVisible(false);
        addButton.setVisible(true);
        companyTextField.setVisible(true);
        cancelButton.setVisible(true);
    }

    @FXML
    public void onAddButtonClick() {
        String name = companyTextField.getText();
        ObservableList<String> newList = list.getItems();
        if (name.equals("")) {
            errorLabel.setText("Така назва неприпустима!");
            companyTextField.setText("");
            errorLabel.setVisible(true);
            return;
        } else if (name.length() > 25) {
            errorLabel.setText("Назва не може мати більше 25 символів!");
            companyTextField.setText("");
            errorLabel.setVisible(true);
            return;
        }
        for (String company : newList) {
            if (company.equals(name)) {
                errorLabel.setText("Така компанія вже існує!");
                companyTextField.setText("");
                errorLabel.setVisible(true);
                return;
            }
        }

        MobileCompany mobileCompany = new MobileCompany(name);

        companyTextField.setText("");
        errorLabel.setVisible(false);
        companyTextField.setVisible(false);
        cancelButton.setVisible(false);
        addButton.setVisible(false);
        addCompanyButton.setVisible(true);

        newList.add(mobileCompany.getName());
        new MobileCompanyService().insertMobileCompany(mobileCompany);
        list.setItems(newList);
    }

    @FXML
    public void onCancelButtonClick() {
        companyTextField.setText("");
        errorLabel.setVisible(false);
        cancelButton.setVisible(false);
        companyTextField.setVisible(false);
        addButton.setVisible(false);
        addCompanyButton.setVisible(true);
    }

    @FXML
    public void onStatisticButtonClick() {
        new Util().setScene(getClass().getResource("company-statistic.fxml"));
    }
}