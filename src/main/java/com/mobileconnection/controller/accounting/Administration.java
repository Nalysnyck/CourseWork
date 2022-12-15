package com.mobileconnection.controller.accounting;

import com.mobileconnection.data.User;
import com.mobileconnection.controller.Util;
import com.mobileconnection.database.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.mobileconnection.controller.Util.getCurrentUser;
import static com.mobileconnection.controller.Util.setCurrentUser;

public class Administration implements Initializable {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> administratorColumn;
    @FXML
    private TableColumn<User, String> editPermissionColumn;
    @FXML
    private TableColumn<User, String> dateLastSeenColumn;
    @FXML
    private ImageView userIcon;
    @FXML
    private Label nameLabel;
    @FXML
    private Button editPermissionButton;
    @FXML
    private Button administratorButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    @FXML
    private Label isOnlineLabel;
    @FXML
    private Label registrationDateLabel;
    @FXML
    private Circle isOnlineCircle;

    private final User currentUser = getCurrentUser();
    private User selectedUser = currentUser, updatedUser = currentUser;
    private final LinearGradient gradientGreen = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#77ff70")), new Stop(1, Color.web("#3decff"))), gradientRed = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#ff6f6f")), new Stop(1, Color.web("#ffbf3e")));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<User> selectedItems = FXCollections.observableArrayList(new UserService().selectUsers());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        administratorColumn.setCellValueFactory(new PropertyValueFactory<>("isAdministratorString"));
        editPermissionColumn.setCellValueFactory(new PropertyValueFactory<>("hasEditPermissionString"));
        dateLastSeenColumn.setCellValueFactory(new PropertyValueFactory<>("dateLastSeen"));

        userTable.setItems(selectedItems);

        updateUserDescription();
    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && selectedUser != null) {
            selectedUser = updatedUser = userTable.getSelectionModel().getSelectedItem();
            updateUserDescription();
        }
    }

    private void updateUserDescription(){
        nameLabel.setText(selectedUser.getLogin());
        isOnlineLabel.setText(selectedUser.getDateLastSeenDifference());
        editPermissionButton.setBackground(Background.fill(selectedUser.hasEditPermission() ? gradientGreen : gradientRed));
        administratorButton.setBackground(Background.fill(selectedUser.isAdministrator() ? gradientGreen : gradientRed));
        registrationDateLabel.setText("Дата реєстрації: " + selectedUser.getDateRegistration());
        userIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/avatar/" + selectedUser.getAvatarId() + ".png")).toExternalForm()));
        isOnlineCircle.setFill(Color.web(selectedUser.getDateLastSeen().equals("") ? "#77ff70" : "#ff6f6f"));

        if (currentUser.isAdministrator() && !(selectedUser.getLogin().equals(currentUser.getLogin()))) {
            editPermissionButton.setDisable(false);
            administratorButton.setDisable(false);
        } else {
            editPermissionButton.setDisable(true);
            administratorButton.setDisable(true);
        }
    }

    @FXML
    public void onBackButtonClick(){
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/main.fxml"));
    }

    @FXML
    public void onEditPermissionButtonClick(){
        updatedUser.setHasEditPermission(!(updatedUser.hasEditPermission()));
        editPermissionButton.setBackground(Background.fill(selectedUser.hasEditPermission() ? gradientGreen : gradientRed));
    }

    @FXML
    public void onAdministratorButtonClick(){
        updatedUser.setAdministrator(!(updatedUser.isAdministrator()));
        administratorButton.setBackground(Background.fill(selectedUser.isAdministrator() ? gradientGreen : gradientRed));
    }

    @FXML
    public void onSaveButtonClick(){
        if (!(selectedUser.isAdministrator()))
            return;
        if (selectedUser.isAdministrator() == updatedUser.isAdministrator() && updatedUser.isAdministrator() == selectedUser.hasEditPermission() == updatedUser.hasEditPermission()) {
            return;
        }
        new UserService().updateUser(updatedUser, selectedUser);
        ObservableList<User> selectedItems = userTable.getItems();
        selectedItems.set(selectedItems.indexOf(selectedUser), updatedUser);
        userTable.setItems(selectedItems);
        if (selectedUser.getLogin().equals(getCurrentUser().getLogin()))
            setCurrentUser(updatedUser);
    }
}