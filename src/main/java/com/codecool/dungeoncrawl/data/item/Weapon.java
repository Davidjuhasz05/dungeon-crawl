package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;

public class Weapon extends Item {

    public Weapon(Cell cell ) {
        super(cell, ItemType.WEAPON, 3, "Weapon");
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
