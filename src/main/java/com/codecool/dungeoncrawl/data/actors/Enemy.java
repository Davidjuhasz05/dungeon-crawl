package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.item.Item;

public abstract class Enemy extends Actor {
  protected int movementRange;

  public Enemy(Cell cell) {
    super(cell, true);
  }

  @Override
  public MoveResult evaluateMove(int dx, int dy) {
    Cell nextCell = this.getCell().getNeighbor(dx, dy);

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
