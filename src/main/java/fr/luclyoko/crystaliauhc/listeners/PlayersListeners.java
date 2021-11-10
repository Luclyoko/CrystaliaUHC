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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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

        event.setJoinMessage("§7(§a+§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + main.getServer().getMaxPlayers() + "§7]");
        player.teleport(new Location(gameManager.getGameWorld().getWorld(), 0.5, 160, 0.5));
        player.setGameMode(GameMode.ADVENTURE);
        player.setDisplayName("§8» §r" + player.getName());

        main.getPlayerManager().registerPlayer(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage("§7(§c-§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + main.getServer().getMaxPlayers() + "§7]");

        main.getScoreboardManager().onLogout(player);

        if (gameManager.isStarted()) main.getPlayerManager().getExactPlayer(player).setOnline(false);
        else main.getPlayerManager().deletePlayer(player);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;

        if (!gameManager.isStarted()) event.setCancelled(true);

        if (event.getEntity() instanceof Player && !gameManager.getGameSettings().getInvincibility().hasTriggered()) event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.isCancelled()) return;

        if (!gameManager.isStarted()) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;

        if (!gameManager.getGameSettings().getPvp().hasTriggered()) event.setCancelled(true);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setFormat(event.getPlayer().getDisplayName() + "§7: §r" + event.getMessage());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        if (!gameManager.isStarted()) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        if (!gameManager.isStarted()) event.setCancelled(true);
    }
}
