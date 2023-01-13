package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.timers.Border;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameStartingEvent;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeleportationTask extends BukkitRunnable {
    private Main main;

    private GameManager gameManager;

    private List<CrystaliaPlayer> playersToTP;

    public TeleportationTask(Main main, GameManager gameManager, List<CrystaliaPlayer> playersToTP) {
        this.main = main;
        this.gameManager = gameManager;
        this.playersToTP = new ArrayList<>(playersToTP);
        gameManager.getGameWorld().getWorld().setDifficulty(Difficulty.NORMAL);
        if (gameManager.getGameSettings().isDay()) {
            gameManager.getGameWorld().getWorld().setTime(0L);
        } else {
            gameManager.getGameWorld().getWorld().setTime(12000L);
        }
        gameManager.getGameWorld().getWorldBorder().setSize((((Border)gameManager.getGameSettings().getBorder()).getBorderInitialSize() * 2));
        if (main.getTeamManager().getTeamsSize() > 1)
            for (Team team : main.getTeamManager().getTeams())
                team.setSpawnLoc(generateRandomLocation());
    }

    public void run() {
        if (!this.playersToTP.isEmpty()) {
            CrystaliaPlayer crystaliaPlayer = this.playersToTP.get(0);
            Player player = Bukkit.getPlayer(crystaliaPlayer.getPlayerUUID());
            player.getInventory().setContents(this.main.getGameManager().getGameSettings().getStartInventory());
            player.getInventory().setArmorContents(this.main.getGameManager().getGameSettings().getStartArmor());
            player.setSaturation(20.0F);
            player.setFoodLevel(20);
            player.setMaxHealth(20.0D);
            player.setHealth(20.0D);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 255, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 255, false, false));
            Bukkit.getOnlinePlayers().forEach(player1 -> this.main.getTitle().sendActionBar(player1, "§3Téléportation de §b" + player.getName()));
            if (this.main.getTeamManager().getTeamsSize() == 1) {
                player.teleport(generateRandomLocation());
            } else {
                player.teleport(crystaliaPlayer.getTeam().getSpawnLoc());
            }
            this.playersToTP.remove(crystaliaPlayer);
        } else {
            for (CrystaliaPlayer crystaliaPlayer : this.main.getPlayerManager().getCrystaliaPlayers()) {
                Player player = Bukkit.getPlayer(crystaliaPlayer.getPlayerUUID());
                if (!crystaliaPlayer.isSpec()) {
                    for (PotionEffect potionEffect : player.getActivePotionEffects())
                        player.removePotionEffect(potionEffect.getType());
                    player.setGameMode(GameMode.SURVIVAL);
                    continue;
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(this.gameManager.getGameWorld().getCenter());
            }
            Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLa partie commence, bonne chance à tous.");
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1.0F, 1.0F);
                this.main.getTitle().sendTitle(player, 20, 30, 20, this.gameManager.getGamemodeUhc().getDisplayName(), "§aDébut de la partie !");
            });
            this.gameManager.setGameState(GameState.PLAYING);
            this.gameManager.setStarted(true);
            this.main.getMapManager().unloadSpawnSchematic();
            this.main.getServer().getPluginManager().callEvent(new GameStartingEvent());
            this.gameManager.getGameTask().init();
            cancel();
        }
    }

    public static Location generateRandomLocation() {
        Random random = new Random();
        GameManager gameManager = Main.getInstance().getGameManager();
        boolean xSign = random.nextBoolean();
        boolean zSign = random.nextBoolean();
        int xCoordinates = random.nextInt((int)gameManager.getGameWorld().getWorldBorder().getSize() / 2);
        int zCoordinates = random.nextInt((int)gameManager.getGameWorld().getWorldBorder().getSize() / 2);
        int xValue = xSign ? xCoordinates : -xCoordinates;
        int zValue = zSign ? zCoordinates : -zCoordinates;
        return gameManager.getGameSettings().isDevMode() ? gameManager
                .getGameWorld().getWorld().getHighestBlockAt(gameManager.getGameWorld().getCenter()).getLocation().add(0.5D, 5.0D, 0.5D) : gameManager
                .getGameWorld().getWorld().getHighestBlockAt(xValue, zValue).getLocation().add(0.5D, 5.0D, 0.5D);
    }
}
