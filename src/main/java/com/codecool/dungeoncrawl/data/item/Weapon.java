package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;

public abstract class Weapon extends Item {

    public Weapon(Cell cell, ItemType type, int damage_value, String name) {
        super(cell, type, damage_value, name);
    }
}
