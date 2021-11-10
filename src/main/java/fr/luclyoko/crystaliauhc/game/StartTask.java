package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class StartTask extends BukkitRunnable {

    private Main main;
    private GameManager gameManager;
    private int countdown;
    private boolean starting;

    public StartTask(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
        this.countdown = 11;
        this.starting = false;
    }

    @Override
    public void run() {
        starting = true;
        countdown--;
        if (countdown > 0) {
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f));
            Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§3Démarrage de la partie dans §b" + countdown + " §3seconde(s).");
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f));
            Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§3Démarrage de la téléportation des joueurs.");
            new TeleportationTask(main, gameManager, main.getPlayerManager().getGamePlayers()).runTaskTimer(main, 20L, 20L);
            cancel();
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        starting = false;
        countdown = 11;
    }
}
