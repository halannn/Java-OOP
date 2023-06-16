package com.kelompok1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {
    private static final String url = "jdbc:mysql://"
            + App.dotenv.get("MYSQL_HOST", "localhost") + ":"
            + App.dotenv.get("MYSQL_PORT", "3306") + "/"
            + App.dotenv.get("MYSQL_DB", "tubes_pbo_sia");
    private static final String user = App.dotenv.get("MYSQL_USERNAME", "root");
    private static final String password = App.dotenv.get("MYSQL_PASSWORD", "");

    private static Connection conn;

    public static void loadJDBCDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    public static void disconnect() throws SQLException {
        conn.close();
    }

    public static PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }
}
