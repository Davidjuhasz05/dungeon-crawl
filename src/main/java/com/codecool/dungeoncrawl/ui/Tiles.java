package com.codecool.dungeoncrawl.ui;

import com.codecool.dungeoncrawl.data.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("floor2", new Tile(3, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("spider", new Tile(30, 5));
        tileMap.put("golem", new Tile(30, 6));
        tileMap.put("sword", new Tile(0, 30));
        tileMap.put("armor", new Tile(3, 23));
        tileMap.put("key", new Tile(18, 23));
        tileMap.put("potion", new Tile(17, 25));
        tileMap.put("door", new Tile(10, 9));
        tileMap.put("torch", new Tile(4, 15));
        tileMap.put("quit", new Tile(22, 23));
        tileMap.put("animalSkull", new Tile(1, 15));
        tileMap.put("skeletonSkull", new Tile(0, 15));
        tileMap.put("grass", new Tile(5, 0));
        tileMap.put("a", new Tile(19, 30)); tileMap.put("b", new Tile(20, 30)); tileMap.put("c", new Tile(21, 30));
        tileMap.put("d", new Tile(22, 30)); tileMap.put("e", new Tile(23, 30)); tileMap.put("f", new Tile(24, 30));
        tileMap.put("g", new Tile(25, 30)); tileMap.put("h", new Tile(26, 30)); tileMap.put("i", new Tile(27, 30));
        tileMap.put("j", new Tile(28, 30)); tileMap.put("k", new Tile(29, 30)); tileMap.put("l", new Tile(30, 30));
        tileMap.put("m", new Tile(31, 30)); tileMap.put("n", new Tile(19, 31)); tileMap.put("o", new Tile(20, 31));
        tileMap.put("p", new Tile(21, 31)); tileMap.put("q", new Tile(22, 31)); tileMap.put("r", new Tile(23, 31));
        tileMap.put("s", new Tile(24, 31)); tileMap.put("t", new Tile(25, 31)); tileMap.put("u", new Tile(26, 31));
        tileMap.put("v", new Tile(27, 31)); tileMap.put("w", new Tile(28, 31)); tileMap.put("x", new Tile(29, 31));
        tileMap.put("y", new Tile(30, 31)); tileMap.put("z", new Tile(31, 31));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
