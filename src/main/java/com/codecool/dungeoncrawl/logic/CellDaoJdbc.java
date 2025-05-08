package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;

import javax.sql.DataSource;
import java.sql.*;

public class CellDaoJdbc {
    private final DataSource dataSource;

    public CellDaoJdbc(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getMapWidth() throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            return conn.createStatement()
                    .executeQuery("select max(x) from map")
                    .getInt(1);
        } catch(SQLException e) {
            throw new SQLException("Could not get map width");
        }
    }

    public int getMapHeight() throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            return conn.createStatement()
                    .executeQuery("select max(y) from map")
                    .getInt(1);
        } catch(SQLException e) {
            throw new SQLException("Could not get map height");
        }
    }

    public String getMapName() throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            return conn.createStatement()
                    .executeQuery("select mapname from map limit 1")
                    .getString(1);
        } catch(SQLException e) {
            throw new SQLException("Could not get map name");
        }
    }

    public ResultSet getCellByCoordinates(int x, int y) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            PreparedStatement statement = conn.prepareStatement("select cellType, actor, item from map where map.x = ? and map.y = ?");
            statement.setInt(1, x);
            statement.setInt(2, y);
            return statement.executeQuery();
        } catch(SQLException e) {
            throw new SQLException("Could not get cell");
        }
    }

    public Actor loadActor(Cell cell, String actorId) throws SQLException {
        Actor actor;
        try(Connection conn = dataSource.getConnection()){
            PreparedStatement statement = conn.prepareStatement("select health, actorType, inventory, weapon from actor where actor.actor_id = ?");
            statement.setString(1, actorId);
            ResultSet results = statement.executeQuery();
            int health = results.getInt("health");
            String actorType = results.getString("actorType");
            Array inventory = results.getArray("inventory");
            String weapon = results.getString("weapon");
            switch(actorType){
                case "player":
                    actor = new Player(cell);
                    break;
                case "skeleton":
                    actor = new Skeleton(cell);
                    break;
                default:
                    actor = null;
            }
            if(actor == null){
                throw new SQLException("Could not load actor " + actorId);
            }
            actor.setHealth(health);
            if(inventory != null && actor instanceof Player){
                loadInventory((Player) actor, inventory);
            }
            if(weapon != null && actor instanceof Player){
                Item loadedWeapon = loadItem(weapon);
                if(loadedWeapon instanceof Weapon){
                    ((Player) actor).addWeapon((Weapon) loadedWeapon);
                }
            }
        } catch(SQLException e) {
            throw new SQLException("Could not load actor " + actorId);
        }
        return actor;
    }

    private void loadInventory(Player player, Array inventory) throws SQLException {
        String[] inventoryItemIds = (String[]) inventory.getArray();
        for(String itemId : inventoryItemIds){
            player.addToInventory(loadItem(itemId));
        }
    }

    public Item loadItem(String itemId) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            
        } catch(SQLException e) {
            throw new SQLException("Could not load item " + itemId);
        }
    }

}
