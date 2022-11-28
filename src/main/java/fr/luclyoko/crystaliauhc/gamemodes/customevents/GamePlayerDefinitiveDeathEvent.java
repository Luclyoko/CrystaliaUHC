package fr.luclyoko.crystaliauhc.gamemodes.customevents;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePlayerDefinitiveDeathEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private CrystaliaPlayer crystaliaPlayer;

    private CrystaliaPlayer killer;

    private boolean isDeathShown;

    private Map<CrystaliaPlayer, String> shownRole;

    private boolean isCancelled;

    public GamePlayerDefinitiveDeathEvent(CrystaliaPlayer crystaliaPlayer, @Nullable CrystaliaPlayer killer) {
        this.crystaliaPlayer = crystaliaPlayer;
        this.killer = killer;
        this.isDeathShown = true;
        this.shownRole = new HashMap<>();
        this.isCancelled = false;
        if (crystaliaPlayer.getRole() != null)
            Main.getInstance().getPlayerManager().getCrystaliaPlayers().forEach(crystaliaPlayer1 -> this.shownRole.put(crystaliaPlayer1, crystaliaPlayer.getRole().getName()));
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

    public CrystaliaPlayer getKiller() {
        return this.killer;
    }

    public boolean isDeathShown() {
        return this.isDeathShown;
    }

    public void setDeathShown(boolean deathShown) {
        this.isDeathShown = deathShown;
    }

    public Map<CrystaliaPlayer, String> getShownRole() {
        return this.shownRole;
    }

    public void setShownRole(CrystaliaPlayer crystaliaPlayer, String roleName) {
        this.shownRole.put(crystaliaPlayer, roleName);
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
