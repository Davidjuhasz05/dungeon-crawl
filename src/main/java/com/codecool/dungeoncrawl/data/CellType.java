package com.codecool.dungeoncrawl.data;

public enum CellType {
    EMPTY("empty", false),
    FLOOR("floor", true),
    FLOOR2("floor2", true),
    FLOOR3("floor3", true),
    WALL("wall", false),
    DOOR("door", true),
    RETRY("torch", true),
    QUIT("quit", true),
    PROP("animalSkull", true),
    PROP2("skeletonSkull", true),
    GRASS("grass", true),
    TRUNK1("trunk1", false),
    CANOPY1("canopy1", false),
    CANOPY2("canopy2", false),
    A("a", true), B("b", true), C("c", true), D("d", true), E("e", true), F("f", true), G("g", true),
    H("h", true), I("i", true), J("j", true), K("k", true), L("l", true), M("m", true), N("n", true),
    O("o", true), P("p", true), Q("q", true), R("r", true), S("s", true), T("t", true), U("u", true),
    V("v", true), W("w", true), X("x", true), Y("y", true), Z("z", true);

    private final String tileName;
    private final boolean isPassable;

    CellType(String tileName, boolean isPassable) {
        this.tileName = tileName;
        this.isPassable = isPassable;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isBlocked() {return !isPassable;}
}
