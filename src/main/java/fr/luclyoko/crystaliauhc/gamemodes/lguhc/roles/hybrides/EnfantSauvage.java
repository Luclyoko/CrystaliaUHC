package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public class EnfantSauvage extends LGRoleHybrides {
    public EnfantSauvage(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setLgSide(LGSides.VILLAGE);
    }
}
