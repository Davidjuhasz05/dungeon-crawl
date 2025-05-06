package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
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
        Item targetItem = nextCell.getItem();

        if (target == null && targetItem == null) {
            return MoveResult.MOVE;
        }

        if (targetItem != null) {
            return MoveResult.ITEM;
        }

        if (target.isHostile()) {
            return MoveResult.ATTACK;
        }
        return MoveResult.BLOCKED;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public boolean hasWeapon() {
        for(Item item : inventory) {
            if(item.getItemType() == ItemType.WEAPON){
                return true;
            }
        }
        return false;
    }

    public void changeWeapon(Item weapon) {
        Item replacedWeapon = null;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).getItemType() == ItemType.WEAPON){
                replacedWeapon = inventory.set(i, weapon);
            }
        }
        if(replacedWeapon != null) {
            this.getCell().setItem(replacedWeapon);
            replacedWeapon.setCell(this.getCell());
        }
    }

    public void pickup(Item item) {
        if (item.getItemType() == ItemType.POTION) {
            handlePotion(item);
        } else if(item.getItemType() == ItemType.WEAPON && hasWeapon()) {
            changeWeapon(item);
            return;
        }else{
            addToInventory(item);
        }
        item.getCell().setItem(null);
        item.setCell(null);
    }

    private void handlePotion(Item item) {
        if (item.getName() == "Health potion") {
            health += item.getValue();
        }
    }

    public String getTileName() {
        return "player";
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public boolean hasKey() {
        for(Item item : inventory) {
            if(item.getItemType().equals(ItemType.ACCESS)) return true;
        }
        return false;
    }

    @Override
    public void attack(Actor target) {
        int attackDamage = damage;
        for (Item item : inventory) {
            if (item.getItemType() == ItemType.WEAPON) {
                attackDamage += item.getValue();
            }
        }
        target.getHit(attackDamage);
    }

    @Override
    public void getHit(int damage) {
        int attackDamage = damage;
        for (Item item : inventory) {
            if (item.getItemType() == ItemType.ARMOR) {
                attackDamage -= item.getValue();

            }
        }
        if(attackDamage > 0){
            super.getHit(damage);
        }
    }
}
