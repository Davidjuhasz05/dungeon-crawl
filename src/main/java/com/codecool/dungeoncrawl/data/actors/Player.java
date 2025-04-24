package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.ItemType;

import java.util.List;

import java.util.ArrayList;

public class Player extends Actor {
    private final List<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell, false);
    }

    @Override
    public MoveResult evaluateMove(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);

        if (nextCell.getType().isBlocked()) return MoveResult.BLOCKED;

        Actor target = nextCell.getActor();

        if (target == null) {
            return MoveResult.MOVE;
        }

        if (target.isHostile()) {
            return MoveResult.ATTACK;
        }
        return MoveResult.BLOCKED;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
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

    public boolean hasKey() {
        for(Item item : inventory) {
            if(item.getItemType().equals(ItemType.ACCESS)) return true;
        }
        return false;
    }

}
