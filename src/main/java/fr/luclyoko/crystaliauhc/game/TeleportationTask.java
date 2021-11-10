package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.PlayerUtils;
import fr.luclyoko.crystaliauhc.utils.Reflection;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class TeleportationTask extends BukkitRunnable {

    private Main main;
    private GameManager gameManager;
    private List<CrystaliaPlayer> playersToTP;

    public TeleportationTask(Main main,
                             GameManager gameManager,
                             List<CrystaliaPlayer> playersToTP) {
        this.main = main;
        this.gameManager = gameManager;
        this.playersToTP = playersToTP;
    }

    @Override
    public void run() {
        if (!playersToTP.isEmpty()) {
            CrystaliaPlayer crystaliaPlayer = playersToTP.get(0);
            Player player = Bukkit.getPlayer(crystaliaPlayer.getPlayerUUID());

            Bukkit.getOnlinePlayers().forEach(player1 -> PlayerUtils.sendActionText(player1, "§3Téléportation de §b" + player.getName()));
            player.teleport(generateRandomLocation());
            playersToTP.remove(crystaliaPlayer);
        } else {
            for (CrystaliaPlayer crystaliaPlayer : main.getPlayerManager().getCrystaliaPlayers()) {
                Player player = Bukkit.getPlayer(crystaliaPlayer.getPlayerUUID());
                if (!crystaliaPlayer.isSpec()) {
                    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                        player.removePotionEffect(potionEffect.getType());

                    }
                    player.setGameMode(GameMode.SURVIVAL);
                } else {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(gameManager.getGameWorld().getCenter());
                }
            }
            gameManager.getGameWorld().getWorld().setDifficulty(Difficulty.NORMAL);
            gameManager.getGameWorld().getWorld().setTime(0);
            Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLa partie commence, bonne chance à tous.");
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1f, 1f));
            gameManager.getGameTask().runTaskTimer(main, 20L, (gameManager.getGameSettings().isDevMode() ? 5L : 20L));
            gameManager.setGameState(GameState.PLAYING);
            gameManager.setStarted(true);
            cancel();
        }
    }

    public Location generateRandomLocation() {
        Random random = new Random(System.currentTimeMillis());

        boolean xSign = random.nextBoolean();
        boolean zSign = random.nextBoolean();

        int xCoordinates = random.nextInt((int) gameManager.getGameWorld().getWorldBorder().getSize() / 2);
        int zCoordinates = random.nextInt((int) gameManager.getGameWorld().getWorldBorder().getSize() / 2);

        int xValue = (xSign ? xCoordinates : -xCoordinates);
        int zValue = (zSign ? zCoordinates : -zCoordinates);

        return gameManager.getGameWorld().getWorld().getHighestBlockAt(xValue, zValue).getRelative(0, 5, 0).getLocation();
    }
}
