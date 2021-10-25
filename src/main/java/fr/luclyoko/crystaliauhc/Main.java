package fr.luclyoko.crystaliauhc;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.map.MapManager;
import fr.luclyoko.crystaliauhc.modules.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private MapManager mapManager;
    private GameManager gameManager;

    @Override
    public void onLoad() {
        File file = new File(getDataFolder().getParentFile().getParentFile(), "world");
        deleteWorld(file);
        this.mapManager = new MapManager(this);
        this.mapManager.modifyBiomes();
    }

    @Override
    public void onEnable() {
        instance = this;
        new Commands(this).registerAll();
        gameManager = new GameManager(this);
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
