package com.example.scorerecordingmanager;

import java.sql.*;
import java.util.Objects;

//database connection class
public final class SQLiteJDBC {
    private static final String url = "jdbc:sqlite:" + Objects.requireNonNull(SQLiteJDBC.class.getResource("/com/example/scorerecordingmanager/dataBase/sqlite.db")).getFile();
private static Connection connection;
    public static Connection getConnection(){
        if(connection==null){
            connect();
        }
        return connection;
    }

    private static void connect() {
        try {
            connection = DriverManager.getConnection(url);
            LogManager.getLogger().info("SQLite was connected");
        } catch (SQLException e) {
            LogManager.getLogger().error(e.getMessage(),e);
        } 
    }
}
