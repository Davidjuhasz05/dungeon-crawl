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
           int finalResult=0;
           ResultSet result  = conn.prepareStatement("Select max(x) from map").executeQuery();
           if(result.next()){
               finalResult=result.getInt(1);
           }
            return finalResult;
        } catch(SQLException e) {
            throw new SQLException("Could not get map width");
        }
    }

    public int getMapHeight() throws SQLException {
        try(Connection conn = dataSource.connect()){
            ResultSet result=  conn.createStatement()
                     .executeQuery("select max(y) from map");
            if(result.next()){
                return result.getInt(1);
            }
            throw new SQLException();
        } catch(SQLException e) {
            throw new SQLException("Could not get map height");
        }
    }

    public String getMapName() throws SQLException {
        try(Connection conn = dataSource.connect()){
            ResultSet result= conn.createStatement()
                    .executeQuery("select mapname from map limit 1");
            if(result.next()){
                return result.getString(1);
            }
            throw new SQLException();
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
            actorDaoJdbc.clearActors();
            itemDaoJdbc.clearItems();
        } catch(SQLException e) {
            throw new SQLException("Could not clear tables");
        }
    }
}
