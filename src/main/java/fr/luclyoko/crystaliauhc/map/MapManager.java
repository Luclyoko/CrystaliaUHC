package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private final Main main;
    private List<GameWorld> gameWorlds;

    public MapManager(Main main) {
        this.main = main;
        this.gameWorlds = new ArrayList<>();
    }

    public void loadMap(String worldName, int xCenter, int zCenter) {
        gameWorlds.add(new GameWorld(Bukkit.createWorld(WorldCreator.name(worldName)), xCenter, zCenter));
    }
}
