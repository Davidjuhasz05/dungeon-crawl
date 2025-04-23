package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public class Potion extends Item {
    public Potion(Cell cell, ItemType itemType, int value, String name) {
        super(cell, itemType, value, name);
    }

    @Override
    public String getTileName() {
        return "";
    }
}
