package fr.luclyoko.crystaliauhc;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.map.GameWorld;
import fr.luclyoko.crystaliauhc.map.MapManager;
import fr.luclyoko.crystaliauhc.modules.Commands;
import fr.luclyoko.crystaliauhc.modules.Listeners;
import fr.luclyoko.crystaliauhc.utils.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private ScheduledExecutorService executorMonoThread;
    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    private ScheduledExecutorService scheduledExecutorService;
    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    private ScoreboardManager scoreboardManager;
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    private MapManager mapManager;
    private GameManager gameManager;

    public Main () {
        instance = this;
    }

    @Override
    public void onLoad() {
        File file = new File(getDataFolder().getParentFile().getParentFile(), "world");
        deleteWorld(file);
        this.mapManager = new MapManager(this);
        this.mapManager.modifyBiomes();
        this.gameManager = new GameManager(this, new GameWorld(Bukkit.getWorld("world"), 0, 0));
    }

    @Override
    public void onEnable() {
        new Commands(this).registerAll();
        new Listeners(this).registerAll();
        this.executorMonoThread = Executors.newScheduledThreadPool(1);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.scoreboardManager = new ScoreboardManager();
        getLogger().info("Starting CrystaliaUHC plugin...");
    }

    @Override
    public void onDisable() {

    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    private boolean deleteWorld(File path) {
        if (path.exists()) {
            final File[] files = path.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return path.delete();
    }
}
