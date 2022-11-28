package fr.luclyoko.crystaliauhc.game.timers;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.timers.Timer;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Pvp implements Timer {
    GameManager gameManager;

    int pvpTime;

    boolean pvp;

    List<Integer> remindTimers;

    public Pvp(GameManager gameManager, int pvpTime) {
        this.gameManager = gameManager;
        this.pvpTime = pvpTime;
        this.pvp = false;
        this.remindTimers = Arrays.asList(new Integer[] { Integer.valueOf(600), Integer.valueOf(300), Integer.valueOf(60), Integer.valueOf(30), Integer.valueOf(10), Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1) });
    }

    public int getTriggerTime() {
        return this.pvpTime;
    }

    public List<Integer> getRemindTimers() {
        return this.remindTimers;
    }

    public void setTriggerTime(int triggerTime) {
        this.pvpTime = triggerTime;
    }

    public void init() {
        this.pvp = true;
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLe PvP est désormais activé.");
                Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F));
    }

    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aActivation du PvP dans §2" + ((remindTime >= 60) ? ((remindTime / 60) + " §aminute(s).") : (remindTime + " §aseconde(s).")));
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F));
    }

    public boolean hasTriggered() {
        return this.pvp;
    }

    public String getForceCommand() {
        return "pvp";
    }
}
