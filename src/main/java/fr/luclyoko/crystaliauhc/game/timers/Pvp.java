package fr.luclyoko.crystaliauhc.game.timers;

import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pvp implements Timer {

    int pvpTime;
    boolean pvp;
    List<Integer> remindTimers;

    public Pvp(int pvpTime) {
        this.pvpTime = pvpTime;
        this.pvp = false;
        this.remindTimers = Arrays.asList(10*60, 5*60, 60, 30, 10, 5, 4, 3, 2, 1);
    }

    @Override
    public int getTriggerTime() {
        return pvpTime;
    }

    @Override
    public List<Integer> getRemindTimers() {
        return remindTimers;
    }

    @Override
    public void setTriggerTime(int triggerTime) {
        this.pvpTime = triggerTime;
    }

    @Override
    public void init() {
        this.pvp = true;
        Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLe PvP est désormais activé.");
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f));
    }

    @Override
    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§aActivation du PvP dans §2" + (remindTime >= 60 ? remindTime / 60 + " §aminute(s)." : remindTime + " §aseconde(s)."));
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f));
    }

    @Override
    public boolean hasTriggered() {
        return pvp;
    }
}
