package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public class Armor extends ItemWithValue {
    private static final int DEFENSE_VALUE = 1;

    public Armor(Cell cell) {
        super(cell, ItemType.ARMOR, DEFENSE_VALUE, "Armor");
    }

    @Override
    public String getTileName() {
        return "armor";
    }

}
