package fr.luclyoko.crystaliauhc.map;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class MapGenerator {
    private final Main main;

    private final World world;

    private final int radius;

    private final int xCenter;

    private final int zCenter;

    private int numberChunk;

    private int lastShow = -1;

    private ChunkLoader chunkLoader;

    private BukkitTask task;

    private boolean finish = false;

    private final Listener listener;

    public MapGenerator(Main main, World world, int radius, int xCenter, int zCenter) {
        this.listener = new Listener() {
            @EventHandler(priority = EventPriority.LOW)
            public void onChunkUnload(ChunkUnloadEvent event) {
                event.getChunk().unload(true);
            }
        };
        this.main = main;
        this.world = world;
        this.radius = radius;
        this.xCenter = xCenter;
        this.zCenter = zCenter;
        preload();
    }

    public void preload() {
        long startTime = System.currentTimeMillis();
        Bukkit.getPluginManager().registerEvents(this.listener, (Plugin)this.main);
        this.task = Bukkit.getScheduler().runTaskTimer((Plugin)this.main, this.chunkLoader = new ChunkLoader(this.world, startTime, this.radius, this.xCenter, this.zCenter), 1L, 1L);
    }

    public boolean isFinish() {
        return this.finish;
    }

    public ChunkLoader getChunkLoader() {
        return this.chunkLoader;
    }

    void setFinish() {
        this.finish = true;
    }

    private class ChunkLoader implements Runnable {
        private final World world;

        private final long startTime;

        private final int size;

        public int percentage;

        private int todo;

        private int x;

        private int z;

        private int xCenter;

        private int zCenter;

        private ChunkLoader(World world, long startTime, int size, int xCenter, int zCenter) {
            this.world = world;
            this.startTime = startTime;
            this.size = size;
            this.x = -size;
            this.z = -size;
            this.todo = size * size * 4 / 256;
            this.xCenter = xCenter;
            this.zCenter = zCenter;
        }

        public void run() {
            int i = 0;
            while (i < 50) {
                this.world.getChunkAt(this.world.getBlockAt(this.x - this.xCenter, 64, this.z - this.zCenter)).load(true);
                this.percentage = MapGenerator.this.numberChunk * 100 / this.todo;
                if (this.percentage > MapGenerator.this.lastShow && this.percentage <= 100) {
                    MapGenerator.this.lastShow = this.percentage;
                    MapGenerator.this.main.getLogger().info("Prégénération du monde " + this.world.getName() + " : " + this.percentage + "% / 100%");
                    if (!Bukkit.getOnlinePlayers().isEmpty())
                        Bukkit.getOnlinePlayers().forEach(player -> MapGenerator.this.main.getTitle().sendActionBar(player, "§aPrégénération (monde : §o" + this.world.getName() + "§r§a) : §2" + this.percentage + "% / 100%"));
                }
                this.z += 16;
                if (this.z >= this.size) {
                    this.z = -this.size;
                    this.x += 16;
                }
                if (this.x >= this.size) {
                    MapGenerator.this.task.cancel();
                    MapGenerator.this.finish = true;
                    HandlerList.unregisterAll(MapGenerator.this.listener);
                    String l = String.format("%.3f", (System.currentTimeMillis() - this.startTime) / 1000.0D);
                    Bukkit.broadcastMessage(MapGenerator.this.main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aPrégénération du monde " + this.world.getName() + " terminée avec succès.");
                            Location location = new Location(Bukkit.getWorld("world"), 0.0D, 150.0D, 0.0D);
                    return;
                }
                MapGenerator.this.numberChunk = MapGenerator.this.numberChunk + 1;
                i++;
            }
        }
    }
}
