package com.codecool.dungeoncrawl.database;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.item.Item;
import com.codecool.dungeoncrawl.data.item.weapon.Weapon;

import java.sql.*;

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
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO actor (health, actorType) VALUES (?, ?) RETURNING id"
            );
            stmt.setInt(1, actor.getHealth());
            stmt.setString(2, actor.getTileName());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to insert actor");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
