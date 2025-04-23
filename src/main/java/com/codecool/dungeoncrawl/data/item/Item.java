package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private ItemType itemType;
    private int value;

    public Item(Cell cell, ItemType itemType, int value){
        this.cell = cell;
        this.itemType = itemType;
        this.value = value;
    };

    public abstract boolean pickUp(Item item);

}
