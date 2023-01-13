package fr.luclyoko.crystaliauhc.game.timers;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.Arrays;
import java.util.List;

public class Border implements Timer {
    GameManager gameManager;

    int borderTime;

    boolean border;

    int borderInitialSize;

    int borderFinalSize;

    double borderSpeed;

    List<Integer> remindTimers;

    public Border(GameManager gameManager, int borderTime) {
        this.gameManager = gameManager;
        this.borderTime = borderTime;
        this.border = false;
        this.borderInitialSize = 1000;
        this.borderFinalSize = 250;
        this.borderSpeed = 0.5D;
        this.remindTimers = Arrays.asList(new Integer[] {
                Integer.valueOf(1800),
                Integer.valueOf(600),
                Integer.valueOf(300),
                Integer.valueOf(60),
                Integer.valueOf(30),
                Integer.valueOf(10),
                Integer.valueOf(5),
                Integer.valueOf(4),
                Integer.valueOf(3),
                Integer.valueOf(2),
                Integer.valueOf(1) });
    }

    public int getTriggerTime() {
        return this.borderTime;
    }

    public List<Integer> getRemindTimers() {
        return this.remindTimers;
    }

    public void setTriggerTime(int triggerTime) {
        this.borderTime = triggerTime;
    }

    public void init(boolean sound) {
        this.border = true;
        this.gameManager.getGameWorld().getWorldBorder().setSize((this.borderFinalSize * 2), (long)((this.borderInitialSize - this.borderFinalSize) / this.borderSpeed));
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa bordure a commencé sa réduction jusqu'en " + this.borderFinalSize + "/" + this.borderFinalSize + " à la vitesse de " + this.borderSpeed + " bloc(s)/seconde.");
        if (sound) Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F));
    }

    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aRéduction de la bordure dans §2" + ((remindTime >= 60) ? ((remindTime / 60) + " §aminute(s)." ) : (remindTime + " §aseconde(s).")));
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F));
    }

    public boolean hasTriggered() {
        return this.border;
    }

    public String getForceCommand() {
        return "border";
    }

    public int getBorderInitialSize() {
        return this.borderInitialSize;
    }

    public void setBorderInitialSize(int borderInitialSize) {
        this.borderInitialSize = borderInitialSize;
    }

    public int getBorderFinalSize() {
        return this.borderFinalSize;
    }

    public void setBorderFinalSize(int borderFinalSize) {
        this.borderFinalSize = borderFinalSize;
    }

    public double getBorderSpeed() {
        return this.borderSpeed;
    }

    public void setBorderSpeed(double borderSpeed) {
        this.borderSpeed = borderSpeed;
    }
}
