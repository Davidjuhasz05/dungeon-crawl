package com.codecool.dungeoncrawl.database;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class GameDatabaseDataSource {
    private final String dbName = "dungeon_crawl";
    private final String dbUser = "postgres";
    private final String userDbPassword = "psql";

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

