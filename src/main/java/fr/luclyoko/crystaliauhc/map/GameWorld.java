package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class GameWorld {

    private final Main main;
    private final World world;
    private final Location center;

    public GameWorld(Main main, String worldName, int xCenter, int zCenter) {
        this.main = main;
        this.world = new WorldCreator(worldName).createWorld();
        this.center = new Location(world, xCenter, 120, zCenter);
    }

    public GameWorld generate(int radius) {
        new MapGenerator(this.main, this.world, radius, this.center.getBlockX(), this.center.getBlockZ());
        return this;
    }
}
