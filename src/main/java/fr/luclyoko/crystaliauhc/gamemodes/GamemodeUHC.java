package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.teams.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GamemodeUHC implements Listener {
    protected final Main main;

    protected final GameManager gameManager;

    protected UHCGamemodes gamemodeEnum;

    protected String defaultName;

    protected String displayName;

    protected int maxTeamSize;

    protected Class<? extends GuiBuilder> configGui;

    public GamemodeUHC(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
        this.configGui = null;
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
    }

    public List<String> getOptionalScoreboardLines(CrystaliaPlayer crystaliaPlayer) {
        return new ArrayList<>();
    }

    public UHCGamemodes getGamemodeEnum() {
        return this.gamemodeEnum;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDefaultName() {
        return this.defaultName;
    }

    public String getDisplayNameChat() {
        return this.defaultName + " ";
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Class<? extends GuiBuilder> getConfigGui() {
        return this.configGui;
    }

    public int getMaxTeamSize() {
        return this.maxTeamSize;
    }

    public void checkWin() {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        if (!this.gameManager.isStarted())
            return;
        if (!this.gameManager.getGameState().equals(GameState.PLAYING))
            return;
        List<CrystaliaPlayer> playersLeft = this.main.getPlayerManager().getOnlineAlivePlayers();
        if (this.main.getTeamManager().getTeamsSize() <= 1) {
            if (playersLeft.size() == 1) {
                finish(((CrystaliaPlayer)playersLeft.get(0)).getPlayerName());
            } else if (playersLeft.size() < 1) {
                finish("?");
            }
        } else {
            Team lastTested = null;
            for (CrystaliaPlayer crystaliaPlayer : playersLeft) {
                if (lastTested == null) {
                    lastTested = crystaliaPlayer.getTeam();
                    continue;
                }
                if (!lastTested.equals(crystaliaPlayer.getTeam()))
                    return;
            }
            assert lastTested != null;
            finish(lastTested.getTeamColor().getDisplayColor() + lastTested.getTeamSymbol().getSymbol() + " " + lastTested.getTeamColor().getDisplayName());
        }
    }

    public void finish(String winner) {
        this.gameManager.getGameTask().cancel();
        this.gameManager.setGameState(GameState.FINISHED);
        this.main.getMapManager().loadSpawnSchematic();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.teleport(new Location(this.gameManager.getGameWorld().getWorld(), 0.5D, 153.0D, 0.5D));
            player.playSound(player.getLocation(), Sound.GHAST_DEATH, 1.0F, 1.0F);
        });
        Bukkit.broadcastMessage(getDisplayNameChat() + "§aLa partie est terminée. \nVictoire de: §f§l" + winner);
        Bukkit.getOnlinePlayers().forEach(player -> this.main.getTitle().sendTitle(player, Integer.valueOf(20), Integer.valueOf(60), Integer.valueOf(20), "§6Fin de la partie !", "§aVictoire de : §f§l" + winner));
        this.gameManager.getGameSettings().setWinner(winner);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        if (!this.gameManager.isStarted())
            return;
        final Player player = event.getEntity();
        final CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
        final Optional<CrystaliaPlayer> opKiller = Optional.ofNullable(this.main.getPlayerManager().getExactPlayer(player.getKiller()));
        event.setDeathMessage(null);
        (new BukkitRunnable() {
            public void run() {
                player.spigot().respawn();
                if (opKiller.isPresent()) {
                    GamemodeUHC.this.main.getServer().getPluginManager().callEvent((Event)new GamePlayerDefinitiveDeathEvent(crystaliaPlayer, opKiller.get()));
                } else {
                    GamemodeUHC.this.main.getServer().getPluginManager().callEvent((Event)new GamePlayerDefinitiveDeathEvent(crystaliaPlayer, null));
                }
            }
        }).runTaskLater((Plugin)this.main, 20L);
    }

    @EventHandler
    public void onDefinitiveDeath(GamePlayerDefinitiveDeathEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        CrystaliaPlayer crystaliaPlayer = event.getCrystaliaPlayer();
        Optional<CrystaliaPlayer> opKiller = Optional.ofNullable(event.getKiller());
        if (opKiller.isPresent()) {
            CrystaliaPlayer killer = opKiller.get();
            if (this.main.getPlayerManager().getAliveGamePlayers().contains(killer)) {
                Bukkit.broadcastMessage(getDisplayNameChat() +
                        "§r" +
                        ((this.main.getTeamManager().getTeamsSize() > 1) ? (crystaliaPlayer.getTeam().getTeamColor().getDisplayColor() + crystaliaPlayer.getTeam().getTeamSymbol().getSymbol() + " ") : "§f") +
                        crystaliaPlayer.getPlayerName() +
                        " §7a été tué par " +
                        ((this.main.getTeamManager().getTeamsSize() > 1) ? (killer.getTeam().getTeamColor().getDisplayColor() + killer.getTeam().getTeamSymbol().getSymbol() + " ") : "§f") +
                        killer.getPlayerName());
            } else {
                Bukkit.broadcastMessage(getDisplayNameChat() +
                        "§r" +
                        ((this.main.getTeamManager().getTeamsSize() > 1) ? (crystaliaPlayer.getTeam().getTeamColor().getDisplayColor() + crystaliaPlayer.getTeam().getTeamSymbol().getSymbol() + " ") : "§f") +
                        crystaliaPlayer.getPlayerName() +
                        " §7est mort.");
            }
        } else {
            Bukkit.broadcastMessage(getDisplayNameChat() +
                    "§r" +
                    ((this.main.getTeamManager().getTeamsSize() > 1) ? (crystaliaPlayer.getTeam().getTeamColor().getDisplayColor() + crystaliaPlayer.getTeam().getTeamSymbol().getSymbol() + " ") : "§f") +
                    crystaliaPlayer.getPlayerName() +
                    " §7est mort.");
        }
        crystaliaPlayer.sendMessage(getDisplayNameChat() + "§aVous êtes mort et allez donc être placé en spectateur.");
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player1.getLocation(), Sound.WITHER_DEATH, 1.0F, 1.0F));
        crystaliaPlayer.setAlive(false);
        if (this.main.getTeamManager().getTeamsSize() > 1 && crystaliaPlayer.getTeam().getMembers().stream().noneMatch(CrystaliaPlayer::isAlive)) {
            final Team team = crystaliaPlayer.getTeam();
            (new BukkitRunnable() {
                public void run() {
                    Bukkit.broadcastMessage(GamemodeUHC.this.gameManager.getGamemodeUhc().getDisplayNameChat() +
                            "§aL'équipe " +
                            team.getTeamColor().getDisplayColor() +
                            team.getTeamSymbol().getSymbol() +
                            " " +
                            team.getTeamColor().getDisplayName() + " §aest éliminée.");
                }
            }).runTaskLater((Plugin)this.main, 1L);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!this.gameManager.getGamemodeUhc().equals(this))
            return;
        Player player = event.getPlayer();
        event.setRespawnLocation(this.gameManager.getGameWorld().getCenter());
        Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> player.setGameMode(GameMode.SPECTATOR), 1L);
        checkWin();
    }
}
