package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;

public class Potion extends Item {
    private static final int HEALING_VALUE = 3;

    public Potion(Cell cell) {
        super(cell, ItemType.POTION, HEALING_VALUE, "Health potion");
    }

    @Override
    public String getTileName() {
        return "potion";
    }

    @Override
    public void doEffect(Player player) {
        player.addHealth(HEALING_VALUE);
        removeFromCell();
    }
}
