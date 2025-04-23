package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell, boolean isHostile) {
        super(cell, isHostile);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
