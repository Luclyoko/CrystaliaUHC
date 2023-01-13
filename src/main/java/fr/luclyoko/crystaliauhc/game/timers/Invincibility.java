package fr.luclyoko.crystaliauhc.game.timers;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.Collections;
import java.util.List;

public class Invincibility implements Timer {
    GameManager gameManager;

    int invincibilityTime;

    boolean invincibility;

    List<Integer> remindTimers;

    public Invincibility(GameManager gameManager, int invincibilityTime) {
        this.gameManager = gameManager;
        this.invincibilityTime = invincibilityTime;
        this.invincibility = false;
        this.remindTimers = Collections.singletonList(Integer.valueOf(10));
    }

    public int getTriggerTime() {
        return this.invincibilityTime;
    }

    public List<Integer> getRemindTimers() {
        return this.remindTimers;
    }

    public void setTriggerTime(int triggerTime) {
        this.invincibilityTime = triggerTime;
    }

    public void init(boolean sound) {
        this.invincibility = true;
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLes dégâts sont désormais activés.");
        if (sound) Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 1.0F));
    }

    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aActivation des dégâts dans §2" + ((remindTime >= 60) ? ((remindTime / 60) + " §aminute(s)." ) : (remindTime + " §aseconde(s).")));
    }

    public boolean hasTriggered() {
        return this.invincibility;
    }

    public String getForceCommand() {
        return "invincibility";
    }
}
