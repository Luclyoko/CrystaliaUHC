package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameDayStartingEvent;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameNightStartingEvent;
import fr.luclyoko.crystaliauhc.timers.Timer;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {
    private Main main;

    private GameManager gameManager;

    private int timer;

    List<Timer> timersToCheck;

    private int dayNightCycleTimer;

    public GameTask(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
        this.timer = 0;
        this.timersToCheck = new ArrayList<>();
        this.dayNightCycleTimer = 0;
    }

    public void init() {
        this.timersToCheck = this.gameManager.getGameSettings().getAllTimers();
        runTaskTimer((Plugin)this.main, 20L, this.gameManager.getGameSettings().isDevMode() ? 5L : 20L);
    }

    public void run() {
        this.timer++;
        this.timersToCheck.forEach(timer1 -> {
            if (!timer1.hasTriggered()) {
                int remindTime = timer1.getTriggerTime() - this.timer;
                if (timer1.getRemindTimers().contains(Integer.valueOf(remindTime)))
                    timer1.reminder(remindTime);
                if (timer1.getTriggerTime() == this.timer)
                    timer1.init();
            }
        });
        if (this.dayNightCycleTimer >= this.gameManager.getGameSettings().getDayNightCycle()) {
            this.gameManager.getGameSettings().setDay(!this.gameManager.getGameSettings().isDay());
            this.dayNightCycleTimer = 0;
            if (this.gameManager.getGameSettings().isDay()) {
                this.main.getServer().getPluginManager().callEvent((Event)new GameDayStartingEvent());
            } else {
                this.main.getServer().getPluginManager().callEvent((Event)new GameNightStartingEvent());
            }
        }
        if (!this.gameManager.getGameSettings().isEternalDay()) {
            this.dayNightCycleTimer++;
            if (this.gameManager.getGameSettings().isDay()) {
                this.gameManager.getGameWorld().getWorld().setTime(24000L - 12000L + this.dayNightCycleTimer * (12000 / this.gameManager.getGameSettings().getDayNightCycle()));
            } else {
                this.gameManager.getGameWorld().getWorld().setTime(24000L - this.dayNightCycleTimer * (12000 / this.gameManager.getGameSettings().getDayNightCycle()));
            }
        }
    }

    public int getTimer() {
        return this.timer;
    }

    public int getDayNightCycleTimer() {
        return this.dayNightCycleTimer;
    }
}
