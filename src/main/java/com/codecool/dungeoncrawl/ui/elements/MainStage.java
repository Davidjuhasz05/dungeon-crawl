package com.codecool.dungeoncrawl.ui.elements;

import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class MainStage {
    private final Canvas canvas;
    private final Scene scene;
    private final StatusPane statusPane;

    public MainStage(Canvas canvas) {
        this.canvas = canvas;
        statusPane = new StatusPane();
        scene = setUpScene();
    }

    private Scene setUpScene() {
        BorderPane borderPane = statusPane.build();
        borderPane.setCenter(canvas);
        return new Scene(borderPane);
    }

    public Scene getScene() {
        return scene;
    }

    public void setHealthLabelValue(int health) {
        this.statusPane.setHealthValue(health);
    }

    public void setInventoryLabelText(List<Item> inventory){
        this.statusPane.setInventoryValue(inventory);
    }

    public void setWeaponLabelValue(Weapon weapon){
        this.statusPane.setWeaponValue(weapon);
    }

}
