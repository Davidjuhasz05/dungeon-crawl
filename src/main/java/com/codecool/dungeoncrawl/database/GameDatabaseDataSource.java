package com.codecool.dungeoncrawl.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabase {
    String dbName = System.getenv("db_name");
    String dbUser = System.getenv("dbUserName");
    String userDbPasswoed = System.getenv("dbPassword");



    public void getConnection() {
        try {
            connect();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database" + e.getMessage());
        }
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(userDbPasswoed);

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

    public void run() {
        try {
            setup();
        } catch (SQLException throwables) {
            System.err.println("Could not connect to the database.");
            return;
        }

    }

    private void setup() throws SQLException {

    }
}
