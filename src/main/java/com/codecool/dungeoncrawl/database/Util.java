package com.codecool.dungeoncrawl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Util {

    //CURRRENT DELET ORDER, map, actor, item
    public void clearAllTables(List<String> tableNames, GameDatabaseDataSource source) throws SQLException {

        try (Connection connection = source.connect()) {
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
