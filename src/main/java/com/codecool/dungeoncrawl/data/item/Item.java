package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.actors.Player;

public abstract class Item implements Drawable {
    private Cell cell;
    private final ItemType itemType;
    private final String name;

    public Item(Cell cell, ItemType itemType, String name) {
        this.cell = cell;
        this.cell.setItem(this);
        this.itemType = itemType;
        this.name = name;
    }

    public void doEffect(Player player) {
        player.addToInventory(this);
        removeFromCell();
    }

    protected void removeFromCell() {
        cell.setItem(null);
        cell = null;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
