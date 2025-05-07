package com.codecool.dungeoncrawl.database;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.util.List;

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

    // Clear all the specified tables (map, item, actor)
    public void clearAllTables(List<String> tableNames) throws SQLException {

        try (Connection connection = connect()) {
            for (String table : tableNames) {
                String sql = "DELETE FROM " + table;
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error clearing tables", e);
        }
    }
}

