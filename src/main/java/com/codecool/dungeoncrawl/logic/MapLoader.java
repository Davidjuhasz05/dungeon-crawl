package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.item.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapPath) {
        InputStream is = MapLoader.class.getResourceAsStream(mapPath);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    checkCellTypes(line.charAt(x), cell, map);
                }
            }
        }
        return map;
    }

    private static void checkCellTypes(char c, Cell cell, GameMap map) {
        switch (c) {
            case ' ':
                cell.setType(CellType.EMPTY);
                break;
            case '#':
                cell.setType(CellType.WALL);
                break;
            case '.':
                cell.setType(CellType.FLOOR);
                break;
            case 's':
                cell.setType(CellType.FLOOR);
                map.addEnemy(new Skeleton(cell));
                break;
            case 'w':
                cell.setType(CellType.FLOOR);
                map.addItem(new Weapon(cell));
                break;
            case 'a':
                cell.setType(CellType.FLOOR);
                map.addItem(new Armor(cell));
                break;
            case 'k':
                cell.setType(CellType.FLOOR);
                map.addItem(new Key(cell, 1));
                break;
            case 'p':
                cell.setType(CellType.FLOOR);
                map.addItem(new Potion(cell));
                break;
            case '@':
                cell.setType(CellType.FLOOR);
                map.setPlayer(new Player(cell));
                break;
            case 'd':
                cell.setType(CellType.EXIT);
                break;
            case 'G':
                cell.setType(CellType.WALL);
                break;
            case 'A':
                cell.setType(CellType.WALL);
                break;
            case 'M':
                cell.setType(CellType.WALL);
                break;
            case 'E':
                cell.setType(CellType.WALL);
                break;
            case 'O':
                cell.setType(CellType.WALL);
                break;
            case 'V':
                cell.setType(CellType.WALL);
                break;
            case 'R':
                cell.setType(CellType.WALL);
                break;
            default:
                throw new RuntimeException("Unrecognized character: '" + c + "'");
        }
    }

}
