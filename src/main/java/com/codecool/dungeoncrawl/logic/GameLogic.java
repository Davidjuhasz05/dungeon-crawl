package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.MoveResult;
import com.codecool.dungeoncrawl.data.item.Item;
import java.util.List;
import com.codecool.dungeoncrawl.data.actors.Enemy;

import java.util.Random;
import java.util.stream.Collectors;

public class GameLogic {
    private final GameMap map;

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

    private void clearDeadEnemies() {
        List<Enemy> enemies = map.getEnemies().stream()
                .filter(enemy -> enemy.getCell() != null)
                .collect(Collectors.toList());
        map.setEnemies(enemies);
    }

    public void handleEnemiesTurn() {
        clearDeadEnemies();
        Random random = new Random();
        for(Enemy enemy : map.getEnemies()) {
            int dx, dy;
            int movementRange = enemy.getMovementRange();
            boolean isValidMove = false;
            while(!isValidMove) {
                dx = random.nextInt((movementRange + 1) - (movementRange * -1)) + (movementRange * -1);
                dy = random.nextInt((movementRange + 1) - (movementRange * -1)) + (movementRange * -1);
                MoveResult moveResult = enemy.evaluateMove(dx, dy);
                switch(moveResult) {
                    case MOVE:
                        enemy.move(dx, dy);
                        isValidMove = true;
                        break;
                    case ATTACK:
                        enemy.attack(enemy.getNeighbourCellActor(dx, dy));
                        isValidMove = true;
                        break;
                    case BLOCKED:
                        break;
                }
            }
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
