package com.mobileconnection.database;

import com.mobileconnection.data.MobileCompany;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MobileCompanyService {
    public MobileCompany[] selectMobileCompanies() {
        String query = "SELECT * FROM \"MobileCompany\" ORDER BY \"UsersNumber\" DESC";
        MobileCompany[] companies = null;
        MobileCompany tempCompany;

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            // записуємо дані в тимчасовий список
            ArrayList<MobileCompany> list = new ArrayList<>();
            while (result.next()) {
                tempCompany = new MobileCompany(result.getString("Name"));
                tempCompany.setUsersNumber(result.getInt("UsersNumber"));
                list.add(tempCompany);
            }

            // перетворюємо список в масив
            companies = new MobileCompany[list.size()];
            list.toArray(companies);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }
    public void insertMobileCompany(MobileCompany company) {
        String queryGetMaxId = "SELECT MAX(id) FROM \"MobileCompany\"";
        String query = "INSERT INTO \"MobileCompany\"(\"id\", \"Name\", \"UsersNumber\") VALUES(?, ?, 0)";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryGetMaxId);
            ResultSet result = pst.executeQuery();

            int id = 0;
            if (result.next())
                id = result.getInt(1) + 1;

            pst = connection.prepareStatement(query);

            pst.setInt(1, id);
            pst.setString(2, company.getName());

            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteMobileCompany(MobileCompany company) {
        String queryGetTariffsId = "SELECT \"TariffID\" FROM \"MobileCompanyTariffs\" WHERE \"MobileCompanyID\" = (SELECT \"id\" FROM \"MobileCompany\" WHERE \"Name\" = ?)";
        String queryDeleteTariffsConnections = "DELETE FROM \"MobileCompanyTariffs\" WHERE \"MobileCompanyID\" = (SELECT \"id\" FROM \"MobileCompany\" WHERE \"Name\" = ?)";
        String queryDeleteTariffs = "DELETE FROM \"Tariff\" WHERE \"id\" = ?";
        String queryDeleteCompany = "DELETE FROM \"MobileCompany\" WHERE \"Name\" = ?";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryGetTariffsId);
            pst.setString(1, company.getName());
            ResultSet result = pst.executeQuery();

            List<Integer> tariffIds = new ArrayList<>();
            while (result.next()) {
                tariffIds.add(result.getInt("TariffID"));
            }

            PreparedStatement pst1 = connection.prepareStatement(queryDeleteTariffsConnections);
            pst1.setString(1, company.getName());
            pst1.executeUpdate();

            PreparedStatement pst2;
            while (!tariffIds.isEmpty()) {
                pst2 = connection.prepareStatement(queryDeleteTariffs);
                pst2.setInt(1, tariffIds.get(0));
                pst2.executeUpdate();
                tariffIds.remove(0);
            }

            PreparedStatement pst3 = connection.prepareStatement(queryDeleteCompany);
            pst3.setString(1, company.getName());
            pst3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
