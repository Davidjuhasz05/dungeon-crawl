package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;

public class Key extends Item {


    public Key(Cell cell, int value) {
        super(cell, ItemType.ACCESS, value, "Key: "+value);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
