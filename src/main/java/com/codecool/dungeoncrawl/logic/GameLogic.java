package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.item.Item;
import java.util.List;
import com.codecool.dungeoncrawl.data.actors.Enemy;

import java.util.Random;

public class GameLogic {
    private GameMap map;

    public GameLogic() {
        this.map = MapLoader.loadMap();
    }

    public double getMapWidth() {
        return map.getWidth();
    }

    public double getMapHeight() {
        return map.getHeight();
    }

    public void setup() {
    }

    public void moveEnemies() {
        Random random = new Random();
        for(Enemy enemy : map.getEnemies()) {
            int dx, dy;
            int movementRange = enemy.getMovementRange();
            do {
                dx = random.nextInt((movementRange + 1) - (movementRange * -1)) + (movementRange * -1);
                dy = random.nextInt((movementRange + 1) - (movementRange * -1)) + (movementRange * -1);
            } while(!enemy.move(dx, dy));
        }
    }

    public Cell getCell(int x, int y) {
        return map.getCell(x, y);
    }

    public String getPlayerHealth() {
        return Integer.toString(map.getPlayer().getHealth());
    }

    public List<Item> getPlayerInventory(){
        return map.getPlayer().getInventory();
    }

    public GameMap getMap() {
        return map;
    }
}
