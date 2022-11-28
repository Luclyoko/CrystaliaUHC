package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import javax.annotation.Nullable;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.Plugin;

public class GameWorld {
    private final Main main;

    private final World world;

    private final Location center;

    private final WorldBorder worldBorder;

    private final Biome centerBiome;

    private final Listener listener;

    public GameWorld(Main main, String worldName, int xCenter, int zCenter, @Nullable Biome centerBiome) {
        this.listener = new Listener() {
            @EventHandler(priority = EventPriority.LOWEST)
            public void spawnBiomeChanger(ChunkLoadEvent event) {
                Chunk chunk = event.getChunk();
                if (event.isNewChunk() && event.getWorld().equals(GameWorld.this.world) && GameWorld.this.centerBiome != null) {
                    int cX = GameWorld.this.center.getChunk().getX();
                    int cZ = GameWorld.this.center.getChunk().getZ();
                    int x = chunk.getX();
                    int z = chunk.getZ();
                    if (cX - x >= -15 && cX - x <= 15 &&
                            cZ - z >= -15 && cZ - z <= 15)
                        for (int xL = 0; xL < 16; xL++) {
                            for (int zL = 0; zL < 16; zL++)
                                chunk.getBlock(xL, 0, zL)
                                        .setBiome(GameWorld.this.centerBiome);
                        }
                }
            }

            @EventHandler
            public void onWorldInit(WorldInitEvent event) {
                event.getWorld().setKeepSpawnInMemory(false);
            }
        };
        this.main = main;
        this.world = main.getServer().createWorld(new WorldCreator(worldName));
        this.world.setSpawnLocation(10000, 1000, 10000);
        this.center = new Location(this.world, xCenter, 120.0D, zCenter);
        this.worldBorder = this.world.getWorldBorder();
        this.worldBorder.setCenter(this.center);
        this.worldBorder.setSize(2000.0D);
        this.centerBiome = centerBiome;
        main.getServer().getPluginManager().registerEvents(this.listener, (Plugin)main);
        this.world.setGameRuleValue("naturalRegeneration", "false");
        this.world.setGameRuleValue("doFireTick", "false");
        this.world.setGameRuleValue("doDayNightCycle", "false");
        this.world.setPVP(true);
        this.world.setTime(0L);
        this.world.setDifficulty(Difficulty.NORMAL);
    }

    public GameWorld generate(int radius) {
        new MapGenerator(this.main, this.world, radius, this.center.getBlockX(), this.center.getBlockZ());
        return this;
    }

    public World getWorld() {
        return this.world;
    }

    public Location getCenter() {
        return this.center;
    }

    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }
}
