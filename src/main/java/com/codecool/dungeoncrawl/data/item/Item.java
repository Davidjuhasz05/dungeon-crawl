package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private ItemType itemType;
    private int value;
    private String name;

    public Item(Cell cell, ItemType itemType, int value, String name) {
        this.cell = cell;
        this.cell.setItem(this);
        this.itemType = itemType;
        this.value = value;
        this.name = name;
    };

    public Cell getCell() {
        return cell;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
