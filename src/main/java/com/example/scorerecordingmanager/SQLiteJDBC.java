package com.example.scorerecordingmanager;

import java.sql.*;

public final class SQLiteJDBC {
    private static final String url = "jdbc:sqlite:src/main/resources/com/example/scorerecordingmanager/dataBase/sqlite.db";
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }


}
