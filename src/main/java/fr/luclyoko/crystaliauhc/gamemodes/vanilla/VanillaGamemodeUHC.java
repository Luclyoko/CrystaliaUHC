package fr.luclyoko.crystaliauhc.gamemodes.vanilla;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;

public class VanillaGamemodeUHC extends GamemodeUHC {

    public VanillaGamemodeUHC(GameManager gameManager) {
        super(gameManager);
        this.displayName = "§6UHC";
        this.gamemodeEnum = UHCGamemodes.VANILLA;
    }

}
