package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.noragami;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.Jinki;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Bishamon extends ArenaRole {
    public Bishamon(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.BISHAMON;
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addPower(new Jinki(getArenaUHC(), this, main));
    }
}
