package com.codecool.dungeoncrawl.database;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;

import java.sql.*;

public class CellDaoJdbc {
    private final GameDatabaseDataSource dataSource;
    private final ActorDaoJdbc actorDaoJdbc;
    private final ItemDaoJdbc itemDaoJdbc;

    public CellDaoJdbc(GameDatabaseDataSource dataSource, ActorDaoJdbc actorDaoJdbc, ItemDaoJdbc itemDaoJdbc) {
        this.dataSource = dataSource;
        this.actorDaoJdbc = actorDaoJdbc;
        this.itemDaoJdbc = itemDaoJdbc;
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
            PreparedStatement statement = conn.prepareStatement("select cellType, actor, item from map where map.x = ? and map.y = ?");
            statement.setInt(1, x);
            statement.setInt(2, y);
            return statement.executeQuery();
        } catch(SQLException e) {
            throw new SQLException("Could not get cell");
        }
    }

    public void saveCells (GameMap map) throws SQLException{

        try (Connection conn = dataSource.connect()) {
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    Cell cell = map.getCell(x, y);
                    saveCell(conn, cell, x, y);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error while saving cell", e);
        }
    }

    public void saveCell(Connection conn, Cell cell, int x, int y) throws SQLException {

            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO map (mapName, x, y, cellType, actor, item) VALUES (?, ?, ?, ?, ?, ?)"
            );

            statement.setString(1, cell.getGameMap().getName());
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setString(4, cell.getType().name());

            if (cell.getActor() != null) {
                int actorId = actorDaoJdbc.saveActor(cell.getActor());
                statement.setInt(5, actorId);
            } else {
                statement.setNull(5, Types.INTEGER);
            }

            if (cell.getItem() != null) {
                int itemId = itemDaoJdbc.saveItem(cell.getItem());
                statement.setInt(6, itemId);
            } else {
                statement.setNull(6, Types.INTEGER);
            }

            statement.executeUpdate();

    }


    public void clearTables() throws SQLException {
        try(Connection conn = dataSource.connect()){
            String sql= "DELETE FROM map";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            itemDaoJdbc.clearItems();
            actorDaoJdbc.clearActors();
        } catch(SQLException e) {
            throw new SQLException("could't clear tables");
        }
    }
}
