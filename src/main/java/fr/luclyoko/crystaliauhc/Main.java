package fr.luclyoko.crystaliauhc;

import fr.luclyoko.crystaliauhc.map.MapManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public MapManager mapManager;

    @Override
    public void onEnable() {
        mapManager = new MapManager(this);
    }

    @Override
    public void onDisable() {

    }
}
