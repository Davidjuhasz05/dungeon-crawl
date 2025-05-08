package com.codecool.dungeoncrawl.data.actors.enemies;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.MoveResult;
import com.codecool.dungeoncrawl.data.item.Item;

public abstract class Enemy extends Actor {
    protected int movementRange;

    public Enemy(Cell cell, int movementRange) {
        super(cell, true);
        this.movementRange = movementRange;
    }

    @Override
    public MoveResult evaluateMove(int dx, int dy) {
        Cell nextCell;
        try {
            nextCell = this.getCell().getNeighbor(dx, dy);
        } catch(ArrayIndexOutOfBoundsException e) {
            return MoveResult.BLOCKED;
        }


        if (nextCell.getType().isBlocked()) return MoveResult.BLOCKED;

        Actor target = nextCell.getActor();
        Item targetItem = nextCell.getItem();

        if (target == null && targetItem == null) {
            return MoveResult.MOVE;
        }

        if (target != null && !target.isHostile()) {
            return MoveResult.ATTACK;
        }

        return MoveResult.BLOCKED;
    }

    public int getMovementRange() {
        return movementRange;
    }
}
