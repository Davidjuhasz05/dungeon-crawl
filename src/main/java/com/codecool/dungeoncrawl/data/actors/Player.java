package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell, false);
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public boolean move(int dx, int dy) {
        Cell nextCell  = this.getCell().getNeighbor(dx, dy);
        CellType nextCellType = nextCell.getType();
        if (nextCellType.isPassable()
                && (nextCell.getActor() == null
                || !nextCell.getActor().isHostile())){
            super.move(dx, dy);
        }
        return false;
    }

}
