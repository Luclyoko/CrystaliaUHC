package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGObserveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer observed;

    private CrystaliaPlayer analyste;

    public LGObserveEvent(CrystaliaPlayer observed, CrystaliaPlayer analyste) {
        this.observed = observed;
        this.analyste = analyste;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getObserved() {
        return this.observed;
    }

    public CrystaliaPlayer getAnalyste() {
        return this.analyste;
    }
}
