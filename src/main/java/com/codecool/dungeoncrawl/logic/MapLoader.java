package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.enemies.Golem;
import com.codecool.dungeoncrawl.data.actors.enemies.Skeleton;
import com.codecool.dungeoncrawl.data.actors.enemies.Spider;
import com.codecool.dungeoncrawl.data.item.*;
import com.codecool.dungeoncrawl.data.item.weapon.Sword;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class MapLoader {
    private static final Random RANDOM = new Random();
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
            case 'v':
                cell.setType(CellType.FLOOR);
                map.addEnemy(new Spider(cell));
                break;
            case 'g':
                cell.setType(CellType.FLOOR);
                map.addEnemy(new Golem(cell));
                break;
            case 'w':
                cell.setType(CellType.FLOOR);
                new Sword(cell);
                break;
            case 'a':
                cell.setType(CellType.FLOOR);
                new Armor(cell);
                break;
            case 'k':
                cell.setType(CellType.FLOOR);
                new Key(cell);
                break;
            case 'p':
                cell.setType(CellType.FLOOR);
                new HealthPotion(cell);
                break;
            case 't':
                cell.setType(CellType.FLOOR);
                map.addTorch(new Torch(cell));
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
            case '*':
                cell.setType(CellType.PROP2);
                break;
            case '-':
                cell.setType(CellType.GRASS);
                break;
            case ':':
                cell.setType(CellType.TALL_GRASS);
                break;
            case 'o':
                int randomNumber = RANDOM.nextInt(2);
                cell.setType(randomNumber == 0 ? CellType.CANOPY1 : CellType.CANOPY2);
                break;
            case 'i':
                cell.setType(CellType.TRUNK1);
                break;
            case '|':
                cell.setType(CellType.FOREST_WALL_LEFT);
                break;
            case '/':
                cell.setType(CellType.FOREST_WALL_RIGHT);
                break;
            case '{':
                cell.setType(CellType.FOREST_WALL_TOP);
                break;
            case '}':
                cell.setType(CellType.FOREST_WALL_BOTTOM);
                break;
            case '<':
                cell.setType(CellType.FOREST_WALL_TOP_LEFT);
                break;
            case '>':
                cell.setType(CellType.FOREST_WALL_CORNER_1);
                break;
            case '[':
                cell.setType(CellType.FOREST_WALL_CORNER_2);
                break;
            case ']':
                cell.setType(CellType.FOREST_WALL_BOTTOM_RIGHT);
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
