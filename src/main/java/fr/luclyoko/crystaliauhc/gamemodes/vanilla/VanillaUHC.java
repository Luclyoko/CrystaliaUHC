package fr.luclyoko.crystaliauhc.gamemodes.vanilla;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import fr.luclyoko.crystaliauhc.map.GameWorld;

import java.io.File;

public class VanillaUHC extends GamemodeUHC {
    public VanillaUHC(Main main, GameManager gameManager) {
        super(main, gameManager);
        this.defaultName = "§6UHC";
        this.gamemodeEnum = UHCGamemodes.VANILLA;
        this.maxTeamSize = 10;
        this.displayName = getDefaultName();
        main.getServer().unloadWorld("gameworld", false);
        main.getMapManager().deleteWorld(new File(main.getDataFolder().getParentFile().getParentFile(), "gameworld"));
        gameManager.setGameWorld(new GameWorld(main, "gameworld", 0, 0, null));
    }
}
