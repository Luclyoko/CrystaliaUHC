package fr.luclyoko.crystaliauhc.gamemodes.arena;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.game.TeleportationTask;
import fr.luclyoko.crystaliauhc.game.timers.Border;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import fr.luclyoko.crystaliauhc.gamemodes.arena.inventories.InventoryManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesManager;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameStartingEvent;
import fr.luclyoko.crystaliauhc.map.GameWorld;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class ArenaUHC extends GamemodeUHC {

    private final ArenaRolesManager arenaRolesManager;
    private final InventoryManager inventoryManager;
    public ArenaUHC(Main main, GameManager gameManager) {
        super(main, gameManager);
        this.defaultName = "§bCrystArena";
        this.displayName = getDefaultName();
        this.gamemodeEnum = UHCGamemodes.ARENA;
        this.maxTeamSize = 1;
        this.arenaRolesManager = new ArenaRolesManager(main, gameManager);
        this.inventoryManager = new InventoryManager(this);
        main.getTeamManager().setTeamsSize(1);
        main.getTeamManager().resetTeams();
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            player1.getInventory().remove(Material.BANNER);
            if (player1.getWorld().getName().equals("world"))
                player1.teleport(main.getMapManager().getLobby().getHighestBlockAt(0, 0).getLocation().add(0.5D, 5.0D, 0.5D));
        });
        main.getServer().unloadWorld("gameworld", false);
        main.getMapManager().deleteWorld(new File(main.getDataFolder().getParentFile().getParentFile(), "gameworld"));
        gameManager.getGameSettings().setDayNightCycle(300);
        gameManager.getGameSettings().setEternalDay(false);
        gameManager.getGameSettings().setDay(true);
        gameManager.getGameSettings().setDayNightCycle(180);
        gameManager.getGameSettings().setStrengthPercent(20);
        ((Border)gameManager.getGameSettings().getBorder()).setBorderInitialSize(100);
        ((Border)gameManager.getGameSettings().getBorder()).setBorderFinalSize(100);
        gameManager.setGameWorld(new GameWorld(main, "gameworld", 0, 0, Biome.ROOFED_FOREST));
    }

    public List<String> getOptionalScoreboardLines(CrystaliaPlayer crystaliaPlayer) {
        if (this.gameManager.getGameState().equals(GameState.PLAYING) && crystaliaPlayer.isAlive() && crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole() instanceof ArenaRole) {
            SimpleDateFormat timers = new SimpleDateFormat("mm:ss");
            timers.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            return Arrays.asList(" §8» §3Rôle: §r" + crystaliaPlayer.getRole().getName(),
                    " §8» §aKills: §r" + ((ArenaRole)crystaliaPlayer.getRole()).getKills());
        }
        return super.getOptionalScoreboardLines(crystaliaPlayer);
    }

    public ArenaRolesManager getArenaRolesManager() {
        return arenaRolesManager;
    }

    @Override
    public void start(Player host) {
        host.sendMessage(getDisplayNameChat() + "§aInitialisation de l'arène.");
        if (gameManager.getGameSettings().isDay()) {
            gameManager.getGameWorld().getWorld().setTime(0L);
        } else {
            gameManager.getGameWorld().getWorld().setTime(12000L);
        }
        gameManager.getGameWorld().getWorldBorder().setSize((((Border)gameManager.getGameSettings().getBorder()).getBorderInitialSize() * 2));
        gameManager.setGameState(GameState.PLAYING);
        gameManager.setStarted(true);
        gameManager.getGameWorld().getWorld().setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getScheduler().runTaskLater(main, () -> gameManager.getGameWorld().getWorld().setDifficulty(Difficulty.NORMAL), 20);
        gameManager.getGameTask().init();
        main.getServer().getPluginManager().callEvent(new GameStartingEvent());
        this.main.getCommand("arena").setExecutor(new ArenaCommand(this.main));
    }

    @Override
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!gameManager.getGamemodeUhc().equals(this)) return;
        if (!gameManager.isStarted()) return;

        Player player = event.getEntity();
        CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
        if (player.getKiller() != null && player.getKiller() != player) {
            Player killer = event.getEntity().getKiller();
            killer.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE).setAmount(3).toItemStack());
            CrystaliaPlayer cKiller = main.getPlayerManager().getExactPlayer(killer);
            if (cKiller.getRole() != null && cKiller.getRole() instanceof ArenaRole) ((ArenaRole)cKiller.getRole()).increaseKills();
            if (killer.getHealth() < killer.getMaxHealth() - 6) killer.setHealth(killer.getHealth() + 6);
            else killer.setHealth(killer.getMaxHealth());
            event.setDeathMessage(getDisplayNameChat() + "§7" + crystaliaPlayer.getPlayerName() + " (§e" + crystaliaPlayer.getRole().getName() + "§7) a été tué par " + cKiller.getPlayerName() + ".");
        } else {
            event.setDeathMessage(getDisplayNameChat() + "§7" + crystaliaPlayer.getPlayerName() + " (§e" + crystaliaPlayer.getRole().getName() + "§7) est mort.");
            arenaRolesManager.addForcedRole(crystaliaPlayer, ((ArenaRole)crystaliaPlayer.getRole()).getArenaRolesEnum());
        }
        if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole() instanceof ArenaRole) {
            ArenaRole arenaRole = (ArenaRole) crystaliaPlayer.getRole();
            if (arenaRole.isSilent()) event.setDeathMessage("");
        if (crystaliaPlayer.isAlive() && crystaliaPlayer.isOnline()) {
            arenaRolesManager.removePlayingRole(arenaRole.getArenaRolesEnum());
            arenaRole.resetEffects();
            crystaliaPlayer.setRole(null);
            crystaliaPlayer.setAlive(false);
            event.setKeepInventory(true);
            Bukkit.getScheduler().runTaskLater(main, () -> player.spigot().respawn(), 10);
        }
        }
    }

    @Override
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        Player player = event.getPlayer();
        event.setRespawnLocation(this.main.getMapManager().getLobby().getBlockAt(0, this.main.getMapManager().getLobby().getHighestBlockYAt(0, 0), 0).getLocation().add(0.5D, 5.0D, 0.5D));
        player.setFoodLevel(20);
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.ADVENTURE);
        player.setWalkSpeed(0.2f);
        player.setAllowFlight(false);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("§6Entrer dans l'arène")
                .toItemStack());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        if (!this.gameManager.isStarted())
            return;
        Player player = event.getPlayer();
        CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player);
        if (!crystaliaPlayer.isAlive() && crystaliaPlayer.isOnline()) {
            Action action = event.getAction();
            if ((player.getItemInHand().getType().equals(Material.NETHER_STAR) && player.getItemInHand().getItemMeta().getDisplayName().equals("§6Entrer dans l'arène")) && (
                    action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
                crystaliaPlayer.setAlive(true);
                player.teleport(TeleportationTask.generateRandomLocation());
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();
                arenaRolesManager.pickRole(crystaliaPlayer, true);
                ArenaRole arenaRole = (ArenaRole) crystaliaPlayer.getRole();
                inventoryManager.fillInventory(crystaliaPlayer);
                if (!arenaRole.getRoleItems().isEmpty()) arenaRole.getRoleItems().forEach(itemStack -> player.getInventory().addItem(itemStack));
                crystaliaPlayer.getRole().setInvincible(true);
                Bukkit.getScheduler().runTaskLater(main, () -> {
                    if (crystaliaPlayer.isOnline() && crystaliaPlayer.isAlive()) {
                        crystaliaPlayer.getRole().setInvincible(false);
                        crystaliaPlayer.sendMessage("§aVous pouvez désormais prendre des dégâts.");
                    }
                }, 3*20);
                crystaliaPlayer.getRole().updatePlayerAttributes();
            }
        }

    }

    @EventHandler
    public void onGameStart(GameStartingEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player);
            crystaliaPlayer.setAlive(false);
            player.setFoodLevel(20);
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[3]);
            player.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR)
                    .setDisplayName("§6Entrer dans l'arène")
                    .toItemStack());
        }
        gameManager.getGameSettings().getPvp().init(false);
        gameManager.getGameSettings().getInvincibility().init(false);
        gameManager.getGameSettings().getBorder().init(false);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.isCancelled())
            return;
        if (!gameManager.isStarted()) return;
        if (!gameManager.getGamemodeUhc().equals(this)) return;
        if (event.getEntityType().equals(EntityType.DROPPED_ITEM)) return;
        if (event.getLocation().getWorld().equals(gameManager.getGameWorld().getWorld()))
            event.setCancelled(true);
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
