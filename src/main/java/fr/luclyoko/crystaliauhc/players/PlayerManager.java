package fr.luclyoko.crystaliauhc.players;

import fr.luclyoko.crystaliauhc.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;

public class PlayerManager {
    private Main main;

    private Map<UUID, CrystaliaPlayer> crystaliaPlayers;

    public PlayerManager(Main main) {
        this.main = main;
        this.crystaliaPlayers = new HashMap<>();
    }

    public CrystaliaPlayer getExactPlayer(Player player) {
        return this.crystaliaPlayers.get(player.getUniqueId());
    }

    public void registerPlayer(Player player) {
        CrystaliaPlayer crystaliaPlayer = new CrystaliaPlayer(this.main, this.main.getGameManager(), player, this.main.getGameManager().isStarted());
        this.crystaliaPlayers.put(player.getUniqueId(), crystaliaPlayer);
    }

    public void deletePlayer(Player player) {
        CrystaliaPlayer crystaliaPlayer = getExactPlayer(player);
        this.crystaliaPlayers.remove(player.getUniqueId());
    }

    public List<CrystaliaPlayer> getCrystaliaPlayers() {
        return new ArrayList<>(this.crystaliaPlayers.values());
    }

    public List<CrystaliaPlayer> getGamePlayers() {
        return (List<CrystaliaPlayer>)this.crystaliaPlayers.values().stream().filter(crystaliaPlayer -> !crystaliaPlayer.isSpec()).collect(Collectors.toList());
    }

    public List<CrystaliaPlayer> getAliveGamePlayers() {
        return (List<CrystaliaPlayer>)getGamePlayers().stream().filter(CrystaliaPlayer::isAlive).collect(Collectors.toList());
    }

    public List<CrystaliaPlayer> getOnlineGamePlayers() {
        return (List<CrystaliaPlayer>)getGamePlayers().stream().filter(CrystaliaPlayer::isOnline).collect(Collectors.toList());
    }

    public List<CrystaliaPlayer> getOnlineAlivePlayers() {
        return (List<CrystaliaPlayer>)getAliveGamePlayers().stream().filter(CrystaliaPlayer::isOnline).collect(Collectors.toList());
    }
}
