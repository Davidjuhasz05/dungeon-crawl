package com.codecool.dungeoncrawl.database;

import java.sql.*;

public class CellDaoJdbc {
    private final GameDatabaseDataSource dataSource;

    public CellDaoJdbc(GameDatabaseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getMapWidth() throws SQLException {
        try(Connection conn = dataSource.connect()){
            return conn.createStatement()
                    .executeQuery("select max(x) from map")
                    .getInt(1);
        } catch(SQLException e) {
            throw new SQLException("Could not get map width");
        }
    }

    public int getMapHeight() throws SQLException {
        try(Connection conn = dataSource.connect()){
            return conn.createStatement()
                    .executeQuery("select max(y) from map")
                    .getInt(1);
        } catch(SQLException e) {
            throw new SQLException("Could not get map height");
        }
    }

    public String getMapName() throws SQLException {
        try(Connection conn = dataSource.connect()){
            return conn.createStatement()
                    .executeQuery("select mapname from map limit 1")
                    .getString(1);
        } catch(SQLException e) {
            throw new SQLException("Could not get map name");
        }
    }

    public ResultSet getCells() throws SQLException {
        try(Connection conn = dataSource.connect()){
            PreparedStatement statement = conn.prepareStatement("select celltype, actor, item, x, y from map");
            return statement.executeQuery();
        } catch(SQLException e) {
            throw new SQLException("Could not get cells");
        }
    }
}
