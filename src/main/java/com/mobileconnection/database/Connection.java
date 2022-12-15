package com.mobileconnection.database;

import java.sql.DriverManager;
import java.sql.SQLException;

class Connection {
    private final String url = "jdbc:postgresql://db.aeemizygyivrzlevraqj.supabase.co:5432/postgres"; // "jdbc:postgresql://localhost:5432/MobileConnection"
    private final String usernameServer = "postgres";
    private final String passwordServer = "XuwzvtOhHcyClVx4"; // "asd345fghA"
    public java.sql.Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(url, usernameServer, passwordServer);
    }
}