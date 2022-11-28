package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public class Ange extends LGRoleSolitaires {
    public Ange(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setLgSide(LGSides.ANGE);
    }

    public static class AngeGardien extends Ange {
        public AngeGardien(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
            super(gameManager, crystaliaPlayer);
        }
    }

    public static class AngeDechu extends Ange {
        public AngeDechu(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
            super(gameManager, crystaliaPlayer);
        }
    }
}
