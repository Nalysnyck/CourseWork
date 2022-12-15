package com.mobileconnection.controller.tariff;

import com.mobileconnection.data.TariffPackage;
import com.mobileconnection.controller.Util;
import com.mobileconnection.database.TariffService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TariffStatistic implements Initializable {
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
        TariffPackage[] tariffArray = new TariffService().selectTariffs();
        int i = 0, otherUsersNumber = 0, allUsersNumber = 0;

        for (TariffPackage tariff : tariffArray)
            allUsersNumber += tariff.getUsersNumber();

        if (allUsersNumber == 0){
            noDataLabel.setVisible(true);
            return;
        }

        for (; (i < 10) && (i < tariffArray.length); i++)
            if (tariffArray[i].getUsersNumber() != 0)
                pieChart.getData().add(new PieChart.Data(tariffArray[i].getName(), tariffArray[i].getUsersNumber()));

        for (; i < tariffArray.length; i++)
            otherUsersNumber += tariffArray[i].getUsersNumber();

        if (otherUsersNumber != 0)
            pieChart.getData().add(new PieChart.Data("Інші", otherUsersNumber));
    }

    @FXML
    public void onBackButtonClick(){
        new Util().setScene(getClass().getResource("/com/mobileconnection/controller/company/company.fxml"));
    }
}
