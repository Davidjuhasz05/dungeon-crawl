package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    protected int health;
    protected int damage;
    private final boolean isHostile;

    public Actor(Cell cell, boolean isHostile) {
        this.cell = cell;
        this.cell.setActor(this);
        this.isHostile = isHostile;
    }

    public abstract MoveResult evaluateMove(int dx, int dy);

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public void getHit(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            cell.setActor(null);
            cell = null;
        }
    }

    public void attack(Actor target) {
        target.getHit(damage);
    }

    public Actor getNeighbourCellActor(int dx, int dy) {
        return this.cell.getNeighbourActor(dx, dy);
    }

    public int getHealth() {
        return health;
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

    public boolean isHostile() {
        return isHostile;
    }
}
