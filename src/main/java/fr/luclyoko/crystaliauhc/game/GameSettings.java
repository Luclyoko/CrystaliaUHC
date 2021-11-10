package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.game.timers.Border;
import fr.luclyoko.crystaliauhc.game.timers.Invincibility;
import fr.luclyoko.crystaliauhc.game.timers.Pvp;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSettings {

    private GameManager gameManager;
    private boolean devMode;

    private UUID host;
    private String hostName;
    private List<UUID> specs;

    private Timer pvp;
    private Timer invincibility;
    private Timer border;
    private List<Timer> otherTimers;

    public GameSettings(GameManager gameManager) {
        this.gameManager = gameManager;
        this.devMode = false;

        this.host = null;
        this.hostName = "Aucun";
        this.specs = new ArrayList<>();

        this.pvp = new Pvp(20*60);
        this.invincibility = new Invincibility(60);
        this.border = new Border(60*60);
        this.otherTimers = new ArrayList<>();
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public UUID getHost() {
        return host;
    }

    public void setHost(UUID host) {
        this.host = host;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void addSpec(Player specs) {
        this.specs.add(specs.getUniqueId());
        specs.setDisplayName("§bSpec §8» §r" + specs.getName());
    }

    public Timer getPvp() {
        return pvp;
    }

    public void setPvp(Timer pvp) {
        this.pvp = pvp;
    }

    public Timer getInvincibility() {
        return invincibility;
    }

    public void setInvincibility(Timer invincibility) {
        this.invincibility = invincibility;
    }

    public Timer getBorder() {
        return border;
    }

    public void setBorder(Timer border) {
        this.border = border;
    }

    public void removeSpec(Player player) {
        specs.remove(player.getUniqueId());
    }

    public List<UUID> getSpecs() {
        return specs;
    }

    public List<Timer> getOtherTimers() {
        return otherTimers;
    }

    public void setOtherTimers(List<Timer> otherTimers) {
        this.otherTimers = otherTimers;
    }
}
