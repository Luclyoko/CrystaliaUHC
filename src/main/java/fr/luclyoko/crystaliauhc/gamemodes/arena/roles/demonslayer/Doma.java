package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.DomaSteal;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Doma extends ArenaRole {
    public Doma(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.DOMA;
        addNightEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addPower(new DomaSteal(getArenaUHC(), this, main));
    }
}
