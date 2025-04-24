package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public class Potion extends Item {
    public Potion(Cell cell) {
        super(cell, ItemType.POTION, 3, "Health potion");
    }

    @Override
    public String getTileName() {
        return "potion";
    }
}
