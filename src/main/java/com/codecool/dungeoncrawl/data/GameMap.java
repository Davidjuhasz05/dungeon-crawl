package com.codecool.dungeoncrawl.data;

import com.codecool.dungeoncrawl.data.actors.Enemy;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.item.Torch;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final String name;
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final List<Enemy> enemies = new ArrayList<>();
    private Player player;
    private final List<Torch> torches = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType, String name) {
        this.name = name;
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public List<Torch> getTorches() {
        return torches;
    }

    public void addTorch(Torch torch) {
        this.torches.add(torch);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public void addEnemy(Skeleton enemy) {
        this.enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies.clear();
        this.enemies.addAll(enemies);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
