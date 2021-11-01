package fr.luclyoko.crystaliauhc.gamemodes.bleach;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;

import java.util.Arrays;
import java.util.List;

public class BleachUHC extends GamemodeUHC {

    public BleachPhase currentPhase;

    public BleachUHC(GameManager gameManager) {
        super(gameManager);
        this.displayName = "§cBleach §9UHC";
        this.gamemodeEnum = UHCGamemodes.BLEACH;
    }

    @Override
    public List<String> getOptionalScoreboardLines() {
        if (!gameManager.getGameState().equals(GameState.PLAYING)) return super.getOptionalScoreboardLines();

        //TODO Ajouter timer / rôle du joueur
        return Arrays.asList(" §7» §3Rôle: ",
                " §7» §6Phase n°§l" + currentPhase);
    }
}
