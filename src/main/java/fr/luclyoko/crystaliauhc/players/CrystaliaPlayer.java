package fr.luclyoko.crystaliauhc.players;

import org.bukkit.entity.Player;

import java.util.UUID;

public class CrystaliaPlayer {

    private UUID playerUUID;
    private String playerName;
    private boolean isOnline;

    private boolean isSpec;
    private boolean isDead;

    public CrystaliaPlayer(Player player, boolean isSpec) {
        this.playerUUID = player.getUniqueId();
        this.playerName = player.getName();
        this.isOnline = true;

        this.isSpec = isSpec;
        this.isDead = false;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isSpec() {
        return isSpec;
    }

    public void setSpec(boolean spec) {
        isSpec = spec;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
