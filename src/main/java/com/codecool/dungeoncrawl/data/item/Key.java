package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public class Key extends Item {

    public Key(Cell cell) {
        super(cell, ItemType.ACCESS, "Key");
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
