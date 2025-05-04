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
                    setCellTypes(line.charAt(x), cell, map);
                }
            }
        }
        return map;
    }

    private static void setCellTypes(char c, Cell cell, GameMap map) {
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
            case ',':
                cell.setType(CellType.FLOOR2);
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
                cell.setType(CellType.DOOR);
                break;
            case '0':
                cell.setType(CellType.QUIT);
                break;
            case '1':
                cell.setType(CellType.RETRY);
                break;
            case '+':
                cell.setType(CellType.PROP);
                break;
            case '-':
                cell.setType(CellType.GRASS);
                break;
            default:
                if(c >= 'A' && c <= 'Z') {
                    cell.setType(CellType.valueOf(String.valueOf(c)));
                } else {
                    throw new RuntimeException("Unrecognized character: '" + c + "'");
                }
        }
    }

}
