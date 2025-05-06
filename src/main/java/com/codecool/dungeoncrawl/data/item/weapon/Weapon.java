package com.codecool.dungeoncrawl.data.item.weapon;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.item.ItemType;
import com.codecool.dungeoncrawl.data.item.ItemWithValue;

public abstract class Weapon extends ItemWithValue {

    public Weapon(Cell cell, ItemType type, int damage_value, String name) {
        super(cell, type, damage_value, name);
    }
}
