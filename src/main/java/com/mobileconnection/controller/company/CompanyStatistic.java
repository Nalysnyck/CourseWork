package com.mobileconnection.controller.company;

import com.mobileconnection.data.MobileCompany;
import com.mobileconnection.controller.Util;
import com.mobileconnection.database.MobileCompanyService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyStatistic implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Label statisticLabel;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label noDataLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MobileCompany[] companies = new MobileCompanyService().selectMobileCompanies();
        int i = 0, otherUsersNumber = 0, allUsersNumber = 0;

        for (MobileCompany company : companies)
            allUsersNumber += company.getUsersNumber();

        if (allUsersNumber == 0){
            noDataLabel.setVisible(true);
            return;
        }

        for (; (i < 10) && (i < companies.length); i++)
            if (companies[i].getUsersNumber() != 0)
                pieChart.getData().add(new PieChart.Data(companies[i].getName(), companies[i].getUsersNumber()));

        for (; i < companies.length; i++)
            otherUsersNumber += companies[i].getUsersNumber();

        if (otherUsersNumber != 0)
            pieChart.getData().add(new PieChart.Data("Інші", otherUsersNumber));
    }

    @FXML
    public void onBackButtonClick(){
        new Util().setScene(getClass().getResource("main.fxml"));
    }
}
