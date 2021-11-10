package fr.luclyoko.crystaliauhc.game.timers;

import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Invincibility implements Timer {

    int invincibilityTime;
    boolean invincibility;
    List<Integer> remindTimers;

    public Invincibility(int invincibilityTime) {
        this.invincibilityTime = invincibilityTime;
        this.invincibility = false;
        this.remindTimers = Collections.singletonList(10);
    }

    @Override
    public int getTriggerTime() {
        return invincibilityTime;
    }

    @Override
    public List<Integer> getRemindTimers() {
        return remindTimers;
    }

    @Override
    public void setTriggerTime(int triggerTime) {
        this.invincibilityTime = triggerTime;
    }

    @Override
    public void init() {
        invincibility = true;
        Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLes dégâts sont désormais activés.");
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.EXPLODE, 1f, 1f));
    }

    @Override
    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + "§aActivation des dégâts dans §2" + (remindTime >= 60 ? remindTime / 60 + " §aminute(s)." : remindTime + " §aseconde(s)."));
    }

    @Override
    public boolean hasTriggered() {
        return invincibility;
    }
}
