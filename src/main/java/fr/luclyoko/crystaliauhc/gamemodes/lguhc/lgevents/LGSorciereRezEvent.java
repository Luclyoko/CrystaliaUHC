package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGSorciereRezEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer rez;

    private CrystaliaPlayer sorciere;

    public LGSorciereRezEvent(CrystaliaPlayer rez, CrystaliaPlayer sorciere) {
        this.rez = rez;
        this.sorciere = sorciere;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getRez() {
        return this.rez;
    }

    public CrystaliaPlayer getSorciere() {
        return this.sorciere;
    }
}
