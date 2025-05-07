package com.codecool.dungeoncrawl.ui.elements;

import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class StatusPane {
    private static final int RIGHT_PANEL_WIDTH = 250;
    private static final int RIGHT_PANEL_PADDING = 20;
    private static final int HEALTH_WARNING_THRESHOLD = 7;
    private static final int HEALTH_CRITICAL_THRESHOLD = 3;

    private static final String FONT_FAMILY = "'Segoe UI Emoji'";
    private static final String LABEL_STYLE = "-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 22; -fx-font-family: " + FONT_FAMILY + ";";
    private static final String HEALTH_LABEL_STYLE = "-fx-font-size: 30; -fx-font-family: " + FONT_FAMILY + ";";
    private static final String WEAPON_LABEL_STYLE = "-fx-font-size: 23; -fx-text-fill: white; -fx-font-family: " + FONT_FAMILY + ";";

    private final VBox root;
    private final Label healthValueLabel;
    private final Label weaponValueLabel;
    private final VBox inventoryListBox;

    public StatusPane() {
        root = new VBox(30);
        root.setPrefWidth(RIGHT_PANEL_WIDTH);
        root.setPadding(new Insets(RIGHT_PANEL_PADDING));
        root.setStyle("-fx-background-color: #2b2b2b; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        healthValueLabel = createLabel("", HEALTH_LABEL_STYLE);
        root.getChildren().add(createHealthSection());
        weaponValueLabel = createLabel("", WEAPON_LABEL_STYLE);
        root.getChildren().add(createWeaponSection());
        inventoryListBox = new VBox(10);
        inventoryListBox.setPadding(new Insets(10, 0, 0, 5));
        root.getChildren().add(createInventorySection());
    }

    public BorderPane build() {
        BorderPane pane = new BorderPane();
        pane.setRight(root);
        return pane;
    }

    public void setHealthValue(int health) {
        healthValueLabel.setText(String.valueOf(health));
        healthValueLabel.setStyle(getHealthStyle(health));
    }

    public void setInventoryValue(List<Item> inventory) {
        inventoryListBox.getChildren().clear();
        for (Item item : inventory) {
            Label itemLabel = createInventoryLabel(item.getName());
            inventoryListBox.getChildren().add(itemLabel);
        }
    }

    public void setWeaponValue(Weapon weapon){
        weaponValueLabel.setText(weapon.getName());
    }

    private VBox createHealthSection() {
        Label label = createLabel("❤ Health", LABEL_STYLE);
        VBox healthBox = new VBox(10, label, healthValueLabel);
        healthBox.setPadding(new Insets(0));
        return healthBox;
    }

    private VBox createWeaponSection() {
        Label label = createLabel("⚔ Weapon", LABEL_STYLE);
        VBox weaponBox = new VBox(10, label, weaponValueLabel);
        weaponBox.setPadding(new Insets(0));
        return weaponBox;
    }

    private VBox createInventorySection() {
        Label label = createLabel("🎒 Inventory", LABEL_STYLE);
        VBox inventoryBox = new VBox(10, label, inventoryListBox);
        inventoryBox.setPadding(new Insets(0));
        return inventoryBox;
    }

    private Label createLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    private Label createInventoryLabel(String text) {
        Label label = new Label(text);
        label.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 18;" +
                        "-fx-padding: 10 15;" +
                        "-fx-background-color: #3c3f41;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.2), 2, 0, 0, 2);" +
                        "-fx-font-family: " + FONT_FAMILY + ";"
        );
        return label;
    }

    private String getHealthStyle(int health) {
        String color = "lightgreen";
        if (health <= HEALTH_CRITICAL_THRESHOLD) {
            color = "red";
        } else if (health <= HEALTH_WARNING_THRESHOLD) {
            color = "orange";
        }
        return "-fx-text-fill: " + color + "; " + HEALTH_LABEL_STYLE;
    }
}
