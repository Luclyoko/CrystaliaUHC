package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapManager {

    private final Main main;
    private List<GameWorld> gameWorlds;

    public MapManager(Main main) {
        this.main = main;
        this.gameWorlds = new ArrayList<>();
    }

    public GameWorld loadMap(String worldName, int xCenter, int zCenter) {
        GameWorld gameWorld = new GameWorld(main, worldName, xCenter, zCenter);
        gameWorlds.add(gameWorld);

        return gameWorld;
    }

    public void modifyBiomes() {
        final HashMap<String, HashMap<String, Integer>> biomsdisable = new HashMap<>();
        final HashMap<String, Integer> replace_biome = new HashMap<>();
        replace_biome.put(Biome.FOREST.name(), Biome.FOREST.ordinal());
        biomsdisable.put(Biome.BEACH.toString(), replace_biome);
        biomsdisable.put(Biome.OCEAN.toString(), replace_biome);
        biomsdisable.put(Biome.DESERT.toString(), replace_biome);
        biomsdisable.put(Biome.DESERT_HILLS.toString(), replace_biome);
        biomsdisable.put(Biome.DEEP_OCEAN.toString(), replace_biome);
        biomsdisable.put(Biome.JUNGLE.toString(), replace_biome);
        biomsdisable.put(Biome.JUNGLE_EDGE.toString(), replace_biome);
        biomsdisable.put(Biome.JUNGLE_HILLS.toString(), replace_biome);
        biomsdisable.put(Biome.EXTREME_HILLS.toString(), replace_biome);
        biomsdisable.put(Biome.COLD_TAIGA_HILLS.toString(), replace_biome);
        biomsdisable.put(Biome.COLD_BEACH.toString(), replace_biome);
        biomsdisable.put(Biome.COLD_TAIGA.toString(), replace_biome);
        new BiomeModifier(biomsdisable);
    }
}
