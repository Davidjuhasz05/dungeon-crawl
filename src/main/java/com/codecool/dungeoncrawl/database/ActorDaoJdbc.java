package com.codecool.dungeoncrawl.database;


import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.enemies.Golem;
import com.codecool.dungeoncrawl.data.actors.enemies.Skeleton;
import com.codecool.dungeoncrawl.data.actors.enemies.Spider;
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

    public Actor loadActor(Cell cell, int actorId) throws SQLException {
        try (Connection conn = dataSource.connect()) {
            Actor actor;
            PreparedStatement statement = conn.prepareStatement("select health, actortype, inventory, weapon, remainingsteps, visionrange from actor where actor.id = ?");
            statement.setInt(1, actorId);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                int health = results.getInt("health");
                String actorType = results.getString("actorType");
                Array inventory = results.getArray("inventory");
                int weapon = results.getInt("weapon");
                int remainingsteps = results.getInt("remainingsteps");
                int visionRange = results.getInt("visionrange");
                switch (actorType) {
                    case "Player":
                        actor = new Player(cell, visionRange, remainingsteps);
                        break;
                    case "Skeleton":
                        actor = new Skeleton(cell);
                        break;
                    case "Spider":
                        actor = new Spider(cell);
                        break;
                    case "Golem":
                        actor = new Golem(cell);
                        break;
                    default:
                        actor = null;
                }
                if (actor == null) {
                    throw new SQLException("Could not load actor " + actorId);
                }
                actor.setHealth(health);
                if (inventory != null && actor instanceof Player) {
                    itemDaoJdbc.loadInventory((Player) actor, inventory);
                }
                if (weapon != 0 && actor instanceof Player) {
                    Item loadedWeapon = itemDaoJdbc.loadItem(weapon, actor.getCell());
                    loadedWeapon.setCell(null);
                    actor.getCell().setItem(null);
                    if (loadedWeapon instanceof Weapon) {
                        ((Player) actor).addWeapon((Weapon) loadedWeapon);
                    }
                }
                return actor;
            }
            throw new SQLException();
        } catch (SQLException e) {
            throw new SQLException("Could not load actor " + actorId);
        }
    }

    public int saveActor(Actor actor) {
        try (Connection conn = dataSource.connect()) {
            Integer weaponId = null;
            Integer[] inventoryIds = null;
            int remainingSteps = -1;
            int visionRange = -1;

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
                remainingSteps = player.getRemainingSteps();
                visionRange = player.getVisionRange();
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO actor (health, actorType, weapon, inventory, remainingsteps, visionrange) VALUES (?, ?, ?, ?, ?, ?) RETURNING id"
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
            if (remainingSteps >= 0) {
                stmt.setInt(5, remainingSteps);
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            if (visionRange >= 0) {
                stmt.setInt(6, visionRange);
            } else {
                stmt.setNull(6, Types.INTEGER);
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
        try (Connection conn = dataSource.connect()) {
            String sql = "DELETE FROM actor";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Could not clear actor");
        }
    }
}
