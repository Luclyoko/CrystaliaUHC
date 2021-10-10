package fr.luclyoko.crystaliauhc;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.map.MapManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private MapManager mapManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        mapManager = new MapManager(this);
        gameManager = new GameManager(this);
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
}
