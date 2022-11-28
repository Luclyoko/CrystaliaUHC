package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public class JoueurDeFlute extends LGRoleSolitaires {
    public JoueurDeFlute(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setLgSide(LGSides.JOUEUR_DE_FLUTE);
    }
}
