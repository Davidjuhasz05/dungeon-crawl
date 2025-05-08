package com.codecool.dungeoncrawl.data.actors.enemies;

import com.codecool.dungeoncrawl.data.Cell;

public class Skeleton extends Enemy {
    private static final int MOVEMENT_RANGE = 1;
    private static final int HEALTH_VALUE = 10;
    private static final int DAMAGE_VALUE = 2;

    public Skeleton(Cell cell) {
        super(cell, MOVEMENT_RANGE);
        damage = DAMAGE_VALUE;
        health = HEALTH_VALUE;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
