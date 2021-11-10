package fr.luclyoko.crystaliauhc.timers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;

import java.util.List;

public interface Timer {

    GameManager gameManager = Main.getInstance().getGameManager();

    int getTriggerTime();

    List<Integer> getRemindTimers();

    void setTriggerTime(int triggerTime);

    void init();

    void reminder(int remindTime);

    boolean hasTriggered();

}
