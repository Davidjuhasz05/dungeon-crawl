package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;

public class Weapon extends Item {


    public Weapon(Cell cell, ItemType itemType, int value, String name) {
        super(cell, itemType, value, name);
    }

    @Override
    public boolean pickUp(Item item) {
        return false;
    }

    @Override
    public String getTileName() {
        return "";
    }
}
