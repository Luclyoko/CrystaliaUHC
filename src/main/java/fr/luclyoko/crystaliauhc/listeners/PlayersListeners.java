package fr.luclyoko.crystaliauhc.listeners;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayersListeners implements Listener {

    private final Main main;
    private final GameManager gameManager;

    public PlayersListeners(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        main.getScoreboardManager().onLogin(player);

        event.setJoinMessage("§8(§a+§8) " + player.getName() + " [§9" + Bukkit.getOnlinePlayers().size() + "§8/§9" + "40" + "§8]");
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 155, 0.5));
        player.setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        main.getScoreboardManager().onLogout(player);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;

        if (!gameManager.isStarted()) {
            event.setCancelled(true);
            return;
        }
    }
}
