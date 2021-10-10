package fr.luclyoko.crystaliauhc.map;

import org.bukkit.Location;
import org.bukkit.World;

public class GameWorld {

    private final World world;
    private final Location center;

    public GameWorld(World world, int xCenter, int zCenter) {
        this.world = world;
        this.center = new Location(world, xCenter, 120, zCenter);
    }
}
