package com.codecool.dungeoncrawl.ui.keyeventhandler;

import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.MoveResult;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DirectionsKeyHandler implements KeyHandler {
    private final KeyCode code;
    private final int dx;
    private final int dy;

    public DirectionsKeyHandler(KeyCode code, int dx, int dy) {
        this.code = code;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public boolean perform(KeyEvent event, GameMap map) {
        if(!code.equals(event.getCode())) {
            return false;
        }

        Actor player = map.getPlayer();
        MoveResult moveResult = player.evaluateMove(dx, dy);

        switch (moveResult) {
            case MOVE:
                player.move(dx, dy);
                break;
            case ATTACK:
                player.attack(player.getNeighbourCellActor(dx, dy));
                break;
            case BLOCKED:
                break;
        }
        return true;
    }
}
