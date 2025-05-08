package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Spider extends Enemy {
  private static final int MOVEMENT_RANGE = 3;
  private static final int HEALTH_VALUE = 5;
  private static final int DAMAGE_VALUE = 5;

  public Spider(Cell cell) {
    super(cell, MOVEMENT_RANGE);
    damage = DAMAGE_VALUE;
    health = HEALTH_VALUE;
  }

  @Override
  public String getTileName() {
    return "spider";
  }
}
