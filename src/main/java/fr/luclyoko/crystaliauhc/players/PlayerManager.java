package fr.luclyoko.crystaliauhc.players;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private Main main;
    private Map<UUID, CrystaliaPlayer> crystaliaPlayers;
    private List<CrystaliaPlayer> gamePlayers;

    public PlayerManager(Main main) {
        this.main = main;
        this.crystaliaPlayers = new HashMap<>();
        this.gamePlayers = new ArrayList<>();
    }

    public CrystaliaPlayer getExactPlayer(Player player) {
        return crystaliaPlayers.get(player.getUniqueId());
    }

    public void registerPlayer(Player player) {
        CrystaliaPlayer crystaliaPlayer = new CrystaliaPlayer(player, main.getGameManager().getGameSettings().getSpecs().contains(player.getUniqueId()));
        crystaliaPlayers.put(player.getUniqueId(), crystaliaPlayer);
        updatePlayer(crystaliaPlayer);
    }

    public void deletePlayer(Player player) {
        crystaliaPlayers.remove(player.getUniqueId());
    }

    public List<CrystaliaPlayer> getCrystaliaPlayers() {
        return new ArrayList<>(crystaliaPlayers.values());
    }

    public void updatePlayer(CrystaliaPlayer crystaliaPlayer) {
        if (!crystaliaPlayer.isSpec() && !gamePlayers.contains(crystaliaPlayer) && !crystaliaPlayer.isDead()) gamePlayers.add(crystaliaPlayer);
        else if (crystaliaPlayer.isSpec() || crystaliaPlayer.isDead()) gamePlayers.remove(crystaliaPlayer);
    }

    public List<CrystaliaPlayer> getGamePlayers() {
        return gamePlayers;
    }
}
