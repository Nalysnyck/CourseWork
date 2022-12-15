package com.mobileconnection.database;

import com.mobileconnection.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    public User[] selectUsers(){
        String query = "SELECT * FROM \"User\"";
        User[] users = null;
        User tempUser;

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();

            // записуємо дані в тимчасовий список
            ArrayList<User> list = new ArrayList<>();
            while (result.next()) {
                tempUser = new User(result.getString("Login"), result.getString("Password"), result.getBoolean("IsAdministrator"), result.getBoolean("HasEditPermission"), result.getString("DateRegistration"), result.getString("DateLastSeen"), result.getInt("AvatarId"));
                list.add(tempUser);
            }

            // перетворюємо список в масив
            users = new User[list.size()];
            list.toArray(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User selectUser(String name){
        String query = "SELECT * FROM \"User\" WHERE \"Login\" = ?";
        User user = null;

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, name);
            ResultSet result = pst.executeQuery();

            if (result.next())
                user = new User(result.getString("Login"), result.getString("Password"), result.getBoolean("IsAdministrator"), result.getBoolean("HasEditPermission"), result.getString("DateRegistration"), result.getString("DateLastSeen"), result.getInt("AvatarId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    public void insertUser(User user) {
        String query = "INSERT INTO \"User\"(\"Login\", \"Password\", \"IsAdministrator\", \"HasEditPermission\", \"DateRegistration\", \"DateLastSeen\", \"AvatarId\") VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPassword());
            pst.setBoolean(3, user.isAdministrator());
            pst.setBoolean(4, user.hasEditPermission());
            pst.setString(5, user.getDateRegistration());
            pst.setString(6, user.getDateLastSeen());
            pst.setInt(7, user.getAvatarId());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(User updated, User old) {
        String queryUserUpdate = "UPDATE \"User\" SET \"Login\" = ?, \"Password\" = ?, \"IsAdministrator\" = ?, \"HasEditPermission\" = ?, \"DateRegistration\" = ?, \"DateLastSeen\" = ?, \"AvatarId\" = ? WHERE \"Login\" = ?";

        try (java.sql.Connection connection = new Connection().getDatabaseConnection()) {
            PreparedStatement pst = connection.prepareStatement(queryUserUpdate);

            pst.setString(1, updated.getLogin());
            pst.setString(2, updated.getPassword());
            pst.setBoolean(3, updated.isAdministrator());
            pst.setBoolean(4, updated.hasEditPermission());
            pst.setString(5, updated.getDateRegistration());
            pst.setString(6, updated.getDateLastSeen());
            pst.setInt(7, updated.getAvatarId());
            pst.setString(8, old.getLogin());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
