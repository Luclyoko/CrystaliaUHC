package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private final Main main;
    private List<World> worlds = new ArrayList<>();

    public MapManager(Main main) {
        this.main = main;
    }

    public void loadMap(String worldName) {
        worlds.add(Bukkit.createWorld(WorldCreator.name(worldName)));
    }
}
