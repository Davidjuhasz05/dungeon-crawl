package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public class Armor extends Item {
    public Armor(Cell cell) {
        super(cell, ItemType.ARMOR, 1, "Armor");
    }

    @Override
    public String getTileName() {
        return "armor";
    }
}
