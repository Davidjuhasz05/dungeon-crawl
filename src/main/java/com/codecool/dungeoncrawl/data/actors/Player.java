package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.item.Item;

import java.util.List;

import java.util.ArrayList;
import com.codecool.dungeoncrawl.data.CellType;

public class Player extends Actor {
    private List<Item> inventory =new ArrayList<>();

    public Player(Cell cell) {
        super(cell, false);
    }

    public void addToInventory(Item item) {
        addToInventory(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public String getTileName() {
        return "player";
    }

    public List<Item> getInventory(){
        return this.inventory;
    }

    @Override
    public boolean move(int dx, int dy) {
        Cell nextCell  = this.getCell().getNeighbor(dx, dy);
        CellType nextCellType = nextCell.getType();
        if (nextCellType.isPassable()
                && (nextCell.getActor() == null
                || !nextCell.getActor().isHostile())){
            super.move(dx, dy);
        }
        return false;
    }

}
