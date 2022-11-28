package fr.luclyoko.crystaliauhc.gamemodes.arena;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;

public class ArenaUHC extends GamemodeUHC {

    public ArenaUHC(Main main, GameManager gameManager) {
        super(main, gameManager);
        this.displayName = "§bCrystArena";
        this.gamemodeEnum = UHCGamemodes.VANILLA;
        this.maxTeamSize = 1;
    }
}
