package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGAnalyseEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer analysed;

    private CrystaliaPlayer analyste;

    public LGAnalyseEvent(CrystaliaPlayer analysed, CrystaliaPlayer analyste) {
        this.analysed = analysed;
        this.analyste = analyste;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CrystaliaPlayer getAnalysed() {
        return this.analysed;
    }

    public CrystaliaPlayer getAnalyste() {
        return this.analyste;
    }
}
