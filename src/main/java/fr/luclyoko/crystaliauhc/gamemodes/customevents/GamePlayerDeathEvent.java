package fr.luclyoko.crystaliauhc.gamemodes.customevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePlayerDeathEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer crystaliaPlayer;

    private boolean isCancelled;

    public GamePlayerDeathEvent(CrystaliaPlayer crystaliaPlayer) {
        this.crystaliaPlayer = crystaliaPlayer;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getCrystaliaPlayer() {
        return this.crystaliaPlayer;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
