package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private List<Item> inventory =new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
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

}
