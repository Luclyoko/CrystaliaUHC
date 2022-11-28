package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public abstract class LGRoleLoups extends LGRole {
    public LGRoleLoups(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setLgSide(LGSides.LOUPS);
        setLoup(true);
    }
}
