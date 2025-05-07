package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;

public class Torch extends Item {
  private static final int VISIBILITY_RANGE = 2;

  public Torch(Cell cell) {
    super(cell, ItemType.TORCH, "Torch");
  }

  @Override
  public void doEffect(Player player) {
    player.applyTorchEffects();
    removeFromCell();
  }

  public boolean isVisible(Cell cell) {
    if(getCell() == null) return false;
    int x = Math.abs(cell.getX() - getCell().getX());
    int y = Math.abs(cell.getY() - getCell().getY());

    return x + y < VISIBILITY_RANGE
            && x != VISIBILITY_RANGE
            && y != VISIBILITY_RANGE;
  }

  @Override
  public String getTileName() {
    return "torch";
  }

}
