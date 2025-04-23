package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell);
        this.movementRange = 1;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
