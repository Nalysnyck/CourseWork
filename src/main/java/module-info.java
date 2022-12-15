module com.mobilecompany {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.mobileconnection.controller;
    exports com.mobileconnection.data;
    exports com.mobileconnection;

    opens com.mobileconnection.controller to javafx.fxml;
    opens com.mobileconnection.data to javafx.fxml;
    opens com.mobileconnection to javafx.fxml;
    exports com.mobileconnection.controller.accounting;
    opens com.mobileconnection.controller.accounting to javafx.fxml;
    exports com.mobileconnection.controller.company;
    opens com.mobileconnection.controller.company to javafx.fxml;
    exports com.mobileconnection.controller.tariff;
    opens com.mobileconnection.controller.tariff to javafx.fxml;
}