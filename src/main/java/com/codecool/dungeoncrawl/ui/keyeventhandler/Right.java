package com.codecool.dungeoncrawl.ui.keyeventhandler;

import com.codecool.dungeoncrawl.data.GameMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Right implements KeyHandler {
    public static final KeyCode code = KeyCode.RIGHT;

    @Override
    public boolean perform(KeyEvent event, GameMap map) {
        if(code.equals(event.getCode())) {
            map.getPlayer().move(1, 0);
            return true;
        }
        return false;
    }
}
