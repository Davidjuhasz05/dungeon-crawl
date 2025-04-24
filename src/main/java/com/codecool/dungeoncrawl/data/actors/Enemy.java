package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;

public abstract class Enemy extends Actor {
  protected int movementRange;

  public Enemy(Cell cell) {
    super(cell, true);
  }

  @Override
  public boolean move(int dx, int dy) {
    Cell nextCell  = this.getCell().getNeighbor(dx, dy);
    CellType nextCellType = nextCell.getType();
    if (nextCellType.isPassable()
            && nextCell.getActor() == null
            && nextCell.getItem() == null) {
      return super.move(dx, dy);
    }
    return false;
  }

  public int getMovementRange() {
    return movementRange;
  }
}
