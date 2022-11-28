package fr.luclyoko.crystaliauhc.gamemodes.customevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer deadPlayer;

    private CrystaliaPlayer killer;

    public GamePlayerKillEvent(CrystaliaPlayer deadPlayer, CrystaliaPlayer killer) {
        this.deadPlayer = deadPlayer;
        this.killer = killer;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getDeadPlayer() {
        return this.deadPlayer;
    }

    public CrystaliaPlayer getKiller() {
        return this.killer;
    }
}
