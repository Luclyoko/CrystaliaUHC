package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public class LoupGarouBlanc extends LGRoleSolitaires {
    public LoupGarouBlanc(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setName("Loup-Garou Blanc");
        setLgSide(LGSides.LOUP_GAROU_BLANC);
        setLoup(true);
        setMaxHealth(30);
        updatePlayerAttributes();
    }

    public String getShortDescription() {
        return "Vous disposez des effets Strength I (la nuit) et Night Vision, ainsi que 5 coeurs supplémentaires. A chaque kill, vous gagnez 1 minute de Speed et 2 coeurs d'absorption pendant 1 minute.\nChaque nuit, un chat reservé aux Loups-Garous est disponible mais vous ne pouvez y envoyer qu'un seul message. Attention car certains rôles peuvent voir ou écrire dans ce chat.";
    }
}
