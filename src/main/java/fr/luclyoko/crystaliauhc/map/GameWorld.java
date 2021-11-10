package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;

public class GameWorld {

    private final Main main;
    private final World world;
    private final Location center;
    private final WorldBorder worldBorder;

    public GameWorld(Main main, String worldName, int xCenter, int zCenter) {
        this.main = main;
        this.world = new WorldCreator(worldName).createWorld();
        this.center = new Location(world, xCenter, 120, zCenter);
        this.worldBorder = world.getWorldBorder();
        this.worldBorder.setCenter(center);
        this.worldBorder.setSize(2*1000);
    }

    public GameWorld generate(int radius) {
        new MapGenerator(this.main, this.world, radius, this.center.getBlockX(), this.center.getBlockZ());
        return this;
    }

    public World getWorld() {
        return world;
    }

    public Location getCenter() {
        return center;
    }

    public WorldBorder getWorldBorder() {
        return worldBorder;
    }
}
