package com.codecool.dungeoncrawl.database;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDaoJdbc {
    private final GameDatabaseDataSource dataSource;
    private final ItemDaoJdbc itemDaoJdbc;

    public ActorDaoJdbc(GameDatabaseDataSource dataSource, ItemDaoJdbc itemDaoJdbc) {
        this.dataSource = dataSource;
        this.itemDaoJdbc = itemDaoJdbc;
    }

    public Actor loadActor(Cell cell, String actorId) throws SQLException {
        try(Connection conn = dataSource.connect()){
            Actor actor;
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
                itemDaoJdbc.loadInventory((Player) actor, inventory);
            }
            if(weapon != null && actor instanceof Player){
                Item loadedWeapon = itemDaoJdbc.loadItem(weapon, actor.getCell());
                loadedWeapon.setCell(null);
                actor.getCell().setItem(null);
                if(loadedWeapon instanceof Weapon){
                    ((Player) actor).addWeapon((Weapon) loadedWeapon);
                }
            }
            return actor;
        } catch(SQLException e) {
            throw new SQLException("Could not load actor " + actorId);
        }
    }

    public int saveActor(Actor actor) {
        try (Connection conn = dataSource.connect()) {
            Integer weaponId = null;
            Integer[] inventoryIds = null;


            if (actor instanceof Player) {
                Player player = (Player) actor;
                if (player.getWeapon() != null) {
                    weaponId = itemDaoJdbc.saveItem(player.getWeapon());
                }

                if (!player.getInventory().isEmpty()) {
                    List<Integer> ids = new ArrayList<>();
                    for (Item item : player.getInventory()) {
                        int id = itemDaoJdbc.saveItem(item);
                        ids.add(id);
                    }
                    inventoryIds = ids.toArray(new Integer[0]);
                }
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO actor (health, actorType, weapon, inventory) VALUES (?, ?, ?, ?) RETURNING id"
            );
            stmt.setInt(1, actor.getHealth());
            stmt.setString(2, actor.getClass().getSimpleName());

            if (weaponId != null) {
                stmt.setInt(3, weaponId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            if (inventoryIds != null) {
                Array sqlArray = conn.createArrayOf("INTEGER", inventoryIds);
                stmt.setArray(4, sqlArray);
            } else {
                stmt.setNull(4, Types.ARRAY);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Failed to insert actor.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving actor", e);
        }
    }

    public void clearActors() throws SQLException {
        try(Connection conn = dataSource.connect()){
            String sql= "DELETE FROM actor";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new SQLException("couldnt cleaer actor");
        }
    }
}
