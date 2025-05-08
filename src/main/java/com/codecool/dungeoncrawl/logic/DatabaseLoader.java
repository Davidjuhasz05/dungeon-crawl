package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Enemy;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.database.ActorDaoJdbc;
import com.codecool.dungeoncrawl.database.CellDaoJdbc;
import com.codecool.dungeoncrawl.database.ItemDaoJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseLoader {
    private final CellDaoJdbc cellDao;
    private final ActorDaoJdbc actorDao;
    private final ItemDaoJdbc itemDao;

    public DatabaseLoader(CellDaoJdbc cellDaoJdbc, ActorDaoJdbc actorDaoJdbc, ItemDaoJdbc itemDaoJdbc) {
        cellDao = cellDaoJdbc;
        actorDao = actorDaoJdbc;
        itemDao = itemDaoJdbc;
    }

    public GameMap loadMap() throws SQLException {
        int width;
        int height;
        String mapName;
        try{
            width = cellDao.getMapWidth();
            height = cellDao.getMapHeight();
            mapName = cellDao.getMapName();
        } catch (SQLException e) {
            throw new SQLException("Could not get map data");
        }

        GameMap map = new GameMap(width, height, CellType.EMPTY, mapName);
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                String cellType;
                String actorId;
                String itemId;
                try{
                    ResultSet results = cellDao.getCellByCoordinates(x, y);
                    cellType = results.getString("cellType");
                    actorId = results.getString("actor");
                    itemId = results.getString("item");
                } catch (SQLException e){
                    throw new SQLException("Could not get cell from database");
                }
                Cell cell = map.getCell(x, y);
                cell.setType(CellType.valueOf(cellType));
                if(actorId != null){
                    try{
                        Actor actor = actorDao.loadActor(cell, actorId);
                        if(actor instanceof Player){
                            map.setPlayer((Player) actor);
                        } else{
                            map.addEnemy((Enemy) actor);
                        }
                    } catch (SQLException e){
                        throw new SQLException("Could not load actor from database");
                    }
                }
                if(itemId != null){
                    try{
                        itemDao.loadItem(itemId, cell);
                    } catch (SQLException e) {
                        throw new SQLException("Could not load item from database");
                    }

                }
            }
        }

        return map;
    }
}
