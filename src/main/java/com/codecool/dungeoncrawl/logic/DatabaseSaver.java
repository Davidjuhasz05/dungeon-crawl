package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.database.CellDaoJdbc;

import java.sql.SQLException;

public class DatabaseSaver {
    private final CellDaoJdbc cellDao;

    public DatabaseSaver(CellDaoJdbc cellDao) {
        this.cellDao = cellDao;
    }

    public void saveMap(GameMap map) {
        try {
            cellDao.clearTables();
            cellDao.saveCells(map);
        } catch (SQLException e) {
            System.out.println("Saving failed: " + e.getMessage());
        }
    }
}
