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

    public ResultSet getCellByCoordinates(int x, int y) throws SQLException {
        try(Connection conn = dataSource.connect()){
            PreparedStatement statement = conn.prepareStatement("select celltype, actor, item from map where map.x = ? and map.y = ?");
            statement.setInt(1, x);
            statement.setInt(2, y);
            return statement.executeQuery();
        } catch(SQLException e) {
            throw new SQLException("Could not get cell");
        }
    }
}
