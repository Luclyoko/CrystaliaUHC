package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.utils.schematics.Schematic;
import fr.luclyoko.crystaliauhc.utils.schematics.SchematicManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapManager {
    private final Main main;

    private List<GameWorld> gameWorlds;

    private World lobby;

    public MapManager(Main main) {
        this.main = main;
        this.gameWorlds = new ArrayList<>();
    }

    public GameWorld loadMap(String worldName, int xCenter, int zCenter, @Nullable Biome centerBiome) {
        GameWorld gameWorld = new GameWorld(this.main, worldName, xCenter, zCenter, centerBiome);
        this.gameWorlds.add(gameWorld);
        return gameWorld;
    }

    public void modifyBiomes() {
        HashMap<String, HashMap<String, Integer>> biomsdisable = new HashMap<>();
        HashMap<String, Integer> replace_biome = new HashMap<>();
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

    public void loadSpawnSchematic() {
        Location location = this.main.getGameManager().getGameWorld().getCenter();
        SchematicManager schematicManager = this.main.getSchematicManager();
        Schematic centerSchematic = schematicManager.loadSchematic(schematicManager.getSchematicFromRessources("spawn.schematic"));
        schematicManager.pasteSchematic(location.clone().add(-15.0D, 30.0D, -15.0D), centerSchematic);
    }

    public void unloadSpawnSchematic() {
        int cX = this.main.getGameManager().getGameWorld().getCenter().getBlockX();
        int cZ = this.main.getGameManager().getGameWorld().getCenter().getBlockZ();
        for (int x = cX - 16; x <= cX + 16; x++) {
            for (int z = cZ - 16; z <= cZ - 16; z++) {
                for (int y = 149; y <= 160; y++)
                    this.main.getGameManager().getGameWorld().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
            }
        }
    }

    public boolean deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return path.delete();
    }

    public void createLobby() {
        this.lobby = this.main.getServer().createWorld(new WorldCreator("lobby"));
        this.lobby.setGameRuleValue("doDayLightCycle", "false");
        this.lobby.setGameRuleValue("doMobSpawning", "false");
        this.lobby.setTime(0L);
        this.lobby.setWeatherDuration(0);
        this.lobby.setThundering(false);
    }

    public World getLobby() {
        return this.lobby;
    }
}
