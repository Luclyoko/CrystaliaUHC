package fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LGNewLoupEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer crystaliaPlayer;

    public LGNewLoupEvent(CrystaliaPlayer crystaliaPlayer) {
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
}
