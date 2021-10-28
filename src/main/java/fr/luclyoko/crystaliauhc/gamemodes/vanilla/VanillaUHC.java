package fr.luclyoko.crystaliauhc.gamemodes.vanilla;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.UHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;

public class VanillaUHC extends UHC {

    public VanillaUHC(GameManager gameManager) {
        super(gameManager);
        this.displayName = "§6UHC";
        this.gamemodeEnum = UHCGamemodes.VANILLA;
    }

}
