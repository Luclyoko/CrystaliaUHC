package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

import java.util.ArrayList;
import java.util.List;

public class GamemodeUHC {

    protected final GameManager gameManager;
    protected UHCGamemodes gamemodeEnum;
    protected String displayName;
    protected int teamsSize;
    protected int maxTeamSize;

    public GamemodeUHC(GameManager gameManager) {
        this.gameManager = gameManager;
        this.teamsSize = 1;
    }

    public List<String> getOptionalScoreboardLines(CrystaliaPlayer crystaliaPlayer) {
        return new ArrayList<>();
    }

    public UHCGamemodes getGamemodeEnum() {
        return gamemodeEnum;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameChat() {
        return displayName + " §8» ";
    }

    public int getTeamsSize() {
        return teamsSize;
    }
}
