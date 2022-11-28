package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGEspionnageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer espionne;

    private CrystaliaPlayer pretresse;

    public LGEspionnageEvent(CrystaliaPlayer espionne, CrystaliaPlayer pretresse) {
        this.espionne = espionne;
        this.pretresse = pretresse;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getEspionne() {
        return this.espionne;
    }

    public CrystaliaPlayer getPretresse() {
        return this.pretresse;
    }
}
