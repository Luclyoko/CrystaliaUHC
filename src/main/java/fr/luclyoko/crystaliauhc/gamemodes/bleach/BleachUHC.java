package fr.luclyoko.crystaliauhc.gamemodes.bleach;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

import java.util.Arrays;
import java.util.List;

public class BleachUHC extends GamemodeUHC {

    public BleachPhase currentPhase;

    public BleachUHC(GameManager gameManager) {
        super(gameManager);
        this.displayName = "§cBleach §9UHC";
        this.gamemodeEnum = UHCGamemodes.BLEACH;
        this.currentPhase = BleachPhase.BEGINNING;
        this.maxTeamSize = 1;
    }

    @Override
    public List<String> getOptionalScoreboardLines(CrystaliaPlayer crystaliaPlayer) {
        if (!gameManager.getGameState().equals(GameState.PLAYING)) return super.getOptionalScoreboardLines(crystaliaPlayer);

        //TODO Ajouter timer / rôle du joueur
        return Arrays.asList(" §8» §3Rôle: §r",
                " §8» §6Phase n°§l" + this.currentPhase.getPhaseNumber());
    }

    public BleachPhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(BleachPhase currentPhase) {
        this.currentPhase = currentPhase;
    }
}
