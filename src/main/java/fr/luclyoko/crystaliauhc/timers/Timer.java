package fr.luclyoko.crystaliauhc.timers;

import fr.luclyoko.crystaliauhc.Main;

import java.util.List;

public interface Timer {
    int getTriggerTime();

    List<Integer> getRemindTimers();

    void setTriggerTime(int paramInt);

    void init(boolean sound);

    void reminder(int paramInt);

    boolean hasTriggered();

    String getForceCommand();

    default void force() {
        setTriggerTime(Main.getInstance().getGameManager().getGameTask().getTimer() + 6);
    }
}
