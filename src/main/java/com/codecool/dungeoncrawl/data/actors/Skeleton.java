package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Skeleton extends Enemy {
    private static final int MOVEMENT_RANGE = 1;
    private static final int DAMAGE_VALUE = 2;

    public Skeleton(Cell cell) {
        super(cell, MOVEMENT_RANGE);
        this.setDamage(DAMAGE_VALUE);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
