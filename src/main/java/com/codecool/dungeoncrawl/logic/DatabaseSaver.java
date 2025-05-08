package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.database.ActorDaoJdbc;
import com.codecool.dungeoncrawl.database.CellDaoJdbc;
import com.codecool.dungeoncrawl.database.ItemDaoJdbc;

import java.nio.channels.ConnectionPendingException;
import java.sql.SQLException;

public class DatabaseSaver {
    private final CellDaoJdbc cellDao;
    private final ActorDaoJdbc actorDao;
    private final ItemDaoJdbc itemDao;

    public DatabaseSaver(CellDaoJdbc cellDao, ActorDaoJdbc actorDao, ItemDaoJdbc itemDao) {
        this.cellDao = cellDao;
        this.actorDao = actorDao;
        this.itemDao = itemDao;
    }

    public void saveMap(GameMap map) {
        try {
            cellDao.saveCells(map);
        } catch (SQLException e) {
            System.out.println("Saving failed" + e.getMessage());
        }
    }
}
