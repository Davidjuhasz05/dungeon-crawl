package com.codecool.dungeoncrawl.database;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.item.Armor;
import com.codecool.dungeoncrawl.data.item.HealthPotion;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.Key;
import com.codecool.dungeoncrawl.data.item.weapon.Sword;

import javax.sql.DataSource;
import java.sql.*;

public class ItemDaoJdbc {
    private final DataSource dataSource;

    public ItemDaoJdbc(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadInventory(Player player, Array inventory) throws SQLException {
        String[] inventoryItemIds = (String[]) inventory.getArray();
        for(String itemId : inventoryItemIds){
            Item loadedItem = loadItem(itemId, player.getCell());
            loadedItem.setCell(null);
            player.getCell().setItem(null);
            player.addToInventory(loadedItem);
        }
    }

    public Item loadItem(String itemId, Cell cell) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            Item loadedItem;
            PreparedStatement statement = conn.prepareStatement("select item from item where itemId = ?");
            statement.setString(1, itemId);
            ResultSet results = statement.executeQuery();
            String itemType = results.getString("item");
            switch(itemType){
                case "sword":
                    loadedItem = new Sword(cell);
                    break;
                case "armor":
                    loadedItem = new Armor(cell);
                    break;
                case "healthPotion":
                    loadedItem = new HealthPotion(cell);
                    break;
                case "key":
                    loadedItem = new Key(cell);
                    break;
                default:
                    loadedItem = null;
            }
            return loadedItem;
        } catch(SQLException e) {
            throw new SQLException("Could not load item " + itemId);
        }
    }
}
