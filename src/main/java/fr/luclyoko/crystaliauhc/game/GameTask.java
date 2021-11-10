package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class GameTask extends BukkitRunnable {

    private GameManager gameManager;
    private int timer;
    List<Timer> timersToCheck;

    public GameTask(GameManager gameManager) {
        this.gameManager = gameManager;
        this.timer = 0;
        this.timersToCheck = Arrays.asList(gameManager.getGameSettings().getInvincibility(),
                gameManager.getGameSettings().getPvp(),
                gameManager.getGameSettings().getBorder());
        timersToCheck.addAll(gameManager.getGameSettings().getOtherTimers());
    }

    @Override
    public void run() {
        timer++;

        timersToCheck.forEach(timer1 -> {
            if (!timer1.hasTriggered()) {
                int remindTime = timer1.getTriggerTime() - timer;
                if (timer1.getRemindTimers().contains(remindTime)) timer1.reminder(remindTime);
                if (timer1.getTriggerTime() == timer) timer1.init();
            }
        });
    }

    public int getTimer() {
        return timer;
    }
}
