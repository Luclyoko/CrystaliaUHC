package fr.luclyoko.crystaliauhc.listeners;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.guis.teamsguis.TeamSelectorGui;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayersListeners implements Listener {
    private final Main main;

    private final GameManager gameManager;

    public PlayersListeners(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.main.getScoreboardManager().onLogin(player);
        if (!this.gameManager.isStarted()) {
            event.setJoinMessage("§7(§a+§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
            this.main.getPlayerManager().registerPlayer(player);
            (new BukkitRunnable() {
                public void run() {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setMaxHealth(20.0D);
                    player.setHealth(20.0D);
                    player.setFoodLevel(20);
                    player.setSaturation(20.0F);
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(new ItemStack[4]);
                    player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                    player.teleport(PlayersListeners.this.main.getMapManager().getLobby().getHighestBlockAt(0, 0).getLocation().add(0.5D, 5.0D, 0.5D));
                }
            }).runTaskLater((Plugin)this.main, 1L);
            CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
            player.setScoreboard(crystaliaPlayer.getScoreboard());
            if (this.main.getTeamManager().getTeamsSize() > 1)
                player.getInventory().setItem(0, this.main.getTeamManager().getTeamsBannerSelection(crystaliaPlayer));
        } else {
            if (!this.main.getPlayerManager().getCrystaliaPlayers().contains(this.main.getPlayerManager().getExactPlayer(player)))
                this.main.getPlayerManager().registerPlayer(player);
            CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
            player.setScoreboard(crystaliaPlayer.getScoreboard());
            if (gameManager.getGamemodeUhc().getGamemodeEnum().equals(UHCGamemodes.ARENA)) {
                crystaliaPlayer.setAlive(true);
                ((ArenaUHC)gameManager.getGamemodeUhc()).getArenaRolesManager().pickRole(crystaliaPlayer, false);
                player.setHealth(0);
                event.setJoinMessage("§7(§a+§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
                return;
            }
            if (crystaliaPlayer.isSpec() || !crystaliaPlayer.isAlive()) {
                event.setJoinMessage("§7(§a+§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
                (new BukkitRunnable() {
                    public void run() {
                        player.teleport(PlayersListeners.this.gameManager.getGameWorld().getCenter());
                        player.setGameMode(GameMode.SPECTATOR);
                        player.setMaxHealth(20.0D);
                        player.setHealth(20.0D);
                        player.setFoodLevel(20);
                        player.setSaturation(20.0F);
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(new ItemStack[4]);
                        player.sendMessage(PlayersListeners.this.main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aLa partie a déjà commencé, vous avez donc été placé en spectateur.");
                    }
                }).runTaskLater(this.main, 1L);
            } else {
                event.setJoinMessage("§7(§a+§7) " + player.getName() + " s'est reconnecté [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
                        crystaliaPlayer.getIdleTask().cancel();
            }
        }
        this.main.getTeamManager().refreshTeams();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.main.getScoreboardManager().onLogout(player);
        if (!this.gameManager.isStarted()) {
            event.setQuitMessage("§7(§c-§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
            this.main.getPlayerManager().deletePlayer(player);
        } else if (!gameManager.getGamemodeUhc().getGamemodeEnum().equals(UHCGamemodes.ARENA)) {
            final CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
            if (crystaliaPlayer.isAlive()) {
                event.setQuitMessage("§7(§c-§7) " + player.getName() + " s'est déconnecté, il a §3" + (this.gameManager.getGameSettings().getIdleTime() / 60) + " §7minute(s) pour se reconnecter [§3" + (Bukkit.getOnlinePlayers().size() - 1) + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
                crystaliaPlayer.setPlayerInvContents(player.getInventory().getContents());
                crystaliaPlayer.setPlayerArmorContents(player.getInventory().getArmorContents());
                crystaliaPlayer.setDeathLocation(player.getLocation());
                crystaliaPlayer.setIdleTask(new BukkitRunnable() {
                    public void run() {
                        crystaliaPlayer.setAlive(false);
                        PlayersListeners.this.main.getServer().getPluginManager().callEvent(new GamePlayerDefinitiveDeathEvent(crystaliaPlayer, null));
                    }
                });
                crystaliaPlayer.getIdleTask().runTaskLater((Plugin)this.main, this.gameManager.getGameSettings().getIdleTime() * 20L);
            } else {
                event.setQuitMessage("§7(§c-§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
            }
        } else {
            final CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole() instanceof ArenaRole) {
                ArenaRole arenaRole = (ArenaRole) crystaliaPlayer.getRole();
                ((ArenaUHC)gameManager.getGamemodeUhc()).getArenaRolesManager().addForcedRole(crystaliaPlayer, arenaRole.getArenaRolesEnum());
                arenaRole.resetEffects();
                crystaliaPlayer.setRole(null);
                crystaliaPlayer.setAlive(false);
            }
            event.setQuitMessage("§7(§c-§7) " + player.getName() + " [§3" + Bukkit.getOnlinePlayers().size() + "§8/§3" + this.gameManager.getGameSettings().getSlots() + "§7]");
        }
        this.main.getTeamManager().refreshTeams();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (this.gameManager.isStarted())
            return;
        Action action = event.getAction();
        if (player.getItemInHand().getType().equals(Material.BANNER) && (
                action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)))
            this.main.getGuiManager().open(player, TeamSelectorGui.class);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted() || event.getEntity().getWorld().equals(this.main.getMapManager().getLobby()))
            event.setCancelled(true);
        if (event.getEntity() instanceof Player && event.getCause().equals(EntityDamageEvent.DamageCause.VOID) && event.getEntity().getWorld().equals(this.main.getMapManager().getLobby()))
            event.getEntity().teleport(this.main.getMapManager().getLobby().getHighestBlockAt(0, 0).getLocation().add(0.5D, 5.0D, 0.5D));
        if (event.getEntity() instanceof Player && !this.gameManager.getGameSettings().getInvincibility().hasTriggered())
            event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted() || event.getEntity().getWorld().equals(this.main.getMapManager().getLobby()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setFormat(event.getPlayer().getDisplayName() + "§8: §r" + event.getMessage());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted() || event.getBlock().getWorld().equals(this.main.getMapManager().getLobby()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted() || event.getBlock().getWorld().equals(this.main.getMapManager().getLobby()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted() || event.getPlayer().getWorld().equals(this.main.getMapManager().getLobby()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMoveItem(InventoryClickEvent event) {
        if (event.isCancelled())
            return;
        if (this.gameManager.isStarted())
            return;
        Player player = (Player)event.getWhoClicked();
        if (player.getGameMode().equals(GameMode.CREATIVE))
            return;
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(player.getInventory())) {
            Material material = event.getCurrentItem().getType();
            if (material.equals(Material.BANNER) &&
                    event.isRightClick())
                this.main.getGuiManager().open(player, TeamSelectorGui.class);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted())
            return;
        for (Enchantment enchantment : event.getEnchantsToAdd().keySet()) {
            if (event.getEnchantsToAdd().get(enchantment) > this.gameManager.getGameSettings().getEnchantLimit(enchantment)) {
                event.setCancelled(true);
                event.getEnchanter().sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cCet enchantement n'est pas autorisé.");
            }
        }
    }

    @EventHandler
    public void onAnvil(InventoryClickEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.isStarted())
            return;
        if (event.getInventory() instanceof AnvilInventory) {
            AnvilInventory anvilInventory = (AnvilInventory)event.getInventory();
            InventoryView inventoryView = event.getView();
            if (event.getSlot() == 2 && event.getClickedInventory() == inventoryView.getTopInventory()) {
                ItemStack result = event.getCurrentItem();
                for (Enchantment enchantment : result.getEnchantments().keySet()) {
                    if (result.getEnchantments().get(enchantment) > this.gameManager.getGameSettings().getEnchantLimit(enchantment)) {
                        event.setCancelled(true);
                        event.getWhoClicked().sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cCet enchantement n'est pas autorisé.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (!this.gameManager.getGameSettings().getPvp().hasTriggered() &&
                event.getEntity() instanceof Player && event.getDamager() instanceof Player)
            event.setCancelled(true);
        if (event.getDamager() instanceof Player) {
            Player player = (Player)event.getDamager();
            for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
                if (activePotionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                    int strengthPercent = this.gameManager.getGameSettings().getStrengthPercent() * (activePotionEffect.getAmplifier() + 1);
                    final CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
                    if (crystaliaPlayer.getRole() != null) {
                        strengthPercent += crystaliaPlayer.getRole().getSelfStrengthPercent();
                    }
                    event.setDamage(event.getDamage() / 2.299999952316284D * (1.0F + strengthPercent / 100.0F));
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
                if (activePotionEffect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
                    int resiPercent = this.gameManager.getGameSettings().getResistancePercent() * (activePotionEffect.getAmplifier() + 1);
                    final CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
                    if (crystaliaPlayer.getRole() != null) {
                        resiPercent += crystaliaPlayer.getRole().getSelfResistancePercent();
                    }
                    if (resiPercent >= 100)
                        event.setCancelled(true);
                    event.setDamage(event.getDamage() * (100 - resiPercent) / 80.0D);
                }
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMaxPlayers(this.gameManager.getGameSettings().getSlots());
        event.setMotd("§3§lCrystalia §8» Bienvenue sur le serveur !\n§aMode de jeu §8» " + this.gameManager.getGamemodeUhc().getDisplayName());
    }
}
