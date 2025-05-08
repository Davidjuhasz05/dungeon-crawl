package com.codecool.dungeoncrawl.database;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;



public class GameDatabaseDataSource {
    private String dbName = System.getenv("dbName");
    private String dbUser = System.getenv("dbUserName");
    private String userDbPassword = System.getenv("dbPassword");

    private PGSimpleDataSource dataSource;

    public GameDatabaseDataSource() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(userDbPassword);
    }

    public Connection connect() throws SQLException {
        return dataSource.getConnection();
    }


}

