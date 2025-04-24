package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.item.Item;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int damage = 5;
    private final boolean isHostile;

    public Actor(Cell cell, boolean isHostile) {
        this.cell = cell;
        this.cell.setActor(this);
        this.isHostile = isHostile;
    }

    public MoveResult evaluateMove(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);

        if (nextCell.getType().isBlocked()) return MoveResult.BLOCKED;

        Actor target = nextCell.getActor();
        Item targetItem = nextCell.getItem();

        if (target == null && targetItem == null) {
            return MoveResult.MOVE;
        }

        if(targetItem != null) {
            return  MoveResult.ITEM;
        }

        if (target.isHostile()) {
            return MoveResult.ATTACK;
        }
        return MoveResult.BLOCKED;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public void attack(Actor target) {
        int damage = this.getDamage();
        int targetHealth = target.getHealth();
        if(damage >= targetHealth) {
            kill(target);
            return;
        }
        target.setHealth(targetHealth - damage);
    }

    private void kill(Actor target) {
        target.getCell().setActor(null);
        target.setCell(null);
    }

    public Actor getNeighbourCellActor(int dx, int dy){
        return this.cell.getNeighbourActor(dx, dy);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean isHostile() {return isHostile;}
}
