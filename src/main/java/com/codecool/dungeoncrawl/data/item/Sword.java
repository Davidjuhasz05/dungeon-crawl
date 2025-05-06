package com.codecool.dungeoncrawl.data.item;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;

import java.util.Optional;

public class Sword extends Weapon{
    private static final int DAMAGE_VALUE = 5;

    public Sword(Cell cell) {
        super(cell, ItemType.WEAPON, DAMAGE_VALUE, "Sword");
    }

    @Override
    public String getTileName() {
        return "sword";
    }

    @Override
    public void doEffect(Player player) {
        player.addWeapon(this);

    }
}
