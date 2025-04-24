package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.ItemType;
import com.codecool.dungeoncrawl.data.item.Weapon;

import java.util.List;

import java.util.ArrayList;

public class Player extends Actor {
    private final List<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell, false);
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public void pickup(Item item) {
        addToInventory(item);
        item.getCell().setItem(null);
        item.setCell(null);
    }

    public String getTileName() {
        return "player";
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    @Override
    public void attack(Actor target) {
        int attackDamage = damage;
        for (Item item : inventory) {
            if (item.getItemType() == ItemType.WEAPON) {
                attackDamage += item.getValue();
                super.attacking(target, attackDamage);
            }
        }
    }
}
