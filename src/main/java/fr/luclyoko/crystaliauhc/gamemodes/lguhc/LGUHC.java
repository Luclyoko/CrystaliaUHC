package fr.luclyoko.crystaliauhc.gamemodes.lguhc;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDeathEvent;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerKillEvent;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameStartingEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGInfectionEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGNewLoupEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGRevealListeLoupsEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRolesManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.gamemodesguis.lgguis.LGMainGui;
import fr.luclyoko.crystaliauhc.map.GameWorld;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.timers.Timer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class LGUHC extends GamemodeUHC {
    private final LGRolesManager rolesManager;

    private boolean isListeLoupsRevealed;

    public LGUHC(Main main, GameManager gameManager) {
        super(main, gameManager);
        this.defaultName = "§cLG UHC";
        this.gamemodeEnum = UHCGamemodes.LG_UHC;
        this.maxTeamSize = 1;
        this.displayName = getDefaultName();
        this.rolesManager = new LGRolesManager(main, gameManager, 1200);
        this.isListeLoupsRevealed = false;
        main.getTeamManager().setTeamsSize(1);
        main.getTeamManager().resetTeams();
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            player1.getInventory().remove(Material.BANNER);
            if (player1.getWorld().getName().equals("world"))
                player1.teleport(main.getMapManager().getLobby().getHighestBlockAt(0, 0).getLocation().add(0.5D, 5.0D, 0.5D));
        });
        main.getServer().unloadWorld("gameworld", false);
        main.getMapManager().deleteWorld(new File(main.getDataFolder().getParentFile().getParentFile(), "gameworld"));
        Random random = new Random(System.currentTimeMillis());
        gameManager.getGameSettings().setDayNightCycle(300);
        gameManager.getGameSettings().setEternalDay(false);
        gameManager.getGameSettings().setDay(true);
        gameManager.setGameWorld(new GameWorld(main, "gameworld", 0, 0, Biome.ROOFED_FOREST));
    }

    public List<String> getOptionalScoreboardLines(CrystaliaPlayer crystaliaPlayer) {
        if (!this.gameManager.getGameState().equals(GameState.PLAYING))
            return super.getOptionalScoreboardLines(crystaliaPlayer);
        SimpleDateFormat timers = new SimpleDateFormat("mm:ss");
        timers.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return Collections.singletonList(" §8» §3Rôle: §r" + (this.rolesManager.hasTriggered() ? (((LGRole)crystaliaPlayer.getRole()).getLgSide().getColor() + crystaliaPlayer.getRole().getName()) : timers.format(Integer.valueOf((this.rolesManager.getTriggerTime() - this.gameManager.getGameTask().getTimer()) * 1000))));
    }

    public void checkWin() {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        if (!this.gameManager.isStarted())
            return;
        if (!this.gameManager.getGameState().equals(GameState.PLAYING))
            return;
        List<LGSides> aliveSides = new ArrayList<>();
        for (CrystaliaPlayer crystaliaPlayer : this.main.getPlayerManager().getOnlineAlivePlayers()) {
            LGSides playerSide = ((LGRole)crystaliaPlayer.getRole()).getLgSide();
            if (!aliveSides.contains(playerSide))
                aliveSides.add(playerSide);
        }
        if (aliveSides.size() == 1)
            finish(aliveSides.get(0).getDisplayName());
    }

    public void finish(String winner) {
        this.gameManager.getGameTask().cancel();
        this.gameManager.setGameState(GameState.FINISHED);
        this.main.getMapManager().loadSpawnSchematic();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.teleport(new Location(this.gameManager.getGameWorld().getWorld(), 0.5D, 153.0D, 0.5D));
            player.playSound(player.getLocation(), Sound.GHAST_DEATH, 1.0F, 1.0F);
        });
        Bukkit.broadcastMessage(getDisplayNameChat() + "§aLa partie est terminée. \nVictoire de: §f§l" + winner + "\n§a§lRécapitulatif des rôles:");
        for (CrystaliaPlayer gamePlayer : this.main.getPlayerManager().getGamePlayers()) {
            LGRole playerRole = (LGRole)gamePlayer.getRole();
            TextComponent msg = new TextComponent((gamePlayer.isAlive() ? "§9" : "§9§m") + gamePlayer.getPlayerName() + ": " + playerRole.getFullName());
            StringBuilder events = new StringBuilder(playerRole.getName() + "\n");
            for (String event : playerRole.getEvents())
                events.append("\n").append(event);
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(events.toString())).create()));
            Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage((BaseComponent)msg));
        }
        Bukkit.getOnlinePlayers().forEach(player -> this.main.getTitle().sendTitle(player, 20, 60, 20, "§6Fin de la partie !", "§aVictoire de: §f§l" + winner));
        this.gameManager.getGameSettings().setWinner(winner);
    }

    public Class<? extends GuiBuilder> getConfigGui() {
        return (Class)LGMainGui.class;
    }

    public LGRolesManager getRolesManager() {
        return this.rolesManager;
    }

    public boolean isListeLoupsRevealed() {
        return this.isListeLoupsRevealed;
    }

    public void setListeLoupsRevealed(boolean listeLoupsRevealed) {
        this.isListeLoupsRevealed = listeLoupsRevealed;
    }

    @EventHandler
    public void onGameStart(GameStartingEvent event) {
        this.gameManager.getGameSettings().addTimer((Timer)this.rolesManager);
        this.main.getCommand("lg").setExecutor((CommandExecutor)new LGCommand(this.main));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        if (!this.gameManager.isStarted())
            return;
        Player player = event.getEntity();
        CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
        event.setDeathMessage(null);
        if (!this.main.getPlayerManager().getOnlineAlivePlayers().contains(crystaliaPlayer)) {
            player.spigot().respawn();
            return;
        }
        crystaliaPlayer.setPlayerInvContents(player.getInventory().getContents());
        crystaliaPlayer.setPlayerArmorContents(player.getInventory().getArmorContents());
        crystaliaPlayer.setDeathLocation(player.getLocation());
        crystaliaPlayer.setAlive(false);
        event.setKeepInventory(true);
        if (!this.rolesManager.hasTriggered()) {
            this.main.getServer().getPluginManager().callEvent((Event)new GamePlayerDefinitiveDeathEvent(crystaliaPlayer, null));
            return;
        }
        Player killer = event.getEntity().getKiller();
        CrystaliaPlayer cKiller = null;
        if (event.getEntity().getKiller() != null) {
            cKiller = this.main.getPlayerManager().getExactPlayer(killer);
            this.main.getServer().getPluginManager().callEvent((Event)new GamePlayerKillEvent(crystaliaPlayer, cKiller));
        }
        GamePlayerDeathEvent gamePlayerDeathEvent = new GamePlayerDeathEvent(crystaliaPlayer);
        this.main.getServer().getPluginManager().callEvent((Event)gamePlayerDeathEvent);
        player.sendMessage(getDisplayNameChat() + "§aVous êtes mort mais vous avez une chance d'être ressuscité dans les 10 prochaines secondes.");
        Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> player.spigot().respawn(), 20L);
        CrystaliaPlayer finalCKiller = cKiller;
        Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> this.main.getServer().getPluginManager().callEvent((Event)new GamePlayerDefinitiveDeathEvent(crystaliaPlayer, finalCKiller)), 200L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDefinitiveDeath(GamePlayerDefinitiveDeathEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        if (event.getCrystaliaPlayer().isAlive()) {
            event.setCancelled(true);
            return;
        }
        if (event.getKiller() != null) {
            ((LGRole)event.getKiller().getRole()).addEvent("Kill sur " + event.getCrystaliaPlayer().getPlayerName());
            ((LGRole)event.getCrystaliaPlayer().getRole()).addEvent("Tué par " + event.getKiller().getPlayerName());
        } else {
            ((LGRole)event.getCrystaliaPlayer().getRole()).addEvent("Mort de PvE");
        }
        if (!this.rolesManager.hasTriggered()) {
            this.main.getPlayerManager().getCrystaliaPlayers().forEach(crystaliaPlayer -> crystaliaPlayer.sendMessage("                   §r§c Mort §8§m                    \n§2Le joueur §l" + event.getCrystaliaPlayer().getPlayerName() + " §2est mort avant l'annonce des rôles.\n§8§m                                                 "));
            this.main.getLogger().info("Mort définitive de " + event.getCrystaliaPlayer().getPlayerName() + " avant l'annonce des rôles.");
        } else if (event.isDeathShown()) {
            this.main.getPlayerManager().getCrystaliaPlayers().forEach(crystaliaPlayer -> crystaliaPlayer.sendMessage("                   §r§c Mort §8§m                    \n§2Un membre du village est mort.\nLe joueur §l" + event.getCrystaliaPlayer().getPlayerName() + "§2, qui était §o" + (String)event.getShownRole().get(crystaliaPlayer) + "§2.\n§8§m                                               "));
            this.main.getLogger().info("Mort définitive de " + event.getCrystaliaPlayer().getPlayerName() + " qui était " + event.getCrystaliaPlayer().getRole().getName() + ".");
        }
        CrystaliaPlayer crystaliaPlayer = event.getCrystaliaPlayer();
        List<ItemStack> invContents = Arrays.asList(crystaliaPlayer.getPlayerInvContents());
        List<ItemStack> armorContents = Arrays.asList(crystaliaPlayer.getPlayerArmorContents());
        if (crystaliaPlayer.getDeathLocation() != null) {
            invContents.stream().filter(itemStack ->
                            (itemStack != null && !itemStack.getType().equals(Material.AIR)))
                    .forEach(itemStack -> crystaliaPlayer.getDeathLocation().getWorld().dropItem(crystaliaPlayer.getDeathLocation(), itemStack));
            armorContents.stream().filter(itemStack ->
                            (itemStack != null && !itemStack.getType().equals(Material.AIR)))
                    .forEach(itemStack -> crystaliaPlayer.getDeathLocation().getWorld().dropItem(crystaliaPlayer.getDeathLocation(), itemStack));
        }
        if (event.getCrystaliaPlayer().isOnline()) {
            Player player = event.getCrystaliaPlayer().getPlayer();
            player.setMaxHealth(20.0D);
            player.setHealth(20.0D);
            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[4]);
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(crystaliaPlayer.getDeathLocation().add(0.0D, 20.0D, 0.0D));
            player.sendMessage(getDisplayNameChat() + "§c§lVous êtes définitivement mort. Merci de penser vous muter sur Mumble.");
        }
        checkWin();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        Player player = event.getPlayer();
        event.setRespawnLocation(this.main.getMapManager().getLobby().getBlockAt(0, this.main.getMapManager().getLobby().getHighestBlockYAt(0, 0), 0).getLocation().add(0.5D, 5.0D, 0.5D));
        player.setFoodLevel(20);
        player.setHealth(player.getMaxHealth());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInfection(LGInfectionEvent event) {
        this.main.getServer().getPluginManager().callEvent((Event)new LGNewLoupEvent(event.getInfected()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onListeLoupsReveal(LGRevealListeLoupsEvent event) {
        setListeLoupsRevealed(true);
    }
}
