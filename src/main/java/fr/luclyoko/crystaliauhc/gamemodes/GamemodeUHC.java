package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.game.GameManager;

import java.util.ArrayList;
import java.util.List;

public class GamemodeUHC {

    public GameManager gameManager;
    public UHCGamemodes gamemodeEnum;
    public String displayName;
    public boolean teamsPossible;

    public GamemodeUHC(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public List<String> getOptionalScoreboardLines() {
        return new ArrayList<>();
    }
}
