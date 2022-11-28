package fr.luclyoko.crystaliauhc.gamemodes.customevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameDayStartingEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
