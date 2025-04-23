package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public class Key extends Item {


    public Key(Cell cell, ItemType itemType, int value, String name) {
        super(cell, itemType, value, name);
    }

    @Override
    public String getTileName() {
        return "";
    }
}
