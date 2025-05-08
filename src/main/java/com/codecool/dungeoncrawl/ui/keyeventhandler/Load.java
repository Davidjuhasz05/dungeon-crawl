package com.codecool.dungeoncrawl.ui.keyeventhandler;

import com.codecool.dungeoncrawl.data.GameMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Load implements KeyHandler {

    @Override
    public boolean perform(KeyEvent event, GameMap map) {
        return event.getCode() == KeyCode.S;
    }
}
