package com.mobileconnection.database;

import com.mobileconnection.data.TariffPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.mobileconnection.controller.Util.getCurrentCompany;

public class TariffService {
    public TariffPackage[] selectTariffs() {
        String queryGet = "SELECT * FROM \"Tariff\" WHERE \"id\" IN (SELECT \"TariffID\" FROM \"MobileCompanyTariffs\" WHERE \"MobileCompanyID\" = (SELECT \"id\" FROM \"MobileCompany\" WHERE \"Name\" = ?))";
        TariffPackage[] tariffs = null;
        TariffPackage tempTariff;

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryGet);
            pst.setString(1, getCurrentCompany().getName());
            ResultSet result = pst.executeQuery();

            // записуємо дані в тимчасовий список
            ArrayList<TariffPackage> list = new ArrayList<>();
            while (result.next()) {
                tempTariff = new TariffPackage(result.getString("Name"), result.getDouble("MonthCost"), result.getInt("CallMinutes"), result.getInt("CallMinutesOnOtherNumbers"), result.getInt("CallMinutesInRoaming"), result.getInt("Traffic"), result.getInt("FreeSmsNumber"), result.getInt("Discount"), result.getString("AppsWithoutTraffic"), result.getInt("UsersNumber"), result.getString("UsageDays"));
                list.add(tempTariff);
            }

            // перетворюємо список в масив
            tariffs = new TariffPackage[list.size()];
            list.toArray(tariffs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tariffs;
    }
    public void insertTariff(TariffPackage tariff) {
        String queryGetMaxId = "SELECT MAX(id) FROM \"Tariff\"";
        String queryAdd1 = "INSERT INTO \"Tariff\"(\"id\", \"Name\", \"MonthCost\", \"CallMinutes\", \"CallMinutesOnOtherNumbers\", \"CallMinutesInRoaming\", \"Traffic\", \"FreeSmsNumber\", \"Discount\", \"AppsWithoutTraffic\", \"UsersNumber\", \"UsageDays\") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String queryAdd2 = "INSERT INTO \"MobileCompanyTariffs\"(\"MobileCompanyID\", \"TariffID\") VALUES((SELECT \"id\" FROM \"MobileCompany\" WHERE \"Name\" = ?), ?)";
        String queryUpdate = "UPDATE \"MobileCompany\" SET \"UsersNumber\" = (\"UsersNumber\" + ?) WHERE \"Name\" = ?";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryGetMaxId);
            ResultSet result = pst.executeQuery();

            int id = 0;
            if (result.next()) {
                id = result.getInt(1) + 1;
            }

            // додаємо тариф в таблицю тарифів
            pst = connection.prepareStatement(queryAdd1);

            pst.setInt(1, id);
            pst.setString(2, tariff.getName());
            pst.setDouble(3, tariff.getCostPerMonth());
            pst.setInt(4, tariff.getCallMinutes());
            pst.setInt(5, tariff.getCallMinutesOnOtherNumbers());
            pst.setInt(6, tariff.getCallMinutesInRoaming());
            pst.setInt(7, tariff.getTraffic());
            pst.setInt(8, tariff.getFreeSmsNumber());
            pst.setDouble(9, tariff.getDiscount());
            pst.setString(10, tariff.getAppsWithoutTraffic());
            pst.setInt(11, tariff.getUsersNumber());
            pst.setString(12, tariff.getDaysOfUsing());

            pst.executeUpdate();

            // додавання зв'язку із компанією
            pst = connection.prepareStatement(queryAdd2);

            pst.setString(1, getCurrentCompany().getName());
            pst.setInt(2, id);

            pst.executeUpdate();

            // оновлення кількості користувачів у компанії
            pst = connection.prepareStatement(queryUpdate);

            pst.setInt(1, tariff.getUsersNumber());
            pst.setString(2, getCurrentCompany().getName());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTariff(TariffPackage updated, TariffPackage old) {
        String queryTariffUpdate = "UPDATE \"Tariff\" SET \"Name\" = ?, \"MonthCost\" = ?, \"CallMinutes\" = ?, \"CallMinutesOnOtherNumbers\" = ?, \"CallMinutesInRoaming\" = ?, \"Traffic\" = ?, \"FreeSmsNumber\" = ?, \"Discount\" = ?, \"AppsWithoutTraffic\" = ?, \"UsersNumber\" = ?, \"UsageDays\" = ? WHERE \"Name\" = ?";
        String queryCompanyUpdate = "UPDATE \"MobileCompany\" SET \"UsersNumber\" = \"UsersNumber\" + ? WHERE \"id\" = (SELECT \"MobileCompanyID\" FROM \"MobileCompanyTariffs\" WHERE \"TariffId\" = (SELECT \"id\" FROM \"Tariff\" WHERE \"Name\" = ?))";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryTariffUpdate);

            pst.setString(1, updated.getName());
            pst.setDouble(2, updated.getCostPerMonth());
            pst.setInt(3, updated.getCallMinutes());
            pst.setInt(4, updated.getCallMinutesOnOtherNumbers());
            pst.setInt(5, updated.getCallMinutesInRoaming());
            pst.setInt(6, updated.getTraffic());
            pst.setInt(7, updated.getFreeSmsNumber());
            pst.setDouble(8, updated.getDiscount());
            pst.setString(9, updated.getAppsWithoutTraffic());
            pst.setInt(10, updated.getUsersNumber());
            pst.setString(11, updated.getDaysOfUsing());
            pst.setString(12, old.getName());

            pst.executeUpdate();

            pst = connection.prepareStatement(queryCompanyUpdate);

            pst.setInt(1, updated.getUsersNumber() - old.getUsersNumber());
            pst.setString(2, old.getName());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTariff(TariffPackage tariff) {
        String queryDelete1 = "DELETE FROM \"MobileCompanyTariffs\" WHERE \"id\" = (SELECT \"id\" FROM \"MobileCompany\" WHERE \"Name\" = ?))";
        String queryDelete3 = "DELETE FROM \"Tariff\" WHERE \"Name\" = ?";
        String queryUpdate = "UPDATE \"MobileCompany\" SET \"UsersNumber\" = (\"UsersNumber\" - ?) WHERE \"Name\" = ?";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryDelete1);
            pst.setString(1, tariff.getName());
            pst.executeUpdate();

            PreparedStatement pst2 = connection.prepareStatement(queryUpdate);
            pst2.setInt(1, tariff.getUsersNumber());
            pst2.setString(2, tariff.getName());
            pst2.executeUpdate();

            PreparedStatement pst3 = connection.prepareStatement(queryDelete3);
            pst3.setString(1, tariff.getName());
            pst3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
