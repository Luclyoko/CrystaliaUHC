package fr.luclyoko.crystaliauhc.listeners;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobsListeners implements Listener {
    private final Main main;

    private final GameManager gameManager;

    public MobsListeners(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.isCancelled())
            return;
        if (event.getLocation().getWorld().equals(this.main.getMapManager().getLobby()))
            event.setCancelled(true);
    }
}
