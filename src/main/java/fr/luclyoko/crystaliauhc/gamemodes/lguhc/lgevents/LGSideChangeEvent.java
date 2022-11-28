package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGSideChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer crystaliaPlayer;

    private LGSides newSide;

    public LGSideChangeEvent(CrystaliaPlayer crystaliaPlayer, LGSides newSide) {
        this.crystaliaPlayer = crystaliaPlayer;
        this.newSide = newSide;
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

    public LGSides getNewSide() {
        return this.newSide;
    }
}
