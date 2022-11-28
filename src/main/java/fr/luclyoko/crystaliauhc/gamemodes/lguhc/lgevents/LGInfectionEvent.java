package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGInfectionEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer infected;

    private CrystaliaPlayer ipdl;

    public LGInfectionEvent(CrystaliaPlayer infected, CrystaliaPlayer ipdl) {
        this.infected = infected;
        this.ipdl = ipdl;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getInfected() {
        return this.infected;
    }

    public CrystaliaPlayer getIpdl() {
        return this.ipdl;
    }
}
