package com.codecool.dungeoncrawl.database;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.item.*;
import com.codecool.dungeoncrawl.data.item.weapon.Sword;

import java.sql.*;

public class ItemDaoJdbc {
    private final GameDatabaseDataSource dataSource;

    public ItemDaoJdbc(GameDatabaseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadInventory(Player player, Array inventory) throws SQLException {
        Integer[] inventoryItemIds = (Integer[]) inventory.getArray();
        for (Integer itemId : inventoryItemIds) {
            Item loadedItem = loadItem(itemId, player.getCell());
            loadedItem.setCell(null);
            player.getCell().setItem(null);
            player.addToInventory(loadedItem);
        }
    }

    public Item loadItem(int itemId, Cell cell) throws SQLException {
        try (Connection conn = dataSource.connect()) {
            Item loadedItem;
            PreparedStatement statement = conn.prepareStatement("select item from item where id = ?");
            statement.setInt(1, itemId);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                String itemType = results.getString("item");

                switch (itemType) {
                    case "Sword":
                        loadedItem = new Sword(cell);
                        break;
                    case "Armor":
                        loadedItem = new Armor(cell);
                        break;
                    case "Health Potion":
                        loadedItem = new HealthPotion(cell);
                        break;
                    case "Key":
                        loadedItem = new Key(cell);
                        break;
                    case "Torch":
                        loadedItem=new Torch(cell);
                        break;
                    default:
                        loadedItem = null;
                }
                return loadedItem;
            }
            throw new SQLException();

        } catch (SQLException e) {
            throw new SQLException("Could not load item " + itemId);
        }
    }

    public int saveItem(Item item) {
        try (Connection conn = dataSource.connect()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO item (item) VALUES (?) RETURNING id"
            );
            stmt.setString(1, item.getName());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Failed to insert item");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving item", e);
        }
    }

    public void clearItems() throws SQLException {
        try (Connection conn = dataSource.connect()) {
            String sql = "DELETE FROM item";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("couldnt cleaer map");
        }
    }
}
