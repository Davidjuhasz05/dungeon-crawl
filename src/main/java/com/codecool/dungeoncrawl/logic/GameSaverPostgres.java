package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;

import java.util.Scanner;

public class GameSaverPostgres {

    public GameSaverPostgres(CellDAO cellDAO, ActorDAO actorDAO) {
        this.cellDAO = cellDAO;
        this.actorDAO = actorDAO;
    }

    public void saveMap(GameMap map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Cell cell = map.getCell(x, y);
                cellDAO.saveCell(cell, x, y);
                if (cell.getActor() != null) {
                    saveActor(cell.getActor());
                }
            }
        }
    }

    private void saveActor(Actor actor) {
        actorDAO.saveActor(actor);
    }

}
