package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.ItemType;
import com.codecool.dungeoncrawl.data.item.ItemWithValue;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;

import java.util.List;
import java.util.ArrayList;

public class Player extends Actor {
    private final List<Item> inventory = new ArrayList<>();
    private Weapon weapon = null;

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

    public void addWeapon(Weapon weapon) {
        if(this.weapon == null){
            this.weapon = weapon;
            getCell().setItem(null);
        }else{
            changeWeapon(weapon);
        }
        weapon.setCell(null);

    }

    public void changeWeapon(Weapon newWeapon) {
        Weapon oldWeapon = this.weapon;
        this.weapon = newWeapon;
        oldWeapon.setCell(this.getCell());
        this.getCell().setItem(oldWeapon);
    }

    public void pickup(Item item) {
        item.doEffect(this);
    }

    public void addHealth(int health) {
        this.health += health;
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
        if(weapon != null){
            attackDamage += weapon.getValue();
        }
        target.getHit(attackDamage);
    }

    @Override
    public void getHit(int damage) {
        int attackDamage = damage;
        for (Item item : inventory) {
            if (item.getItemType() == ItemType.ARMOR) {
                attackDamage -= ((ItemWithValue) item).getValue();
            }
        }
        if(attackDamage > 0){
            super.getHit(attackDamage);
        }
    }
}
