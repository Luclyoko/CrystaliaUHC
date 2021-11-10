package fr.luclyoko.crystaliauhc.game.timers;

import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.Arrays;
import java.util.List;

public class Border implements Timer {

    int borderTime;
    boolean border;
    int borderInitialSize;
    int borderFinalSize;
    double borderSpeed;
    List<Integer> remindTimers;

    public Border(int borderTime) {
        this.borderTime = borderTime;
        this.border = false;
        this.borderInitialSize = 1000;
        this.borderFinalSize = 250;
        this.borderSpeed = 0.5D;
        this.remindTimers = Arrays.asList(30*60, 10*60, 5*60, 60, 30, 10, 5, 4, 3, 2, 1);
    }

    @Override
    public int getTriggerTime() {
        return borderTime;
    }

    @Override
    public List<Integer> getRemindTimers() {
        return remindTimers;
    }

    @Override
    public void setTriggerTime(int triggerTime) {
        this.borderTime = triggerTime;
        this.border = false;
    }

    @Override
    public void init() {
        this.border = true;
        gameManager.getGameWorld().getWorldBorder().setSize(borderFinalSize * 2,
                (long) ((borderInitialSize - borderFinalSize) / borderSpeed));
        Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa bordure a commencé sa réduction jusqu'en " + borderFinalSize + "/" + borderFinalSize + " à la vitesse de " + borderSpeed + " bloc(s)/seconde.");
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f));
    }

    @Override
    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§aRéduction de la bordure dans §2" + (remindTime >= 60 ? remindTime / 60 + " §aminute(s)." : remindTime + " §aseconde(s)."));
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f));
    }

    @Override
    public boolean hasTriggered() {
        return border;
    }

    public int getBorderInitialSize() {
        return borderInitialSize;
    }

    public void setBorderInitialSize(int borderInitialSize) {
        this.borderInitialSize = borderInitialSize;
    }

    public int getBorderFinalSize() {
        return borderFinalSize;
    }

    public void setBorderFinalSize(int borderFinalSize) {
        this.borderFinalSize = borderFinalSize;
    }

    public double getBorderSpeed() {
        return borderSpeed;
    }

    public void setBorderSpeed(double borderSpeed) {
        this.borderSpeed = borderSpeed;
    }
}
