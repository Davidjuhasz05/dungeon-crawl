package com.codecool.dungeoncrawl.ui.elements;

import com.codecool.dungeoncrawl.data.item.Item;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class StatusPane {
    public static final int RIGHT_PANEL_WIDTH = 250;
    public static final int RIGHT_PANEL_PADDING = 20;

    private VBox ui;
    private Label healthValueLabel;
    private VBox inventoryListBox;

    public StatusPane() {
        ui = new VBox(25);
        ui.setPrefWidth(RIGHT_PANEL_WIDTH);
        ui.setPadding(new Insets(RIGHT_PANEL_PADDING));
        ui.setStyle("-fx-background-color: #2b2b2b; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label healthLabel = new Label("❤️Health");
        healthLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16; -fx-font-family: 'Segoe UI Emoji';");
        healthValueLabel = new Label();
        healthValueLabel.setStyle("-fx-text-fill: lightgreen; -fx-font-size: 24; -fx-font-family: 'Segoe UI Emoji';");

        VBox healthBox = new VBox(5, healthLabel, healthValueLabel);
        ui.getChildren().add(healthBox);

        Label inventoryLabel = new Label("🎒 Inventory");
        inventoryLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16; -fx-font-family: 'Segoe UI Emoji';");
        inventoryListBox = new VBox(10);
        inventoryListBox.setPadding(new Insets(5, 0, 0, 10));

        VBox inventoryBox = new VBox(10, inventoryLabel, inventoryListBox);
        ui.getChildren().add(inventoryBox);
    }

    public BorderPane build() {
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(ui);
        return borderPane;
    }

    public void setHealthValue(String text) {
        healthValueLabel.setText(text);
        int health = Integer.parseInt(text);
        if (health > 9) {
            healthValueLabel.setStyle("-fx-text-fill: lightgreen; -fx-font-size: 24; -fx-font-family: 'Segoe UI Emoji';");
        } else if (health > 3 && health <= 7) {
            healthValueLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 24; -fx-font-family: 'Segoe UI Emoji';");
        } else {
            healthValueLabel.setStyle("-fx-text-fill: red; -fx-font-size: 24; -fx-font-family: 'Segoe UI Emoji';");
        }
    }

    public void setInventoryValue(List<Item> inventory) {
        inventoryListBox.getChildren().clear();

        for (Item item : inventory) {
            Label itemLabel = new Label(item.getName());
            itemLabel.setStyle(
                    "-fx-text-fill: white; " +
                            "-fx-font-size: 18; " +
                            "-fx-padding: 10 15; " +
                            "-fx-background-color: #3c3f41; " +
                            "-fx-background-radius: 8px; " +
                            "-fx-border-radius: 8px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.2), 5, 0, 0, 2); " +
                            "-fx-font-family: 'Segoe UI Emoji';");

            inventoryListBox.getChildren().add(itemLabel);
        }
    }
}
