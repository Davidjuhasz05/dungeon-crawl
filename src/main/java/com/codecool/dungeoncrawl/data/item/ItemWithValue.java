package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;

public abstract class ItemWithValue extends Item {
    protected final int value;

    public ItemWithValue(Cell cell, ItemType itemType, int value, String name) {
        super(cell, itemType, name);
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
