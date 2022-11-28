package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public class LoupGarou extends LGRoleLoups {
    public LoupGarou(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setName("Loup-Garou");
    }

    public String getPowersDescription() {
        return "Pour ce faire, vous disposez des effets Strength I (la nuit) et Night Vision.\nA chaque kill, vous gagnez 1 minute de Speed et 2 coeurs d'absorption pendant 1 minute.\nChaque nuit, un chat reservé aux Loups-Garous est disponible mais vous ne pouvez y envoyer qu'un seul message. Attention car certains rôles peuvent voir ou écrire dans ce chat.";
    }
}
