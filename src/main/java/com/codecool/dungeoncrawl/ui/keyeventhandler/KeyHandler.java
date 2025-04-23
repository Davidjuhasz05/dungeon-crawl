package com.codecool.dungeoncrawl.ui.keyeventhandler;

import com.codecool.dungeoncrawl.data.GameMap;
import javafx.scene.input.KeyEvent;

public interface KeyHandler {
    boolean perform(KeyEvent event, GameMap map);
}
