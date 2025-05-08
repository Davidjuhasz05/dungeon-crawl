package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.MoveResult;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.item.Item;

import java.sql.SQLException;
import java.util.List;
import com.codecool.dungeoncrawl.data.actors.Enemy;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;

import java.util.Random;
import java.util.stream.Collectors;


public class GameLogic {
    private GameMap map;
    private static final int MAX_STEP_RETRIES = 10;
    private final List<String> mapPaths = List.of("/gameover.txt","/dungeon.txt", "/dungeon2.txt");
    private int currentMapIndex = 1;
    private final Random random = new Random();
    private final CellDaoJdbc cellDaoJdbc = new CellDaoJdbc(/*DataSource*/);
    private final DatabaseLoader loader = new DatabaseLoader(cellDaoJdbc);

    public GameLogic() {
        this.map = MapLoader.loadMap(mapPaths.get(currentMapIndex++));
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
        for(Enemy enemy : map.getEnemies()) {
            int dx, dy;
            int movementRange = enemy.getMovementRange();
            int retries = 0;
            boolean isValidMove = false;
            while(!isValidMove && ++retries != MAX_STEP_RETRIES) {
                dx = random.nextInt(2 * movementRange + 1) - movementRange;
                dy = random.nextInt(2 * movementRange + 1) - movementRange;
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
        if (map.getPlayer().getHealth() <= 0 || map.getPlayer().getCell() == null) {
            setGameOver();
        }

    }

    public void loadSave(){
        try{
            GameMap savedMap = loader.loadMap();
            currentMapIndex = mapPaths.indexOf(savedMap.getName());
            map = savedMap;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public Cell getCell(int x, int y) {
        return map.getCell(x, y);
    }

    public int getPlayerHealth() {
      return map.getPlayer().getHealth();
    }

    public boolean isVisibleForPlayer(Cell cell) {
        return map.getPlayer().isVisible(cell);
    }

    public boolean isVisibleForTorch(Cell cell) {
        return map.getTorches().stream()
                .anyMatch(t -> t.isVisible(cell));
    }

    public List<Item> getPlayerInventory(){
        return map.getPlayer().getInventory();
    }

    public Weapon getPlayerWeapon(){
        return map.getPlayer().getWeapon();
    }

    public GameMap getMap() {
        return map;
    }

    public int getCurrentMapIndex() {
        return currentMapIndex;
    }

    public void setNextMap() {
        Player currentPlayer = map.getPlayer();
        this.map = MapLoader.loadMap(mapPaths.get(currentMapIndex++));
        Player newPlayer = map.getPlayer();
        currentPlayer.setCell(newPlayer.getCell());
        newPlayer.getCell().setActor(null);
        newPlayer.setCell(null);
        currentPlayer.getCell().setActor(currentPlayer);
        map.setPlayer(currentPlayer);
    }

    public void handleNextTurn() {
        Player player = map.getPlayer();
        Cell playerPos = player.getCell();
        if(playerPos.getType().equals(CellType.DOOR) && player.hasKey()) {
            setNextMap();
        } else {
            handleEnemiesTurn();
        }
    }

    public void setGameOver(){
        currentMapIndex = 0;
        this.map = MapLoader.loadMap(mapPaths.get(currentMapIndex));
    }

}
