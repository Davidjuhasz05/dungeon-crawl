package com.codecool.dungeoncrawl.ui.keyeventhandler;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

public class Util {
    public static boolean checkIfValidMove(KeyEvent event, GameMap map) {
        int x, y;
        switch (event.getCode()) {
            case UP:
                x = map.getPlayer().getX();
                y = map.getPlayer().getY() - 1;
                break;
            case DOWN:
                x = map.getPlayer().getX();
                y = map.getPlayer().getY() + 1;
                break;
            case LEFT:
                x = map.getPlayer().getX() - 1;
                y = map.getPlayer().getY();
                break;
            case RIGHT:
                x = map.getPlayer().getX() + 1;
                y = map.getPlayer().getY();
                break;
            default:
                return false;
        }
        Cell moveToCell = map.getCell(x, y);
        CellType type = moveToCell.getType();
        return type != CellType.EMPTY
                && type != CellType.WALL
                && (moveToCell.getActor() == null || !Objects.equals(moveToCell.getActor().getTileName(), "skeleton"));
    }
}

