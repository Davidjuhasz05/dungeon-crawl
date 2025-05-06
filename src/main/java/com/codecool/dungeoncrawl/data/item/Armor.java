package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;

public class Armor extends Item {
    private static final int DEFENSE_VALUE = 1;

    public Armor(Cell cell) {
        super(cell, ItemType.ARMOR, DEFENSE_VALUE, "Armor");
    }

    @Override
    public String getTileName() {
        return "armor";
    }

}
