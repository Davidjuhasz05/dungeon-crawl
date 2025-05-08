package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Enemy;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.Torch;
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
        try {
            width = cellDao.getMapWidth() + 1;
            height = cellDao.getMapHeight() + 1;
            mapName = cellDao.getMapName();
        } catch (SQLException e) {
            throw new SQLException("Could not get map data");
        }

        GameMap map = new GameMap(width, height, CellType.EMPTY, mapName);
        try {
            ResultSet results = cellDao.getCells();
            while (results.next()) {
                String cellType = results.getString("cellType");
                int actorId = results.getInt("actor");
                int itemId = results.getInt("item");
                int x = results.getInt("x");
                int y = results.getInt("y");
                Cell cell = map.getCell(x, y);
                cell.setType(CellType.valueOf(cellType));
                if (actorId != 0) {
                    try {
                        Actor actor = actorDao.loadActor(cell, actorId);
                        if (actor instanceof Player) {
                            map.setPlayer((Player) actor);
                        } else {
                            map.addEnemy((Enemy) actor);
                        }
                    } catch (SQLException e) {
                        throw new SQLException("Could not load actor from database");
                    }
                }
                if (itemId != 0) {
                    try {
                       Item item=  itemDao.loadItem(itemId, cell);
                       if(item instanceof Torch){
                           map.addTorch((Torch)item);
                       }
                    } catch (SQLException e) {
                        throw new SQLException("Could not load item from database");
                    }

                }
            }
        } catch (SQLException e) {
            throw new SQLException("Could not get cell from database");
        }
        return map;
    }
}
